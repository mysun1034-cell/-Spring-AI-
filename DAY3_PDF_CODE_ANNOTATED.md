# Spring AI Day3 PDF 코드 전체 주석본

이 문서는 `Spring AI Day3 — Advisor _ Chat Memory - Slidev.pdf` 안에 나온 코드 조각들을
슬라이드 기준으로 모아서, 한 줄씩 주석을 붙여 학습용으로 풀어쓴 자료입니다.

주의:
- 슬라이드에는 일부 코드가 축약되어 있습니다. 예: `"..."`, 일부 import 생략
- 그래서 이 문서는 "슬라이드에 보이는 코드"를 기준으로 설명합니다
- 실제 프로젝트 전체 완성본과 100% 동일하지 않을 수 있습니다

---

## 1. Day2 복습 코드

슬라이드 의미:
- Day1은 문자열 넣고 문자열 받기
- Day2는 프롬프트 템플릿에 값을 넣고, 결과를 객체 타입으로 받기

```java
// chatClient를 통해 AI 호출 체인을 시작합니다.
chatClient.prompt()
        // user 프롬프트를 구성합니다.
        .user(u -> u.text(CLASSIFY_TEMPLATE).param("text", text))
        // 실제 모델 호출을 수행합니다.
        .call()
        // 결과를 문자열이 아니라 InquiryResult 타입으로 변환합니다.
        .entity(InquiryResult.class);
```

구술 설명:
- 여기서 핵심은 `content()`가 아니라 `entity()`예요.
- 즉 "AI 답을 그냥 글자로 받는 것"에서
- "AI 답을 자바 객체로 받는 것"까지 왔다는 뜻입니다.

---

## 2. Advisor 인터페이스 구조

```java
// Advisor는 실행 순서를 가지는 Ordered를 상속받습니다.
public interface Advisor extends Ordered {
    // Advisor 이름을 반환합니다.
    String getName();
}

// Ordered는 순서 제어용 인터페이스입니다.
public interface Ordered {
    // 가장 먼저 실행될 수 있는 최상위 우선순위 값입니다.
    int HIGHEST_PRECEDENCE = Integer.MIN_VALUE;
    // 가장 나중에 실행될 수 있는 최하위 우선순위 값입니다.
    int LOWEST_PRECEDENCE = Integer.MAX_VALUE;
    // 각 구현체가 자기 실행 순서를 반환합니다.
    int getOrder();
}

// CallAdvisor는 동기식 .call() 요청을 가로채는 Advisor입니다.
public interface CallAdvisor extends Advisor {
    // 요청을 받아서 전처리/후처리하고, 다음 Advisor 또는 실제 모델 호출로 넘깁니다.
    ChatClientResponse adviseCall(
            ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain);
}
```

구술 설명:
- `Advisor`는 "중간 관리자" 같은 역할입니다.
- `getOrder()` 숫자가 작을수록 먼저 들어갑니다.
- `CallAdvisor`는 오늘 수업 범위고, `.call()` 방식 AI 호출에서 작동합니다.

Python 비교:

```python
class Middleware:
    def before(self): ...
    def after(self): ...
```

---

## 3. ChatClientRequest / ChatClientResponse / mutate

```java
// 요청 객체는 Prompt와 공유 context를 함께 들고 있습니다.
public record ChatClientRequest(Prompt prompt, Map<String, Object> context) {}

// 응답 객체도 실제 chatResponse와 공유 context를 함께 들고 있습니다.
public record ChatClientResponse(ChatResponse chatResponse, Map<String, Object> context) {}

// 기존 request는 불변이므로 mutate()로 복사본 빌더를 얻습니다.
ChatClientRequest mutatedRequest = request.mutate()
        // 새 프롬프트를 넣고
        .prompt(augmentedPrompt)
        // 변경된 새 요청 객체를 만듭니다.
        .build();
```

구술 설명:
- 요청 객체를 직접 막 바꾸는 게 아니라
- "복사해서 수정한 새 요청"을 만드는 구조예요.
- 그래서 `mutate()`가 나옵니다.

Python 비교:

```python
new_request = old_request.copy()
new_request["prompt"] = augmented_prompt
```

---

## 4. MaxCharLengthAdvisor

