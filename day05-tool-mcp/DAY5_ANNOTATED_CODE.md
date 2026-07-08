# Day5 AI 장기요양 헬퍼 - 파일별 전체 주석 코드

이 문서는 원본 코드를 더럽히지 않고 공부하기 위해 만든 **학습용 주석 버전**입니다.

Spring Boot가 아직 헷갈릴 때는 이렇게 보면 됩니다.

```text
사용자/Bruno/브라우저
        |
        v
Controller  : 주소를 받는 입구
        |
        v
Service     : 실제 일을 처리하는 곳
        |
        v
ChatClient  : Spring AI가 AI 모델에게 질문을 보내는 도구
        |
        v
Tool / MCP  : AI가 필요할 때 호출할 수 있는 기능
        |
        v
Gemini      : 최종 답변 생성
```

Python으로 치면 대략 이런 느낌입니다.

```python
# Flask/FastAPI에서 URL을 받는 함수가 Controller 역할입니다.
@app.get("/api/ask")
def ask(question: str):
    # 실제 로직을 service에게 맡깁니다.
    return chat_service.ask(question)
```

---

## 1. 전체 폴더 구조

```text
day05-tool-mcp
├─ build.gradle
├─ gradle.properties
├─ README.md
├─ mcp-sandbox
│  ├─ 보호자-상담-FAQ.md
│  ├─ 복약관리-안내.md
│  └─ 장기요양-주의사항.md
└─ src
   ├─ main
   │  ├─ java/com/study/day05toolmcp
   │  │  ├─ Day05ToolMcpApplication.java
   │  │  ├─ AiController.java
   │  │  ├─ config
   │  │  │  └─ ChatMemoryConfig.java
   │  │  ├─ service
   │  │  │  ├─ ChatService.java
   │  │  │  ├─ ToolChatService.java
   │  │  │  └─ HelpdeskService.java
   │  │  ├─ tool
   │  │  │  ├─ DateTimeTools.java
   │  │  │  ├─ CustomerTools.java
   │  │  │  └─ CompanyRuleTools.java
   │  │  └─ mcp
   │  │     ├─ McpToolCatalog.java
   │  │     └─ McpChatService.java
   │  └─ resources
   │     └─ application.yml
   └─ test/java/com/study/day05toolmcp
      └─ Day05ToolMcpApplicationTests.java
```

핵심은 이것입니다.

```text
Controller는 "어떤 주소로 들어왔는지"를 봅니다.
Service는 "그 요청을 어떻게 처리할지"를 정합니다.
Tool은 "AI가 필요하면 호출할 수 있는 함수"입니다.
MCP는 "외부 프로그램이 제공하는 Tool"입니다.
```

---

## 2. `Day05ToolMcpApplication.java`

역할: Spring Boot 앱의 시작 버튼입니다.

```java
// 이 파일이 속한 패키지입니다.
// 같은 패키지 아래에 있는 Controller, Service, Tool, Config를 Spring이 자동으로 찾습니다.
package com.study.day05toolmcp;

// Spring Boot 앱을 실행하기 위한 클래스입니다.
import org.springframework.boot.SpringApplication;

// "이 클래스가 Spring Boot 애플리케이션의 시작점이다"라고 알려주는 어노테이션입니다.
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 이 어노테이션 하나가 매우 중요합니다.
// Python으로 치면 FastAPI 앱 객체를 만들고 라우터를 등록하는 시작 지점과 비슷합니다.
@SpringBootApplication
public class Day05ToolMcpApplication {

    // Java 프로그램은 main 메서드에서 시작합니다.
    // Python의 if __name__ == "__main__": app.run() 같은 위치입니다.
    public static void main(String[] args) {

        // SpringApplication.run은 Spring Boot 서버를 켭니다.
        // 이 순간부터 @RestController, @Service, @Component, @Configuration을 찾아서 준비합니다.
        SpringApplication.run(Day05ToolMcpApplication.class, args);
    }
}
```

실행 흐름입니다.

```text
0ms   main() 실행
50ms  SpringApplication.run() 호출
300ms @Service, @Component, @RestController 객체 생성
1s    ChatClient, Tool, Memory, MCP 설정 준비
2s    Tomcat 서버가 8080 포트에서 요청 대기
```

---

## 3. `AiController.java`

역할: 외부 요청을 받는 입구입니다.

브루노에서 `GET http://localhost:8080/api/ask?question=안녕`을 보내면, 가장 먼저 이 파일의 메서드가 실행됩니다.

```java
// 컨트롤러가 속한 패키지입니다.
package com.study.day05toolmcp;

// MCP 관련 AI 서비스를 가져옵니다.
// /api/mcp-chat, /api/mixed-chat 같은 주소에서 사용합니다.
import com.study.day05toolmcp.mcp.McpChatService;

// 기본 AI 채팅 서비스입니다.
import com.study.day05toolmcp.service.ChatService;

// Tool + Memory가 결합된 장기요양 헬퍼 서비스입니다.
import com.study.day05toolmcp.service.HelpdeskService;

// 로컬 Tool을 붙여서 대화하는 서비스입니다.
import com.study.day05toolmcp.service.ToolChatService;

// GET 요청 주소를 만드는 어노테이션입니다.
import org.springframework.web.bind.annotation.GetMapping;

// URL 뒤의 ?question=... 값을 꺼내기 위한 어노테이션입니다.
import org.springframework.web.bind.annotation.RequestParam;

// 이 클래스가 REST API 요청을 받는 컨트롤러라고 Spring에게 알려줍니다.
import org.springframework.web.bind.annotation.RestController;

// @RestController는 "이 클래스는 HTTP 요청을 받고 문자열/JSON을 바로 응답한다"는 뜻입니다.
// Python Flask의 @app.route들이 모여 있는 파일과 비슷합니다.
@RestController
public class AiController {

    // 기본 채팅 담당 서비스입니다.
    private final ChatService chatService;

    // 로컬 Tool Calling 담당 서비스입니다.
    private final ToolChatService toolChatService;

    // Tool + Chat Memory를 함께 쓰는 상담 서비스입니다.
    private final HelpdeskService helpdeskService;

    // MCP 서버에서 가져온 외부 Tool을 사용하는 서비스입니다.
    private final McpChatService mcpChatService;

    // 생성자입니다.
    // Spring이 ChatService, ToolChatService, HelpdeskService, McpChatService 객체를 자동으로 넣어줍니다.
    // Python으로 치면 app 시작 시 service 객체들을 만들어서 controller에 넘겨주는 구조입니다.
    public AiController(
            ChatService chatService,
            ToolChatService toolChatService,
            HelpdeskService helpdeskService,
            McpChatService mcpChatService
    ) {
        // 생성자로 받은 ChatService를 이 클래스 내부 변수에 저장합니다.
        this.chatService = chatService;

        // 생성자로 받은 ToolChatService를 저장합니다.
        this.toolChatService = toolChatService;

        // 생성자로 받은 HelpdeskService를 저장합니다.
        this.helpdeskService = helpdeskService;

        // 생성자로 받은 McpChatService를 저장합니다.
        this.mcpChatService = mcpChatService;
    }

    // GET /api/ask 주소를 처리합니다.
    // 예: /api/ask?question=안녕
    @GetMapping("/api/ask")
    public String ask(@RequestParam String question) {

        // Controller는 직접 AI를 호출하지 않습니다.
        // 실제 작업은 ChatService에게 맡깁니다.
        return chatService.ask(question);
    }

    // GET /api/tool/datetime 주소를 처리합니다.
    // 현재 시간 Tool을 AI에게 붙여서 질문합니다.
    @GetMapping("/api/tool/datetime")
    public String toolDatetime(@RequestParam String question) {
        return toolChatService.ask(question);
    }

    // GET /api/tool/customer 주소를 처리합니다.
    // 이름은 customer지만, 우리 프로젝트에서는 수급자 정보를 조회하는 Tool입니다.
    @GetMapping("/api/tool/customer")
    public String toolCustomer(@RequestParam String question) {
        return toolChatService.toolRecipient(question);
    }

    // GET /api/tool/recipient 주소를 처리합니다.
    // customer보다 의미가 더 정확한 별칭 주소입니다.
    @GetMapping("/api/tool/recipient")
    public String toolRecipient(@RequestParam String question) {
        return toolChatService.toolRecipient(question);
    }

    // GET /api/tool/rule 주소를 처리합니다.
    // 장기요양 돌봄 규칙 Tool을 사용합니다.
    @GetMapping("/api/tool/rule")
    public String toolRule(@RequestParam String question) {
        return toolChatService.toolRule(question);
    }

    // GET /api/tool/chat 주소를 처리합니다.
    // 시간 + 수급자 + 돌봄 규칙 Tool을 한꺼번에 붙입니다.
    @GetMapping("/api/tool/chat")
    public String toolChat(@RequestParam String question) {
        return toolChatService.chat(question);
    }

    // GET /api/tool-chat 주소를 처리합니다.
    // 수업 자료나 테스트에서 짧게 부르기 위한 별칭 주소입니다.
    @GetMapping("/api/tool-chat")
    public String toolChatAlias(@RequestParam String question) {
        return toolChatService.chat(question);
    }

    // GET /api/tool/help 주소를 처리합니다.
    // conversationId가 있으면 같은 대화를 이어서 기억합니다.
    @GetMapping("/api/tool/help")
    public String helpChat(
            @RequestParam String question,
            @RequestParam(defaultValue = "care-demo") String conversationId
    ) {
        return helpdeskService.chat(question, conversationId);
    }

    // GET /api/assistant 주소를 처리합니다.
    // 오늘 과제에서 가장 중요한 주소입니다.
    // Tool Calling과 Chat Memory가 함께 들어갑니다.
    @GetMapping("/api/assistant")
    public String assistant(
            @RequestParam String question,
            @RequestParam(defaultValue = "care-demo") String conversationId
    ) {
        return helpdeskService.chat(question, conversationId);
    }

    // GET /api/mcp/filesystem 주소를 처리합니다.
    // MCP filesystem 서버가 읽을 수 있는 mcp-sandbox 문서를 대상으로 질문합니다.
    @GetMapping("/api/mcp/filesystem")
    public String mcpFilesystem(@RequestParam String question) {
        return mcpChatService.chatFilesystem(question);
    }

    // GET /api/mcp/fetch 주소를 처리합니다.
    // MCP fetch 서버를 통해 웹 문서/URL 관련 작업을 시도합니다.
    @GetMapping("/api/mcp/fetch")
    public String mcpFetch(@RequestParam String question) {
        return mcpChatService.chatFetch(question);
    }

    // GET /api/mcp-chat 주소를 처리합니다.
    // 등록된 MCP 도구 전체를 AI에게 붙입니다.
    @GetMapping("/api/mcp-chat")
    public String mcpChat(@RequestParam String question) {
        return mcpChatService.chatAllMcp(question);
    }

    // GET /api/mixed-chat 주소를 처리합니다.
    // 로컬 Tool과 MCP Tool을 함께 붙입니다.
    @GetMapping("/api/mixed-chat")
    public String mixedChat(@RequestParam String question) {
        return mcpChatService.chatMixed(question);
    }
}
```

Controller를 읽을 때는 이렇게 보면 됩니다.

```text
주소 1개 = 메서드 1개

/api/ask          -> ask()
/api/assistant    -> assistant()
/api/mixed-chat   -> mixedChat()
```

---

## 4. `ChatService.java`

역할: Tool 없이 가장 기본 AI 대화만 담당합니다.