슬라이드 파일명:
- `advisor/MaxCharLengthAdvisor.java`

```java
// 응답 길이 제한용 커스텀 Advisor입니다.
public class MaxCharLengthAdvisor implements CallAdvisor {

    // context에 넣어서 호출별로 길이를 덮어쓸 때 쓰는 키입니다.
    public static final String MAX_CHAR_LENGTH = "maxCharLength";

    // 기본 최대 길이입니다.
    private final int defaultMaxCharLength;

    // 실행 순서입니다.
    private final int order;

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest request, CallAdvisorChain chain) {
        // 요청을 전처리해서 프롬프트를 보강합니다.
        ChatClientRequest mutatedRequest = augmentPrompt(request);

        // 보강된 요청을 다음 Advisor 또는 모델 호출로 넘깁니다.
        return chain.nextCall(mutatedRequest);
    }

    private ChatClientRequest augmentPrompt(ChatClientRequest request) {
        // 호출 시점에 context로 넘어온 최대 길이 값을 꺼냅니다.
        Integer maxCharLength = (Integer) request.context().get(MAX_CHAR_LENGTH);

        // 없으면 기본값을 쓰고, 있으면 호출별 값을 씁니다.
        int limit = maxCharLength != null ? maxCharLength : this.defaultMaxCharLength;

        // 기존 사용자 메시지 끝에 "N자 이내로 답변" 조건을 덧붙입니다.
        Prompt augmented = request.prompt().augmentUserMessage(
                m -> UserMessage.builder().text(m.getText() + " " + limit + "자 이내로 답변해 주세요.").build());

        // 프롬프트가 바뀐 새 요청 객체를 만들어 반환합니다.
        return request.mutate().prompt(augmented).build();
    }
}
```

구술 설명:
- 이 Advisor는 질문을 막 바꾸는 게 아니라
- 질문 뒤에 조건을 "덧붙이는" 역할이에요.
- 예를 들어 원래 질문이
- "Spring AI가 뭐야?"
- 였다면 실제 모델에는
- "Spring AI가 뭐야? 300자 이내로 답변해 주세요."
- 처럼 들어갑니다.

---

## 5. CallCounterAdvisor

슬라이드 파일명:
- `advisor/CallCounterAdvisor.java`

```java
// Spring Bean으로 등록해서 여러 곳에서 공유합니다.
@Component
public class CallCounterAdvisor implements CallAdvisor {

    // 호출 횟수를 안전하게 세기 위한 원자적 카운터입니다.
    private final AtomicInteger callCount = new AtomicInteger(0);

    @Override
    public int getOrder() {
        // 거의 마지막에 실행되게 설정합니다.
        return Ordered.LOWEST_PRECEDENCE - 1;
    }

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest request, CallAdvisorChain chain) {
        // 먼저 실제 다음 단계 호출을 수행합니다.
        ChatClientResponse response = chain.nextCall(request);

        // 호출이 끝난 뒤 카운트를 1 증가시킵니다.
        this.callCount.incrementAndGet();

        // 원래 응답을 그대로 돌려줍니다.
        return response;
    }

    // 지금까지 몇 번 호출되었는지 반환합니다.
    public int getCallCount() {
        return this.callCount.get();
    }
}
```

구술 설명:
- 이건 "후처리용 Advisor"예요.
- 먼저 AI 호출을 끝낸 다음
- "이번이 몇 번째 호출이었지?"를 세는 구조입니다.

Python 비교:

```python
count += 1
```

---

## 6. AssistantService

슬라이드 핵심 메시지:
- `ask()` 메서드는 한 글자도 안 바뀐다
- 공통 처리는 생성자에서 Advisor로 붙인다