```java
// 이 서비스가 속한 패키지입니다.
package com.study.day05toolmcp.service;

// Spring AI의 핵심 대화 객체입니다.
import org.springframework.ai.chat.client.ChatClient;

// 이 클래스를 Spring Bean으로 등록합니다.
import org.springframework.stereotype.Service;

// @Service는 "이 클래스는 비즈니스 로직을 처리하는 서비스다"라는 뜻입니다.
// Python으로 치면 chat_service.py 같은 파일입니다.
@Service
public class ChatService {

    // ChatClient는 AI에게 질문을 보내는 객체입니다.
    // final은 생성자에서 한 번 넣으면 바꾸지 않겠다는 뜻입니다.
    private final ChatClient chatClient;

    // 생성자입니다.
    // Spring이 ChatClient.Builder를 자동으로 넣어줍니다.
    public ChatService(ChatClient.Builder builder) {

        // Builder로 ChatClient 객체를 만듭니다.
        // Python으로 치면 client = ChatClient(config)처럼 객체를 만드는 단계입니다.
        this.chatClient = builder.build();
    }

    // Controller에서 받은 question을 AI에게 그대로 보냅니다.
    public String ask(String question) {

        // prompt()는 "이제 AI 요청을 만들기 시작한다"는 뜻입니다.
        return chatClient.prompt()

                // user(question)은 사용자의 질문을 넣는 부분입니다.
                .user(question)

                // call()은 실제로 AI 모델에게 요청을 보내는 부분입니다.
                .call()

                // content()는 AI 응답 본문 문자열만 꺼내는 부분입니다.
                .content();
    }
}
```

실행 흐름입니다.

```text
0ms   /api/ask?question=안녕 요청
10ms  AiController.ask() 실행
15ms  ChatService.ask("안녕") 호출
20ms  chatClient.prompt().user("안녕") 요청 생성
100ms call()로 Gemini 호출
1s    content()로 응답 문자열 반환
```

---

## 5. `DateTimeTools.java`

역할: AI가 현재 시간을 알고 싶을 때 호출할 수 있는 도구입니다.

```java
// Tool 클래스가 속한 패키지입니다.
package com.study.day05toolmcp.tool;

// @Tool은 "AI가 호출할 수 있는 함수"라는 뜻입니다.
import org.springframework.ai.tool.annotation.Tool;

// 현재 사용자의 시간대를 가져오기 위해 사용합니다.
import org.springframework.context.i18n.LocaleContextHolder;

// 이 클래스를 Spring Bean으로 등록합니다.
import org.springframework.stereotype.Component;

// 날짜와 시간을 표현하는 Java 클래스입니다.
import java.time.LocalDateTime;

// @Component는 Spring이 이 클래스를 자동으로 객체로 만들어 관리한다는 뜻입니다.
// @Service와 비슷하지만, 더 일반적인 부품이라는 느낌입니다.
@Component
public class DateTimeTools {

    // @Tool이 붙은 메서드는 AI가 필요하다고 판단하면 호출할 수 있습니다.
    // 여기서는 "현재 날짜와 시간을 알려줘" 같은 질문에 사용됩니다.
    @Tool(description = "현재 날짜와 시간을 반환하는 도구")
    String getCurrentDateTime() {

        // 현재 로컬 시간을 가져옵니다.
        return LocalDateTime.now()

                // Spring의 현재 시간대 정보에 맞춰 ZonedDateTime으로 바꿉니다.
                .atZone(LocaleContextHolder.getTimeZone().toZoneId())

                // 문자열로 바꿔서 AI에게 돌려줍니다.
                .toString();
    }
}
```

Tool Calling의 느낌은 이렇습니다.

```text
사용자: 오늘 밤 돌봄 기록에 날짜도 넣어줘.
AI: 현재 날짜가 필요하네?
AI -> 앱: getCurrentDateTime() 실행해줘.
앱: 2026-07-08T...
AI: 그 날짜를 포함해서 답변 작성.
```

---

## 6. `CustomerTools.java`

역할: 수급자 ID로 장기요양 대상자의 상태 정보를 조회하는 도구입니다.

파일 이름은 수업 자료 호환 때문에 `CustomerTools`지만, 우리 프로젝트 의미로는 `RecipientTools`에 가깝습니다.

```java
// Tool 클래스가 속한 패키지입니다.
package com.study.day05toolmcp.tool;

// AI가 호출 가능한 Tool 메서드 표시입니다.
import org.springframework.ai.tool.annotation.Tool;

// Tool 인자 설명을 붙일 때 사용합니다.
import org.springframework.ai.tool.annotation.ToolParam;

// Spring Bean 등록용입니다.
import org.springframework.stereotype.Component;

// 임시 데이터 저장용 Map입니다.
import java.util.Map;

// 이 클래스를 Spring이 자동으로 만들고 관리합니다.
@Component
public class CustomerTools {

    // record는 Java의 간단한 데이터 묶음입니다.
    // Python으로 치면 dataclass와 비슷합니다.
    public record RecipientProfile(
            String recipientId,
            String name,
            String careLevel,
            String mobility,
            String mealSupport,
            String medication,
            String nightCareNote,
            String guardianContactPriority
    ) {
    }

    // 실제 DB 대신 실습용 고정 데이터를 Map에 넣었습니다.
    // 나중에 JPA Repository나 MariaDB/H2 조회로 바꿀 수 있습니다.
    private static final Map<String, RecipientProfile> RECIPIENTS = Map.of(

            // R001 수급자 정보입니다.
            "R001", new RecipientProfile(
                    "R001",
                    "김영자",
                    "장기요양 3등급",
                    "보행기 사용, 야간 이동 시 낙상 주의",
                    "죽/부드러운 반찬 선호, 물 섭취 확인 필요",
                    "취침 전 혈압약 복용 확인",
                    "새벽 2시 전후 화장실 이동 요청이 잦음",
                    "낙상 또는 복약 누락 시 보호자 즉시 연락"
            ),

            // R002 수급자 정보입니다.
            "R002", new RecipientProfile(
                    "R002",
                    "박문수",
                    "장기요양 4등급",
                    "실내 보행 가능, 계단 이동 금지",
                    "일반식 가능, 당 조절 간식 필요",
                    "식후 당뇨약 복용 확인",
                    "야간 불안감 호소 시 조명 켜고 안정 유도",
                    "응급상황 외에는 다음날 오전 보고"
            ),

            // R003 수급자 정보입니다.
            "R003", new RecipientProfile(
                    "R003",
                    "이순덕",
                    "장기요양 2등급",
                    "휠체어 이동, 침대-휠체어 이동 보조 필요",
                    "연하 주의, 식사 중 기침 여부 확인",
                    "저녁 식후 소화제 복용 확인",
                    "야간 체위 변경과 욕창 부위 확인 필요",
                    "호흡곤란, 식사 중 사레 반복 시 즉시 연락"
            )
    );

    // 이 메서드는 AI가 수급자 정보를 알아야 할 때 호출할 수 있습니다.
    @Tool(description = "수급자 ID로 장기요양 등급, 이동, 식사, 복약, 야간 돌봄 주의사항을 조회한다")
    RecipientProfile getRecipientProfile(

            // AI에게 recipientId 인자가 무엇인지 설명합니다.
            @ToolParam(description = "수급자 ID. 예: R001, R002, R003")
            String recipientId
    ) {

        // Map에서 수급자 ID로 프로필을 찾습니다.
        RecipientProfile profile = RECIPIENTS.get(recipientId);

        // 등록되지 않은 ID가 들어오면 null을 바로 반환하지 않고 안전한 안내 데이터를 반환합니다.
        if (profile == null) {
            return new RecipientProfile(
                    recipientId,
                    "미확인",
                    "미확인",
                    "등록된 이동 정보 없음",
                    "등록된 식사 정보 없음",
                    "등록된 복약 정보 없음",
                    "등록된 야간 돌봄 정보 없음",
                    "담당자 확인 필요"
            );
        }

        // 찾은 수급자 정보를 AI에게 돌려줍니다.
        return profile;
    }
}
```

이 파일을 이해하는 핵심입니다.

```text
AI가 직접 DB를 뒤지는 게 아닙니다.
AI는 "이 Tool을 실행해줘"라고 요청합니다.
실제 실행은 Spring 애플리케이션이 합니다.
```

---

## 7. `CompanyRuleTools.java`

역할: 장기요양 돌봄 주제별 주의사항을 조회하는 도구입니다.

```java
// Tool 클래스가 속한 패키지입니다.
package com.study.day05toolmcp.tool;

// AI 호출 가능 메서드 표시입니다.
import org.springframework.ai.tool.annotation.Tool;

// Tool 인자 설명입니다.
import org.springframework.ai.tool.annotation.ToolParam;

// Spring Bean 등록입니다.
import org.springframework.stereotype.Component;

// 주제와 규칙을 묶기 위한 Map입니다.
import java.util.Map;

// Spring이 자동으로 객체를 만들어줍니다.
@Component
public class CompanyRuleTools {

    // 실습용 규칙 데이터입니다.
    // 지금은 Map이지만, 나중에는 DB 테이블이나 문서 검색으로 바꿀 수 있습니다.
    private static final Map<String, String> RULES = Map.of(
            "야간돌봄",
            "야간에는 낙상 예방이 최우선입니다. 이동 요청 시 먼저 조명을 켜고, 보행 보조기구와 미끄럼 위험을 확인한 뒤 동행합니다.",

            "복약관리",
            "요양보호사는 처방 변경을 임의로 판단하지 않습니다. 복약 여부를 확인하고 누락·거부·이상반응은 기록 후 담당자에게 보고합니다.",

            "식사보조",
            "식사 중 사레, 기침, 삼킴 지연이 반복되면 즉시 식사를 중단하고 상태를 기록한 뒤 담당자에게 공유합니다.",

            "이동지원",
            "침대에서 휠체어로 이동할 때는 브레이크 고정, 발판 정리, 수급자 자세 확인 후 천천히 보조합니다.",

            "응급연락",
            "호흡곤란, 의식저하, 반복 낙상, 흉통, 심한 출혈이 있으면 보호자보다 119 또는 기관 비상연락 체계를 우선합니다.",

            "보호자상담",
            "보호자에게는 확인된 사실, 관찰 시간, 조치 내용을 구분해 전달하고 추측성 진단 표현은 피합니다."
    );

    // AI가 돌봄 규칙이 필요할 때 호출할 수 있는 메서드입니다.
    @Tool(description = "돌봄 주제별 장기요양 상담/케어 주의사항을 조회한다. 주제: 야간돌봄, 복약관리, 식사보조, 이동지원, 응급연락, 보호자상담")
    String getCareRule(

            // AI에게 topic 값으로 어떤 예시가 들어올 수 있는지 알려줍니다.
            @ToolParam(description = "돌봄 규칙 주제. 예: 야간돌봄, 복약관리, 식사보조, 이동지원, 응급연락, 보호자상담")
            String topic
    ) {

        // Map에서 topic에 해당하는 규칙을 찾습니다.
        // 없으면 안전하게 "등록되지 않았다"는 메시지를 반환합니다.
        return RULES.getOrDefault(
                topic,
                "해당 주제의 돌봄 규칙은 등록되어 있지 않습니다. 확인된 자료나 담당자에게 추가 확인이 필요합니다."
        );
    }
}
```

Python으로 치면 이런 함수입니다.

```python
RULES = {"야간돌봄": "낙상 예방이 최우선입니다."}

def get_care_rule(topic: str):
    return RULES.get(topic, "등록된 규칙 없음")
```

---

## 8. `ToolChatService.java`

역할: 로컬 Tool을 ChatClient에 붙여서 AI가 도구를 사용할 수 있게 합니다.