```java
// 서비스 생성자입니다.
public AssistantService(ChatClient.Builder chatClientBuilder, CallCounterAdvisor callCounterAdvisor) {
    this.chatClient = chatClientBuilder
            // AI의 기본 역할을 지정합니다.
            .defaultSystem("...")
            // 이 ChatClient의 모든 호출에 공통 Advisor들을 붙입니다.
            .defaultAdvisors(
                    // 기본 길이 제한 Advisor
                    new MaxCharLengthAdvisor(300, Ordered.HIGHEST_PRECEDENCE),
                    // 민감 단어 차단 Advisor
                    new SafeGuardAdvisor(List.of("욕설","계좌번호","폭력","폭탄"),
                            "해당 질문은 민감한 콘텐츠 요청이므로 응답할 수 없습니다.",
                            Ordered.HIGHEST_PRECEDENCE + 1),
                    // 호출 횟수 세는 Advisor
                    callCounterAdvisor,
                    // 요청/응답 로그를 남기는 내장 Advisor
                    new SimpleLoggerAdvisor(Ordered.LOWEST_PRECEDENCE))
            // 모든 설정을 바탕으로 최종 ChatClient를 생성합니다.
            .build();
}

// 실제 질문 처리 메서드입니다.
public String ask(String question) {
    // 사용자 질문을 그대로 보내고
    return chatClient.prompt().user(question).call().content();
    // 문자열 응답만 꺼내서 반환합니다.
}
```

구술 설명:
- 진짜 중요한 포인트가 여기예요.
- `ask()`는 거의 안 건드립니다.
- 대신 생성자에서 ChatClient에 공통 규칙을 다 심어 놓습니다.
- 그래서 나중에 서비스 메서드는 깔끔하게 유지됩니다.

---

## 7. 호출별 Advisor 추가

슬라이드 주제:
- `defaultAdvisors()` vs `.advisors()`

```java
// 이번 호출에서만 최대 길이를 따로 지정하는 메서드입니다.
public String askWithMaxLength(String question, int maxCharLength) {
    return chatClient.prompt()
            // 이번 호출에만 context 값을 추가합니다.
            .advisors(spec -> spec.param(MaxCharLengthAdvisor.MAX_CHAR_LENGTH, maxCharLength))
            // 사용자 질문을 넣고
            .user(question)
            // 모델을 호출한 뒤
            .call()
            // 본문만 문자열로 꺼냅니다.
            .content();
}
```

구술 설명:
- 생성자에서 깐 기본 Advisor는 그대로 두고
- "이번 한 번만" 옵션을 얹는 방식이에요.
- 즉 기본 세팅은 유지하면서, 호출별로 미세 조정합니다.

---

## 8. SafeGuardAdvisor와 로그 설정

```yaml
# Advisor 관련 DEBUG 로그를 보이게 합니다.
logging:
  level:
    org.springframework.ai.chat.client.advisor: DEBUG
```

```java
// 욕설/계좌번호/폭력/폭탄 같은 민감 단어를 막는 내장 Advisor 예시입니다.
new SafeGuardAdvisor(
  // 차단할 단어 목록
  List.of("욕설","계좌번호","폭력","폭탄"),
  // 차단 시 사용자에게 돌려줄 문장
  "해당 질문은 민감한 콘텐츠...",
  // 실행 순서
  HIGHEST_PRECEDENCE + 1)
```

```http
// 실제 요청 예시입니다.
GET /api/ask?question=계좌번호 훔치는 방법 알려줘
```

```text
// 실제 응답 예시입니다.
"해당 질문은 민감한 콘텐츠 요청이므로 응답할 수 없습니다."
```

구술 설명:
- 이 Advisor는 아예 `nextCall()`을 안 부를 수 있어요.
- 그러면 LLM으로 요청이 안 갑니다.
- 즉 "모델 호출 전 차단"입니다.

---

## 9. ChatMemory 인터페이스

```java
// 대화 기억을 다루는 표준 인터페이스입니다.
public interface ChatMemory {
    // 특정 conversationId에 메시지들을 추가합니다.
    void add(String conversationId, List<Message> messages);

    // 특정 conversationId의 이전 메시지들을 가져옵니다.
    List<Message> get(String conversationId);

    // 특정 conversationId의 기억을 비웁니다.
    void clear(String conversationId);
}
```

구술 설명:
- 메모리의 핵심은 세 가지예요.
- 넣기
- 꺼내기
- 비우기

---

## 10. MessageChatMemoryAdvisor (In-Memory)