```java
// 이 서비스가 속한 패키지입니다.
package com.study.day05toolmcp.service;

// 로컬 Tool 클래스들을 가져옵니다.
import com.study.day05toolmcp.tool.CompanyRuleTools;
import com.study.day05toolmcp.tool.CustomerTools;
import com.study.day05toolmcp.tool.DateTimeTools;

// Spring AI의 대화 클라이언트입니다.
import org.springframework.ai.chat.client.ChatClient;

// 요청/응답 로그를 보기 위한 Advisor입니다.
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;

// 이 클래스를 Service Bean으로 등록합니다.
import org.springframework.stereotype.Service;

// Tool Calling을 담당하는 서비스입니다.
@Service
public class ToolChatService {

    // AI 대화 클라이언트입니다.
    private final ChatClient chatClient;

    // 현재 시간 Tool입니다.
    private final DateTimeTools dateTimeTools;

    // 수급자 정보 Tool입니다.
    private final CustomerTools customerTools;

    // 돌봄 규칙 Tool입니다.
    private final CompanyRuleTools companyRuleTools;

    // 생성자입니다.
    // Spring이 ChatClient.Builder와 Tool 객체들을 자동으로 넣어줍니다.
    public ToolChatService(
            ChatClient.Builder builder,
            DateTimeTools dateTimeTools,
            CustomerTools customerTools,
            CompanyRuleTools companyRuleTools
    ) {

        // ChatClient를 만들면서 기본 시스템 프롬프트와 로그 Advisor를 붙입니다.
        this.chatClient = builder

                // system은 AI의 역할을 정하는 지시문입니다.
                .defaultSystem("""
                        당신은 장기요양 상담을 돕는 AI 헬퍼입니다.
                        답변할 때는 확인된 도구 결과와 일반 안내를 구분하고,
                        의료적·법적 최종 판단은 사람이 확인해야 한다고 안내하세요.
                        """)

                // SimpleLoggerAdvisor는 AI 요청/응답 흐름을 로그로 볼 수 있게 도와줍니다.
                .defaultAdvisors(new SimpleLoggerAdvisor())

                // 최종 ChatClient 객체를 만듭니다.
                .build();

        // 생성자로 받은 Tool 객체를 필드에 저장합니다.
        this.dateTimeTools = dateTimeTools;
        this.customerTools = customerTools;
        this.companyRuleTools = companyRuleTools;
    }

    // 현재 시간 Tool만 붙여서 질문합니다.
    public String ask(String question) {
        return chatClient.prompt()
                .user(question)

                // AI가 필요하면 dateTimeTools 안의 @Tool 메서드를 호출할 수 있습니다.
                .tools(dateTimeTools)
                .call()
                .content();
    }

    // 수급자 정보 Tool만 붙여서 질문합니다.
    public String toolRecipient(String question) {
        return chatClient.prompt()
                .user(question)
                .tools(customerTools)
                .call()
                .content();
    }

    // 돌봄 규칙 Tool만 붙여서 질문합니다.
    public String toolRule(String question) {
        return chatClient.prompt()
                .user(question)
                .tools(companyRuleTools)
                .call()
                .content();
    }

    // 시간 + 수급자 + 규칙 Tool을 모두 붙여서 질문합니다.
    public String chat(String question) {
        return chatClient.prompt()
                .user(question)

                // 여러 Tool 객체를 한 번에 붙입니다.
                .tools(dateTimeTools, customerTools, companyRuleTools)
                .call()
                .content();
    }
}
```

이 파일의 핵심은 `.tools(...)`입니다.

```text
.tools(dateTimeTools)
= AI에게 "필요하면 이 도구를 써도 돼"라고 허락하는 것
```

---

## 9. `ChatMemoryConfig.java`

역할: 대화 기억 저장소를 설정합니다.

```java
// 설정 클래스가 속한 패키지입니다.
package com.study.day05toolmcp.config;

// ChatMemory 인터페이스입니다.
import org.springframework.ai.chat.memory.ChatMemory;

// 메모리에 대화를 저장하는 저장소입니다.
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;

// 최근 N개 메시지를 기억하는 ChatMemory 구현체입니다.
import org.springframework.ai.chat.memory.MessageWindowChatMemory;

// Bean 등록 어노테이션입니다.
import org.springframework.context.annotation.Bean;

// 설정 클래스 표시입니다.
import org.springframework.context.annotation.Configuration;

// @Configuration은 "이 클래스는 Spring 설정 파일이다"라는 뜻입니다.
@Configuration
public class ChatMemoryConfig {

    // @Bean은 이 메서드가 반환하는 객체를 Spring 컨테이너에 등록하라는 뜻입니다.
    // 이름은 inMemoryChatMemory입니다.
    @Bean("inMemoryChatMemory")
    public ChatMemory inMemoryChatMemory() {

        // MessageWindowChatMemory는 최근 대화 일부만 기억합니다.
        return MessageWindowChatMemory.builder()

                // 실제 저장소는 메모리 저장소입니다.
                // 서버를 끄면 내용은 사라집니다.
                .chatMemoryRepository(new InMemoryChatMemoryRepository())

                // 최근 20개 메시지까지만 기억합니다.
                .maxMessages(20)

                // ChatMemory 객체를 완성합니다.
                .build();
    }
}
```

중요한 점입니다.

```text
InMemory = RAM에 저장
서버 재시작 = 기억 사라짐
```

나중에 DB 기억으로 바꾸면 이 Config 쪽이 바뀝니다.

---

## 10. `HelpdeskService.java`

역할: Tool Calling과 Chat Memory를 함께 사용하는 핵심 상담 서비스입니다.