```java
// 메모리 저장소를 메모리 안쪽(Map 같은 곳)으로 둡니다.
ChatMemory chatMemory = MessageWindowChatMemory.builder()
        // 실제 저장 위치는 InMemory 저장소입니다.
        .chatMemoryRepository(new InMemoryChatMemoryRepository())
        // 최대 20개 메시지만 유지합니다.
        .maxMessages(20)
        // 최종 ChatMemory 객체를 만듭니다.
        .build();

// 이 메모리를 ChatClient의 기본 Advisor로 붙입니다.
ChatClient chatClient = chatClientBuilder
        .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
        .build();

// 메모리를 사용하는 실제 채팅 메서드입니다.
public String chat(String question, String conversationId) {
    return chatClient.prompt()
            // 이번 질문을 넣고
            .user(question)
            // 어떤 대화방 메모리를 쓸지 conversationId를 넘깁니다.
            .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, conversationId))
            // 모델을 호출한 뒤
            .call()
            // 본문만 문자열로 반환합니다.
            .content();
}
```

구술 설명:
- 여기서 핵심은 `conversationId`예요.
- 이 값이 있어야
- "어느 사람의 지난 대화"를 다시 가져올지 알 수 있습니다.

Python 비교:

```python
memory["room1"].append(message)
history = memory["room1"]
```

---

## 11. conversationId는 필수

```java
// 메모리 Advisor를 쓸 때는 conversationId를 반드시 넘겨야 합니다.
.advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, conversationId))
```

구술 설명:
- 이게 빠지면
- "어느 대화의 메모리를 꺼내야 하지?"
- 를 모르기 때문에 런타임 에러가 납니다.

---

## 12. JDBC 저장소용 build.gradle / application.yml

```gradle
// JDBC 기능을 추가합니다.
implementation 'org.springframework.boot:spring-boot-starter-jdbc'

// Spring AI의 JDBC 기반 Chat Memory 저장소를 추가합니다.
implementation 'org.springframework.ai:spring-ai-starter-model-chat-memory-repository-jdbc'

// 로컬 파일 DB로 H2를 추가합니다.
runtimeOnly 'com.h2database:h2'
```

```yaml
spring:
  ai:
    chat:
      memory:
        repository:
          jdbc:
            # H2 파일 모드에서는 always로 해줘야 스키마가 확실히 생깁니다.
            initialize-schema: always
  datasource:
    # H2 파일 DB 경로입니다.
    url: jdbc:h2:file:./data/chatmemory
    # H2 드라이버를 사용합니다.
    driver-class-name: org.h2.Driver
```

구술 설명:
- In-Memory는 서버 껐다 켜면 날아갑니다.
- 그래서 JDBC 저장소로 바꾸면
- 대화가 파일 DB에 남습니다.

---

## 13. PersistentChatService

```java
// Spring Bean으로 등록되는 서비스입니다.
@Service
public class PersistentChatService {

    // 실제 AI 호출 도구입니다.
    private final ChatClient chatClient;

    public PersistentChatService(@Qualifier("jdbcChatMemory") ChatMemory chatMemory,
            ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                // JDBC 기반 메모리 Advisor를 기본으로 붙입니다.
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                // 최종 ChatClient를 생성합니다.
                .build();
    }

    public String chat(String question, String conversationId) {
        return chatClient.prompt()
                // 사용자 질문을 넣고
                .user(question)
                // conversationId를 함께 넘겨 어떤 대화 메모리를 쓸지 정합니다.
                .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, conversationId))
                // 실제 모델을 호출합니다.
                .call()
                // 응답 본문만 반환합니다.
                .content();
    }
}
```

구술 설명:
- In-Memory 버전과 거의 똑같죠.
- 정말 차이는
- "메모리를 어디에 저장하느냐"
- 쪽입니다.
- 그래서 Day3는 구조 분리가 왜 좋은지도 같이 보여줍니다.

---

## 14. 오늘 수업 전체를 한 줄로 묶으면

```text
Controller
→ Service
→ ChatClient
→ Advisor 전처리
→ Memory 붙이기
→ LLM 호출
→ Advisor 후처리
→ 응답 반환
```

구술 설명:
- Day3는 "프롬프트 문장 쓰는 기술"보다
- "AI 호출 과정을 설계하는 기술"을 배우는 날입니다.
- 공통 처리면 Advisor
- 대화 맥락이면 Memory
- 이렇게 역할을 나눕니다.