```java
// 서비스 패키지입니다.
package com.study.day05toolmcp.service;

// 로컬 Tool들을 가져옵니다.
import com.study.day05toolmcp.tool.CompanyRuleTools;
import com.study.day05toolmcp.tool.CustomerTools;
import com.study.day05toolmcp.tool.DateTimeTools;

// Spring AI 대화 클라이언트입니다.
import org.springframework.ai.chat.client.ChatClient;

// 대화 기억을 자동으로 붙여주는 Advisor입니다.
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;

// 로그 Advisor입니다.
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;

// 대화 기억 인터페이스입니다.
import org.springframework.ai.chat.memory.ChatMemory;

// 특정 이름의 Bean을 선택할 때 사용합니다.
import org.springframework.beans.factory.annotation.Qualifier;

// Service 등록입니다.
import org.springframework.stereotype.Service;

// Tool + Memory 상담 서비스입니다.
@Service
public class HelpdeskService {

    // AI 대화 클라이언트입니다.
    private final ChatClient chatClient;

    // 현재 시간 Tool입니다.
    private final DateTimeTools dateTimeTools;

    // 수급자 정보 Tool입니다.
    private final CustomerTools customerTools;

    // 돌봄 규칙 Tool입니다.
    private final CompanyRuleTools companyRuleTools;

    // 생성자입니다.
    public HelpdeskService(
            ChatClient.Builder builder,
            DateTimeTools dateTimeTools,
            CustomerTools customerTools,
            CompanyRuleTools companyRuleTools,

            // ChatMemoryConfig에서 만든 inMemoryChatMemory Bean을 정확히 가져옵니다.
            @Qualifier("inMemoryChatMemory") ChatMemory chatMemory
    ) {

        // ChatClient를 만들면서 system prompt와 advisor들을 기본으로 붙입니다.
        this.chatClient = builder
                .defaultSystem("""
                        당신은 장기요양 상담을 돕는 AI 헬퍼입니다.
                        보호자나 요양보호사의 질문에 답할 때 필요하면 도구로 수급자 상태, 돌봄 규칙, 현재 시각을 확인하세요.
                        확인된 도구 결과와 일반 안내를 구분하고, 의료적·법적 최종 판단은 담당자나 전문기관 확인이 필요하다고 안내하세요.
                        정중하고 간결한 한국어로 답변하세요.
                        """)

                // ChatMemoryAdvisor는 conversationId별로 이전 대화를 찾아 다음 요청에 다시 붙입니다.
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())

                // 로그 확인용 Advisor입니다.
                .defaultAdvisors(new SimpleLoggerAdvisor())

                // 최종 ChatClient 생성입니다.
                .build();

        // Tool 객체들을 저장합니다.
        this.dateTimeTools = dateTimeTools;
        this.customerTools = customerTools;
        this.companyRuleTools = companyRuleTools;
    }

    // 실제 상담 메서드입니다.
    // question은 사용자 질문, conversationId는 대화방 ID입니다.
    public String chat(String question, String conversationId) {
        return chatClient.prompt()

                // 사용자 질문을 넣습니다.
                .user(question)

                // Tool들을 AI에게 사용 가능하도록 붙입니다.
                .tools(dateTimeTools, customerTools, companyRuleTools)

                // Advisor에게 conversationId를 알려줍니다.
                // 같은 conversationId면 이전 대화를 이어서 기억합니다.
                .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, conversationId))

                // AI 호출입니다.
                .call()

                // 응답 본문만 꺼냅니다.
                .content();
    }
}
```

Chat Memory는 이렇게 동작합니다.

```text
1턴: conversationId=care-demo, "R001 야간 주의사항 알려줘"
     -> 메모리에 질문과 답변 저장

2턴: conversationId=care-demo, "방금 그 어르신 보호자에게 뭐라고 말하지?"
     -> 이전 대화가 함께 전달됨
     -> AI가 "R001"을 다시 알 수 있음
```

---

## 11. `McpToolCatalog.java`

역할: MCP 서버에서 가져온 Tool들을 분류해서 보관합니다.

MCP는 쉽게 말해 "외부 프로그램이 Tool을 제공하는 표준 방식"입니다.

```java
// MCP 관련 클래스가 속한 패키지입니다.
package com.study.day05toolmcp.mcp;

// MCP 서버와 동기 방식으로 통신하는 클라이언트입니다.
import io.modelcontextprotocol.client.McpSyncClient;

// MCP Tool을 Spring AI ToolCallback으로 바꿔주는 클래스입니다.
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;

// ChatClient에 붙일 수 있는 Tool 형태입니다.
import org.springframework.ai.tool.ToolCallback;

// Spring Bean 등록입니다.
import org.springframework.stereotype.Component;

// List 사용입니다.
import java.util.List;

// MCP Tool들을 한곳에 모아두는 카탈로그입니다.
@Component
public class McpToolCatalog {

    // filesystem MCP 서버에서 가져온 Tool 목록입니다.
    private final ToolCallback[] filesystemTools;

    // fetch MCP 서버에서 가져온 Tool 목록입니다.
    private final ToolCallback[] fetchTools;

    // 모든 MCP 서버의 Tool 목록입니다.
    private final ToolCallback[] allTools;

    // Spring이 현재 연결된 MCP 클라이언트 목록을 넣어줍니다.
    public McpToolCatalog(List<McpSyncClient> mcpClients) {

        // 서버 이름이 filesystem인 클라이언트만 골라 Tool로 바꿉니다.
        this.filesystemTools = toolsFrom(namedClients(mcpClients, "filesystem"));

        // 서버 이름이 fetch인 클라이언트만 골라 Tool로 바꿉니다.
        this.fetchTools = toolsFrom(namedClients(mcpClients, "fetch"));

        // 전체 MCP 클라이언트를 Tool로 바꿉니다.
        this.allTools = toolsFrom(mcpClients);
    }

    // 특정 이름의 MCP 서버만 필터링합니다.
    private List<McpSyncClient> namedClients(List<McpSyncClient> mcpClients, String serverName) {
        return mcpClients.stream()

                // MCP 서버 정보의 name이 원하는 이름과 같은지 확인합니다.
                .filter(client -> serverName.equals(client.getServerInfo().name()))

                // 필터링 결과를 List로 모읍니다.
                .toList();
    }

    // MCP 클라이언트를 ChatClient에 붙일 수 있는 ToolCallback 배열로 변환합니다.
    private ToolCallback[] toolsFrom(List<McpSyncClient> mcpClients) {
        return SyncMcpToolCallbackProvider.builder()

                // MCP 클라이언트 목록을 전달합니다.
                .mcpClients(mcpClients)

                // Provider 객체를 만듭니다.
                .build()

                // 실제 ToolCallback 배열을 꺼냅니다.
                .getToolCallbacks();
    }

    // filesystem Tool 배열을 반환합니다.
    public ToolCallback[] filesystemTools() {
        return filesystemTools;
    }

    // fetch Tool 배열을 반환합니다.
    public ToolCallback[] fetchTools() {
        return fetchTools;
    }

    // 전체 MCP Tool 배열을 반환합니다.
    public ToolCallback[] allTools() {
        return allTools;
    }
}
```

이 파일은 초보자에게 가장 어려운 편입니다. 핵심만 보면 됩니다.

```text
MCP 서버들 연결됨
        |
McpSyncClient 목록이 생김
        |
ToolCallback[]로 변환
        |
ChatClient.tools(...)에 붙임
```

---

## 12. `McpChatService.java`

역할: MCP Tool을 AI 대화에 붙이는 서비스입니다.

```java
// MCP 서비스 패키지입니다.
package com.study.day05toolmcp.mcp;

// 로컬 Tool들을 가져옵니다.
import com.study.day05toolmcp.tool.CompanyRuleTools;
import com.study.day05toolmcp.tool.CustomerTools;
import com.study.day05toolmcp.tool.DateTimeTools;

// Spring AI 대화 클라이언트입니다.
import org.springframework.ai.chat.client.ChatClient;

// Service 등록입니다.
import org.springframework.stereotype.Service;

// 배열 처리를 위해 사용합니다.
import java.util.Arrays;

// Stream 결합을 위해 사용합니다.
import java.util.stream.Stream;

// MCP 대화 서비스입니다.
@Service
public class McpChatService {

    // AI 대화 클라이언트입니다.
    private final ChatClient chatClient;

    // MCP Tool 모음입니다.
    private final McpToolCatalog catalog;

    // 로컬 시간 Tool입니다.
    private final DateTimeTools dateTimeTools;

    // 로컬 수급자 Tool입니다.
    private final CustomerTools customerTools;

    // 로컬 규칙 Tool입니다.
    private final CompanyRuleTools companyRuleTools;

    // 생성자입니다.
    public McpChatService(
            ChatClient.Builder builder,
            McpToolCatalog catalog,
            DateTimeTools dateTimeTools,
            CustomerTools customerTools,
            CompanyRuleTools companyRuleTools
    ) {

        // MCP용 ChatClient를 만듭니다.
        this.chatClient = builder
                .defaultSystem("""
                        당신은 장기요양 상담을 돕는 AI 헬퍼입니다.
                        MCP 문서 도구를 사용할 때는 문서에서 확인한 내용과 일반 안내를 구분하세요.
                        """)
                .build();

        // 필요한 객체들을 필드에 저장합니다.
        this.catalog = catalog;
        this.dateTimeTools = dateTimeTools;
        this.customerTools = customerTools;
        this.companyRuleTools = companyRuleTools;
    }

    // filesystem MCP Tool만 사용합니다.
    public String chatFilesystem(String question) {
        return chatClient.prompt()
                .user(question)
                .tools(catalog.filesystemTools())
                .call()
                .content();
    }

    // fetch MCP Tool만 사용합니다.
    public String chatFetch(String question) {
        return chatClient.prompt()
                .user(question)
                .tools(catalog.fetchTools())
                .call()
                .content();
    }

    // 모든 MCP Tool을 사용합니다.
    public String chatAllMcp(String question) {
        return chatClient.prompt()
                .user(question)
                .tools(catalog.allTools())
                .call()
                .content();
    }

    // 로컬 Tool과 MCP Tool을 함께 사용합니다.
    public String chatMixed(String question) {

        // 로컬 Tool 객체 3개와 MCP Tool 배열을 하나의 Object[]로 합칩니다.
        Object[] tools = Stream.concat(
                        Stream.of(dateTimeTools, customerTools, companyRuleTools),
                        Arrays.stream(catalog.allTools())
                )
                .toArray();

        // 합쳐진 Tool 전체를 ChatClient에 붙입니다.
        return chatClient.prompt()
                .user(question)
                .tools(tools)
                .call()
                .content();
    }
}
```

`chatMixed`가 오늘 과제의 확장 포인트입니다.

```text
로컬 Tool: 현재 시간, 수급자 정보, 돌봄 규칙
MCP Tool: 파일 읽기, 웹 fetch

mixed-chat = 둘 다 붙인 AI
```

---

## 13. `application.yml`

역할: Spring Boot와 Spring AI 설정 파일입니다.

```yaml
# Spring 관련 설정 묶음입니다.
spring:

  # 애플리케이션 이름입니다.
  application:
    name: day05-tool-mcp

  # Spring AI 관련 설정입니다.
  ai:

    # MCP 클라이언트 설정입니다.
    mcp:
      client:

        # MCP 서버 응답을 기다리는 최대 시간입니다.
        request-timeout: 60s

        # stdio 방식 MCP 서버 연결입니다.
        # stdio는 표준입출력으로 외부 프로세스와 통신한다는 뜻입니다.
        stdio:
          connections:

            # filesystem이라는 이름의 MCP 서버입니다.
            filesystem:

              # Windows에서는 npx.cmd로 실행합니다.
              command: npx.cmd

              # npx에 넘길 인자들입니다.
              args:
                - "-y"
                - "@modelcontextprotocol/server-filesystem"

                # 이 폴더 안의 파일만 MCP가 읽을 수 있습니다.
                - "${user.dir}/mcp-sandbox"

            # fetch라는 이름의 MCP 서버입니다.
            fetch:

              # uvx로 Python 기반 MCP fetch 서버를 실행합니다.
              command: uvx.exe

              # 실행할 패키지 이름입니다.
              args:
                - "mcp-server-fetch"

    # Google Gemini 설정입니다.
    google:
      genai:
        chat:

          # 사용할 Gemini 모델 이름입니다.
          model: gemini-3.1-flash-lite

        # API Key는 환경변수 GOOGLE_API_KEY에서 가져옵니다.
        # 실제 키를 코드에 쓰면 절대 안 됩니다.
        api-key: ${GOOGLE_API_KEY}

  # 파일 업로드 설정입니다.
  # Day5에서는 핵심은 아니지만 프로젝트 기본 설정으로 남아 있습니다.
  servlet:
    multipart:
      max-file-size: 10MB
      max-request-size: 10MB

  # 에러 메시지를 응답에 포함할지 설정합니다.
  web:
    error:
      include-message: always

# 로그 레벨 설정입니다.
logging:
  level:

    # Spring AI Advisor 동작을 DEBUG로 보게 합니다.
    org.springframework.ai.chat.client.advisor: DEBUG
```

초보자 기준으로 제일 중요한 부분은 이것입니다.

```yaml
filesystem:
  command: npx.cmd
  args:
    - "-y"
    - "@modelcontextprotocol/server-filesystem"
    - "${user.dir}/mcp-sandbox"
```

이 뜻은 이겁니다.

```text
앱이 실행될 때 npx로 filesystem MCP 서버를 켭니다.
그 MCP 서버는 mcp-sandbox 폴더 안의 파일만 읽을 수 있습니다.
```

---

## 14. `Day05ToolMcpApplicationTests.java`

역할: Spring Boot가 최소한 뜨는지 확인하는 테스트입니다.

```java
// 테스트 클래스가 속한 패키지입니다.
package com.study.day05toolmcp;

// 테스트 메서드 표시입니다.
import org.junit.jupiter.api.Test;

// Spring Boot 전체 컨텍스트를 띄우는 테스트입니다.
import org.springframework.boot.test.context.SpringBootTest;

// Spring Boot 앱이 뜨는지 확인합니다.
// 여기서는 MCP 클라이언트를 꺼둡니다.
// 이유: 테스트할 때마다 npx, uvx 외부 서버까지 켜면 빌드가 불안정해질 수 있기 때문입니다.
@SpringBootTest(properties = "spring.ai.mcp.client.enabled=false")
class Day05ToolMcpApplicationTests {

    // 테스트 메서드입니다.
    // 내용이 비어 있어도 Spring Context가 뜨면 성공입니다.
    @Test
    void contextLoads() {
    }
}
```

중요한 포인트입니다.

```text
테스트에서는 MCP를 끔
실행에서는 MCP를 켬
```

---

## 15. 요청별 전체 흐름

### `/api/ask`

```text
Bruno
 -> AiController.ask()
 -> ChatService.ask()
 -> ChatClient
 -> Gemini
 -> 문자열 응답
```

### `/api/tool-chat`

```text
Bruno
 -> AiController.toolChatAlias()
 -> ToolChatService.chat()
 -> ChatClient.tools(dateTimeTools, customerTools, companyRuleTools)
 -> Gemini가 필요 시 Tool 호출 요청
 -> Spring 앱이 Tool 실제 실행
 -> Tool 결과를 Gemini에게 전달
 -> Gemini 최종 답변
```

### `/api/assistant`

```text
Bruno
 -> AiController.assistant()
 -> HelpdeskService.chat(question, conversationId)
 -> ChatMemoryAdvisor가 conversationId 기준 이전 대화 조회
 -> ChatClient.tools(...)
 -> Gemini
 -> 응답 저장
 -> 문자열 응답
```

### `/api/mcp-chat`

```text
Bruno
 -> AiController.mcpChat()
 -> McpChatService.chatAllMcp()
 -> McpToolCatalog.allTools()
 -> filesystem/fetch MCP Tool 연결
 -> Gemini가 필요 시 외부 Tool 호출
 -> 최종 응답
```

### `/api/mixed-chat`

```text
Bruno
 -> AiController.mixedChat()
 -> McpChatService.chatMixed()
 -> 로컬 Tool + MCP Tool 합치기
 -> ChatClient.tools(전체 도구)
 -> Gemini
 -> 최종 응답
```

---

## 16. 이 프로젝트를 백지에서 만든다면 순서

처음부터 만든다면 이 순서가 가장 자연스럽습니다.

```text
1. Spring Boot 프로젝트 생성
2. build.gradle에 Spring AI, MCP 의존성 추가
3. application.yml에 Gemini API Key, MCP 서버 설정
4. 기본 ChatService 생성
5. AiController에서 /api/ask 연결
6. DateTimeTools 생성
7. CustomerTools 생성
8. CompanyRuleTools 생성
9. ToolChatService에서 .tools(...) 연결
10. /api/tool-chat 주소 추가
11. ChatMemoryConfig 생성
12. HelpdeskService에서 Tool + Memory 결합
13. /api/assistant 주소 추가
14. mcp-sandbox 문서 생성
15. McpToolCatalog 생성
16. McpChatService 생성
17. /api/mcp-chat, /api/mixed-chat 주소 추가
18. Bruno로 주소별 검증
19. README와 학습 기록 정리
```

---

## 17. 한 문장 요약

이 프로젝트는 **Spring Boot가 API 입구를 만들고, Service가 Spring AI ChatClient를 호출하고, AI가 필요하면 로컬 Tool이나 MCP Tool을 요청해서 더 정확한 장기요양 상담 답변을 만드는 구조**입니다.

