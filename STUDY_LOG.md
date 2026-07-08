---
## [2026-07-02] Q: Python 흐름을 구술로 자세히 풀어줘

**질문**: 이 흐름을 구술로 자세히 풀어줘.

**핵심 개념**:
- 입력 → 처리 → 구조화 → 연결 → 반환: 프로그램이 돌아가는 가장 기본 흐름
- 함수와 클래스: 코드를 재사용 가능하게 묶는 방법

**Python 비교**:
```python
message = input("메시지 입력: ")

def process(text):
    if len(text) > 10:
        return text.upper()
    return text.lower()

result = process(message)
print(result)
```

**Java/Spring 연결**:
```java
@GetMapping("/hello")
public String hello(@RequestParam String message) {
    return service.process(message);
}
```

**흐름 타임라인**:
- 0ms: 사용자가 값을 넣음
- 20ms: 프로그램이 변수에 저장함
- 40ms: 조건/반복으로 가공함
- 60ms: 함수로 묶인 로직이 실행됨
- 80ms: 결과를 반환함

**한 줄 메모**:
- 코드는 결국 "받고, 처리하고, 돌려준다"로 이해하면 시작이 쉬워진다.

---
## [2026-07-02] Q: POJO가 뭐야? 자바 기업용은 또 머닝

**질문**: POJO가 뭐야? 자바 기업용은 또 머닝

**핵심 개념**:
- POJO: 특별한 프레임워크 상속 없이, 순수하게 만든 일반 자바 객체
- 기업용 자바: 웹 요청, DB, 보안, 트랜잭션 같은 큰 서비스 구조를 다루는 자바 생태계

**Python 비교**:
```python
class User:
    def __init__(self, name, age):
        self.name = name
        self.age = age
```

**Java/Spring 연결**:
```java
public class User {
    private String name;
    private int age;
}
```

**흐름 타임라인**:
- 0ms: 데이터를 담을 객체가 필요함
- 10ms: POJO를 하나 만듦
- 20ms: Spring이 이 객체를 요청/응답/DB와 연결해 씀

**한 줄 메모**:
- Python의 단순 클래스처럼, Java에서도 "데이터를 담는 기본 상자"가 먼저다.

---
## [2026-07-02] Q: Day1 수업 내용 page35-37을 수행해야 해. 읽고 같이 하나씩 해보자.

**질문**: 이게 오늘 수업 내용인데 page35-37의 내용을 수행해야 해. 읽고 같이 하나씩 해보자.

**핵심 개념**:
- Controller → Service → ChatClient: Spring AI Day1의 기본 호출 구조
- 엔드포인트: `/api/chat`, `/api/teacher`처럼 기능별 URL을 나눠서 사용

**Python 비교**:
```python
from flask import Flask, request

app = Flask(__name__)

@app.route("/chat")
def chat():
    message = request.args.get("message")
    return f"질문: {message}"
```

**Java/Spring 연결**:
```java
@GetMapping("/api/chat")
public String chat(@RequestParam String message) {
    return chatService.chat(message);
}
```

**흐름 타임라인**:
- 0ms: 브라우저가 `/api/chat?message=...` 요청
- 10ms: Controller가 `message`를 받음
- 20ms: Service가 AI 호출 로직 실행
- 100ms+: AI 응답이 반환됨

**한 줄 메모**:
- Day1은 "AI를 부르는 최소 구조"를 손으로 만들어보는 날이었다.

---
## [2026-07-02] Q: day01-chat-client가 IntelliJ에서 실행이 안 되네 왜 그럴까

**질문**: C:\Users\금정산2-PC02\p2-spring\day01-chat-client 여기서 프로젝트를 IntelliJ로 하는데 실행이 안되네 왜 그럴까

**핵심 개념**:
- 실행 문제는 코드 문제와 환경 문제를 분리해서 봐야 함
- Java 21, Gradle, IntelliJ 실행 대상이 맞아야 Spring Boot가 정상 기동함

**Python 비교**:
```python
# 코드가 맞아도
# python 버전이나 가상환경이 틀리면 실행이 안 될 수 있다.
print("환경 설정 확인")
```

**Java/Spring 연결**:
```java
@SpringBootApplication
public class Day01Application {
    public static void main(String[] args) {
        SpringApplication.run(Day01Application.class, args);
    }
}
```

**흐름 타임라인**:
- 0ms: IntelliJ가 실행 설정을 읽음
- 10ms: JDK 버전 확인
- 30ms: Gradle/Spring Boot 클래스 로드
- 100ms+: 톰캣 서버가 뜨면 실행 성공

**한 줄 메모**:
- 실행이 안 될 때는 "코드가 틀렸나?"보다 먼저 "환경이 맞나?"를 같이 봐야 한다.

---
## [2026-07-02] Q: chatService에 빨간 줄이 계속 떠

**질문**: chatService에 빨간 줄이 계속 떠

**핵심 개념**:
- 생성자 주입: Service를 Controller에 연결하는 가장 기본 방식
- 문법 오류 하나로도 IntelliJ 전체가 빨간 줄처럼 보일 수 있음

**Python 비교**:
```python
class ChatService:
    def chat(self, message):
        return message

class ChatController:
    def __init__(self, service):
        self.service = service
```

**Java/Spring 연결**:
```java
private final ChatService chatService;

public ChatController(ChatService chatService) {
    this.chatService = chatService;
}
```

**흐름 타임라인**:
- 0ms: Spring이 Controller를 만들려고 함
- 5ms: 필요한 Service를 찾음
- 10ms: Service 코드에 문법 오류가 있으면 연결 실패

**한 줄 메모**:
- 빨간 줄은 "연결 문제"일 수도 있지만, 실제로는 중괄호/오타 같은 문법 문제인 경우도 많다.

---
## [2026-07-02] Q: HTML도 만들어서 디자인 적용시킬까

**질문**: 응 우리도 html 만들어서 각각에 디자인 적용시킬까?

**핵심 개념**:
- API만 있는 앱에서 UI를 붙이면 사용 흐름이 눈에 보이기 시작함
- 백엔드와 프론트의 연결을 작은 형태로 경험하는 단계

**Python 비교**:
```python
# Flask에서도 API만 만들 수 있지만
# template을 붙이면 브라우저 화면이 생긴다.
```

**Java/Spring 연결**:
```java
@Controller
public class ChatViewController {
    @GetMapping("/")
    public String home() {
        return "chat";
    }
}
```

**흐름 타임라인**:
- 0ms: 브라우저가 `/` 요청
- 10ms: HTML 템플릿 반환
- 30ms: 사용자가 폼 입력
- 50ms: API 호출
- 100ms+: 응답 표시

**한 줄 메모**:
- UI를 붙이면 API가 "보이지 않는 로직"에서 "직접 써보는 기능"으로 바뀐다.

---
## [2026-07-03] Q: Day2 수업 내용이 이해가 안 가. 처음부터 끝까지 설명해줘

**질문**: 오늘 학습 내용인데 이해가 안 가. 처음부터 끝까지 설명해줘,

**핵심 개념**:
- Prompt 설계: AI에게 그냥 질문하지 말고, 답의 형식과 대상까지 지정하는 것
- Structured Output: 문자열 말고 JSON, 객체, 리스트처럼 구조화된 결과 받기

**Python 비교**:
```python
prompt = f"{audience}가 이해하기 쉽게 요약해줘: {text}"
print(prompt)
```

**Java/Spring 연결**:
```java
return chatClient.prompt()
        .user(u -> u.text("{audience}가 이해하기 쉽게 요약해줘: {text}")
                .param("audience", audience)
                .param("text", text))
        .call()
        .content();
```

**흐름 타임라인**:
- 0ms: 요청이 들어옴
- 10ms: 프롬프트 템플릿 생성
- 20ms: 변수 치환
- 80ms+: AI 응답 수신
- 100ms+: 문자열/객체로 변환

**한 줄 메모**:
- Day2는 "AI에게 어떻게 말해야 원하는 형태로 답을 받는가"를 배우는 날이었다.

---
## [2026-07-03] Q: day02-prompt-output에 Gradle 설정 문제가 존재해. 문제가 뭘까?

**질문**: C:\Users\금정산2-PC02\p2-spring\spring-ai-study에서 day02에 Gradle 설정 문제가 존재해. 문제가 뭘까?

**핵심 개념**:
- 경로 문제: 한글 사용자 경로가 Gradle test executor를 흔들 수 있음
- `org.gradle.java.home`, `GRADLE_USER_HOME`: Java/Gradle 경로를 영어 경로로 고정하는 설정

**Python 비교**:
```python
# 라이브러리 코드는 맞아도
# 경로나 환경변수가 꼬이면 실행이 깨질 수 있다.
import os
print(os.environ.get("PYTHONPATH"))
```

**Java/Spring 연결**:
```properties
org.gradle.java.home=C:/jdk21
```

**흐름 타임라인**:
- 0ms: Gradle이 JDK 경로 확인
- 10ms: 캐시/worker 경로 확인
- 30ms: 테스트 실행기 로드
- 50ms+: 한글 경로에서 classpath 문제가 나면 실패

**한 줄 메모**:
- 코드가 아니라 "경로" 때문에 테스트가 깨질 수도 있다는 걸 확인했다.

---
## [2026-07-03] Q: Bruno에서 request를 만들었는데 에러가 뜨네

**질문**: 그냥 우리가 컨트롤러 만들어서 진행하는데 bruno에 request 만들어서 실행하니까 에러 뜨네.

**핵심 개념**:
- 404는 로직 오류보다 "주소가 없거나 다른 앱이 떠 있음"일 가능성이 큼
- Day1과 Day2가 같은 8080 포트를 쓰면, 어떤 앱이 실제로 떠 있는지 먼저 확인해야 함

**Python 비교**:
```python
# Flask에서도 다른 앱을 켜두고
# 없는 route를 치면 404가 난다.
```

**Java/Spring 연결**:
```java
@GetMapping("/api/summary")
public String summary(@RequestParam String text, @RequestParam String audience) {
    return promptService.summarize(text, audience);
}
```

**흐름 타임라인**:
- 0ms: Bruno가 URL 요청
- 10ms: Spring이 매핑 테이블 확인
- 15ms: 주소가 없으면 404 반환
- 20ms: 정적 리소스까지 찾고 없으면 `NoResourceFoundException`

**한 줄 메모**:
- Bruno 에러는 종종 "요청이 틀린 서버로 갔다"가 원인이다.

---
## [2026-07-03] Q: Controller랑 PromptService.java를 한 줄씩 뜯어서 해석하자

**질문**: 우리 한 줄씩 뜯어서 Controller랑 PromptService.java 해석하자.

**핵심 개념**:
- Controller: 요청을 받는 창구
- Service: 실제 AI 호출과 프롬프트 구성 담당

**Python 비교**:
```python
def controller(text, audience):
    return service(text, audience)

def service(text, audience):
    return f"{audience}용 요약: {text}"
```

**Java/Spring 연결**:
```java
@GetMapping("/api/summary")
public String summary(@RequestParam String text, @RequestParam String audience) {
    return promptService.summarize(text, audience);
}
```

**흐름 타임라인**:
- 0ms: 사용자가 요청
- 10ms: Controller가 파라미터 추출
- 20ms: Service 메서드 호출
- 40ms: ChatClient 프롬프트 생성
- 100ms+: 응답 반환

**한 줄 메모**:
- 코드는 줄마다 따로 있지만, 실행은 하나의 물 흐르듯 연결된다.

---
## [2026-07-03] Q: day01만 Git에 추적되는 것 같아

**질문**: 지금 day01만 깃에 추적되는 거 같아.

**핵심 개념**:
- 중첩 저장소: `day01` 안에만 `.git`이 있으면 `day02`는 Git 추적 대상이 아님
- 루트 저장소 통합: `spring-ai-study`를 하나의 Git 저장소로 만들면 day01/day02를 함께 관리 가능

**Python 비교**:
```python
projects = {
    "day01": "git tracked",
    "day02": "not tracked"
}
```

**Java/Spring 연결**:
```java
// 코드 문제가 아니라 프로젝트 구조 문제였다.
```

**흐름 타임라인**:
- 0ms: IntelliJ가 Git 루트를 봄
- 10ms: `day01/.git`만 인식
- 20ms: `day02` 변경은 일반 폴더처럼 보임
- 30ms: 루트 저장소 생성 후 둘 다 추적 가능

**한 줄 메모**:
- 코드 변경이 안 보이는 건 Git 구조 문제일 수도 있다.

---
## [2026-07-03] Q: movie를 추가했는데 MovieResponse는 안 됐어

**질문**: movie를 새로 추가했는데 service와 controller는 구현 되었지만 MovieResponse는 안 됐어. 확인해바

**핵심 개념**:
- DTO/record: AI 응답을 객체 구조로 받기 위한 데이터 상자
- `ParameterizedTypeReference<List<MovieResponse>>() {}`: 리스트 안 타입까지 알려주는 문법

**Python 비교**:
```python
movies = [
    {"title": "영화1", "genre": "드라마", "reason": "잔잔해서"}
]
```

**Java/Spring 연결**:
```java
public record MovieResponse(
        String title,
        String genre,
        String reason
) {
}
```

**흐름 타임라인**:
- 0ms: `/api/movie?mood=...` 요청
- 20ms: Service가 프롬프트 작성
- 80ms+: AI가 영화 목록 반환
- 100ms+: `List<MovieResponse>`로 변환

**한 줄 메모**:
- 객체 응답을 받으려면 "응답 모양"을 담을 DTO가 꼭 필요하다.

---
## [2026-07-03] Q: /packing 호출이 안 돼

**질문**: http://localhost:8080/packing?destination=하와이&days=8 문제가 뭘까 안돼

**핵심 개념**:
- URL 매핑은 문자 하나까지 정확히 맞아야 함
- 실제 등록 주소가 `/api/packing`이면 `/packing`은 다른 주소다

**Python 비교**:
```python
# Flask에서도 /packing 과 /api/packing 은 다른 route다.
```

**Java/Spring 연결**:
```java
@GetMapping("/api/packing")
public List<String> packing(@RequestParam String destination,
                            @RequestParam(defaultValue = "3") int days) {
    return classifyService.packingList(destination, days);
}
```

**흐름 타임라인**:
- 0ms: 브라우저가 `/packing` 요청
- 10ms: Spring이 매핑 검사
- 15ms: 등록된 주소가 없어서 404
- 20ms: `/api/packing`으로 고치면 정상 연결

**한 줄 메모**:
- "안 된다"의 원인이 코드보다 URL 오타인 경우가 생각보다 많다.

---
## [2026-07-03] Q: Spring AI day1 개념부터 시작하자

**질문**: 자 Spring AI day 1 개념부터 시작하자.

**핵심 개념**:
- Spring AI Day1: AI를 웹 애플리케이션 안에서 처음 호출해보는 가장 기초 단계
- Controller → Service → ChatClient: 요청을 받고, AI를 부르고, 응답을 돌려주는 기본 구조

**Python 비교**:
```python
def chat(message):
    prompt = f"질문: {message}"
    return f"AI 응답: {prompt}"
```

**Java/Spring 연결**:
```java
@GetMapping("/api/chat")
public String chat(@RequestParam String message) {
    return chatService.chat(message);
}
```

**흐름 타임라인**:
- 0ms: 사용자가 브라우저나 Bruno에서 질문 전송
- 10ms: Controller가 요청을 받음
- 20ms: Service가 AI에게 보낼 프롬프트를 만듦
- 80ms+: ChatClient가 모델을 호출함
- 120ms+: 응답 문자열을 사용자에게 반환함

**한 줄 메모**:
- Day1의 핵심은 "AI를 똑똑하게 쓰는 것"보다 먼저 "AI를 내 앱에서 한번 연결해보는 것"이다.

---
## [2026-07-03] Q: `public String chat(@RequestParam String message)` 이게 Python으로는 뭘까

**질문**: 이게 python으로는 뭘까

**핵심 개념**:
- 메서드 선언: "이름이 chat인 함수고, 문자열 하나를 받아서 문자열 하나를 돌려준다"는 뜻
- `@RequestParam`: URL이나 요청값에서 `message`를 꺼내 함수 인자로 넣는 장치

**Python 비교**:
```python
def chat(message):
    return f"받은 값: {message}"
```

**Java/Spring 연결**:
```java
public String chat(@RequestParam String message) {
    return "받은 값: " + message;
}
```

**흐름 타임라인**:
- 0ms: 사용자가 `?message=안녕` 값을 보냄
- 10ms: Spring이 `message` 값을 꺼냄
- 20ms: `chat(message)` 메서드에 넣어줌
- 30ms: 메서드가 문자열을 반환함

**한 줄 메모**:
- Python 함수와 거의 같고, 차이는 Spring이 요청값을 자동으로 함수 인자에 꽂아준다는 점이다.

---
## [2026-07-03] Q: `@RequestParam` 설명

**질문**: @RequestParam 설명

**핵심 개념**:
- `@RequestParam`: URL의 쿼리스트링 값이나 요청 파라미터를 메서드 인자로 꺼내오는 장치
- 기본값: 값이 없을 때 `defaultValue`로 대신 넣을 수 있음

**Python 비교**:
```python
from flask import request

def chat():
    message = request.args.get("message")
    return message
```

**Java/Spring 연결**:
```java
@GetMapping("/api/chat")
public String chat(@RequestParam String message) {
    return message;
}
```

**흐름 타임라인**:
- 0ms: 사용자가 `?message=안녕` 요청
- 10ms: Spring이 `message` 값을 찾음
- 20ms: 메서드 파라미터에 자동으로 넣음
- 30ms: 메서드 실행

**한 줄 메모**:
- `@RequestParam`은 "URL에서 이 값 꺼내서 여기 넣어줘"라고 Spring에게 부탁하는 표식이다.

---
## [2026-07-03] Q: `request.args`가 이해 안 가

**질문**: request.args 이거 이해 안가

**핵심 개념**:
- `request.args`: URL 뒤 `?` 뒤에 붙은 값들을 담고 있는 상자
- key-value 구조: `message=안녕`, `days=3`처럼 이름과 값으로 들어감

**Python 비교**:
```python
# /chat?message=안녕&days=3
message = request.args.get("message")
days = request.args.get("days")
```

**Java/Spring 연결**:
```java
public String chat(@RequestParam String message) {
    return message;
}
```

**흐름 타임라인**:
- 0ms: 사용자가 `/chat?message=안녕&days=3` 요청
- 10ms: Flask/Spring이 `?` 뒤를 읽음
- 20ms: `message`, `days`를 이름표처럼 구분함
- 30ms: 필요한 값을 꺼내서 함수에 넣음

**한 줄 메모**:
- `request.args`는 URL 쿼리스트링 보관함이고, Spring의 `@RequestParam`은 그 보관함에서 값을 자동으로 꺼내주는 역할이다.

---
## [2026-07-03] Q: `private final`과 `public class`의 차이가 뭐야

**질문**: 애하고 public class의 차이가 뭐야? class는 파이썬처럼 class인 건 알아

**핵심 개념**:
- `public class`: 클래스 자체를 밖에서 볼 수 있게 여는 선언
- `private final`: 클래스 안에 있는 변수 하나에 대한 규칙 선언

**Python 비교**:
```python
class ChatService:
    def __init__(self, client):
        self._client = client  # private 비슷한 관례
```

**Java/Spring 연결**:
```java
public class ChatService {
    private final ChatClient chatClient;
}
```

**흐름 타임라인**:
- 0ms: 자바가 클래스를 읽음
- 10ms: `public class`로 이 클래스를 밖에서 쓸 수 있는지 판단
- 20ms: 클래스 안 필드를 읽음
- 30ms: `private final`로 이 변수를 어디서 접근/변경 가능한지 판단

**한 줄 메모**:
- `public class`는 "이 상자 자체를 공개할까?"이고, `private final`은 "상자 안 물건을 누가 만지고 바꿀 수 있나?"이다.

---
## [2026-07-03] Q: `chatService.chat(message)` 이게 어떻게 넘긴다는 거임?

**질문**: 이게 어떻게 넘긴다는 거임?

**핵심 개념**:
- 메서드 호출: `객체.메서드(값)` 형태로 다른 메서드에 값을 전달하는 문법
- 인자 전달: 괄호 안 `message`가 `chat(String message)`의 파라미터로 들어감

**Python 비교**:
```python
service.chat(message)
```

**Java/Spring 연결**:
```java
return chatService.chat(message);
```

**흐름 타임라인**:
- 0ms: Controller 안에 `message` 값이 들어있음
- 10ms: `chatService.chat(message)` 호출
- 20ms: Service의 `chat(String message)` 파라미터에 값이 들어감
- 30ms: Service 로직 실행

**한 줄 메모**:
- `chatService.chat(message)`는 "chatService야, 여기 message 값 줄 테니까 chat 함수 실행해줘"라는 뜻이다.

---
## [2026-07-03] Q: `.Builder builder` 이건 왜 이렇게 해?

**질문**: 이건 왜 이렇게 해?

**핵심 개념**:
- `ChatClient.Builder`: 완성품 `ChatClient`를 만들기 전에 설정을 담는 조립 도구
- 생성자 주입: Spring이 필요한 도구를 생성자에 넣어주고, 클래스는 그걸 받아 사용함

**Python 비교**:
```python
class ClientBuilder:
    def build(self):
        return "client"

builder = ClientBuilder()
client = builder.build()
```

**Java/Spring 연결**:
```java
public ChatService(ChatClient.Builder builder) {
    this.chatClient = builder.build();
}
```

**흐름 타임라인**:
- 0ms: Spring이 `ChatService`를 만들려고 함
- 10ms: 먼저 `ChatClient.Builder`를 준비함
- 20ms: 생성자에 `builder`를 넣어줌
- 30ms: `builder.build()`로 실제 `ChatClient` 생성
- 40ms: 이후부터 `chatClient`로 AI 호출

**한 줄 메모**:
- `Builder`는 완성품이 아니라 "조립 도구"이고, `build()`를 호출해야 실제 객체가 나온다.

---
## [2026-07-03] Q: `prompt()`는 프롬프트가 시작한다는 의미야? 전체적으로 이 코드들이 어떻게 돌아가?

**질문**: 프롬프트가 시작한다는 의미야?
전체적으로 이 코드들이 어떻게 돌아가?

**핵심 개념**:
- `prompt()`: AI에게 보낼 대화 한 묶음을 만들기 시작하는 출발점
- `user() -> call() -> content()`: 사용자 메시지 넣기 → 실제 호출하기 → 응답 본문 꺼내기 순서

**Python 비교**:
```python
message = "스프링 AI가 뭐야?"
payload = {"user": message}
response = call_ai(payload)
content = response["content"]
```

**Java/Spring 연결**:
```java
return chatClient.prompt()
        .user(message)
        .call()
        .content();
```

**흐름 타임라인**:
- 0ms: `prompt()`로 대화 한 세트를 만들 준비
- 10ms: `user()`로 사용자 질문 넣기
- 20ms: `call()`로 실제 AI 서버 호출
- 200ms+: AI가 응답 반환
- 210ms: `content()`로 답변 본문만 꺼냄

**한 줄 메모**:
- `prompt()`는 "대화 시작 버튼"이라기보다, "이번에 보낼 질문 꾸러미를 만들기 시작하는 첫 줄"에 가깝다.

---
## [2026-07-03] Q: 왜 최종적으로 우리가 실제로 쓸 건 `ChatClient`라고 해?

**질문**: 왜 이렇게 써?

**핵심 개념**:
- `Builder`는 만드는 도구이고, `ChatClient`는 실제로 일하는 완성품
- 필드에는 보통 "계속 사용할 완성 객체"를 저장하고, Builder는 생성 시점에만 잠깐 씀

**Python 비교**:
```python
builder = ClientBuilder()
client = builder.build()

# 이후 계속 쓰는 건 client
result = client.ask("안녕")
```

**Java/Spring 연결**:
```java
private final ChatClient chatClient;

public ChatService(ChatClient.Builder builder) {
    this.chatClient = builder.build();
}
```

**흐름 타임라인**:
- 0ms: Spring이 Builder 준비
- 10ms: 생성자에서 `build()` 호출
- 20ms: `ChatClient` 완성
- 30ms 이후: 실제 AI 호출은 계속 `chatClient`로 수행

**한 줄 메모**:
- Builder는 만드는 순간에만 필요하고, 진짜 질문을 보내는 건 완성된 `ChatClient`다.

---
## [2026-07-03] Q: `builder`는 어디에 만들어져 있어?

**질문**: 애는 어디에 만들어져있어?

**핵심 개념**:
- Spring이 자동 생성: `ChatClient.Builder`는 내가 직접 `new` 하지 않아도 Spring이 준비해줌
- 빈(Bean): Spring 컨테이너 안에 미리 만들어두고 필요할 때 주입하는 객체

**Python 비교**:
```python
# 내가 직접 만든 경우
builder = ClientBuilder()
service = ChatService(builder)
```

**Java/Spring 연결**:
```java
public ChatService(ChatClient.Builder builder) {
    this.chatClient = builder.build();
}
```

**흐름 타임라인**:
- 0ms: Spring Boot가 시작됨
- 10ms: Spring AI 자동설정이 실행됨
- 20ms: `ChatClient.Builder` 빈이 준비됨
- 30ms: `ChatService` 생성자 호출 시 builder를 넣어줌

**한 줄 메모**:
- `builder`는 내가 손으로 만든 게 아니라, Spring이 뒤에서 만들어놓고 생성자에 꽂아주는 객체다.

---
## [2026-07-03] Q: 2일차 정리해봐

**질문**: 2일차 정리해봐

**핵심 개념**:
- Prompt 설계: AI에게 질문만 던지는 게 아니라, 답의 대상과 형식을 같이 지시하는 것
- Structured Output: 문자열뿐 아니라 JSON, 객체, 리스트 형태로 응답을 받는 것

**Python 비교**:
```python
prompt = f"{audience}가 이해하기 쉽게 요약해줘: {text}"

result = {
    "category": "배송",
    "priority": "HIGH",
    "reason": "배송 지연 문의"
}
```

**Java/Spring 연결**:
```java
return chatClient.prompt()
        .user(u -> u.text("{audience}가 이해하기 쉽게 요약해줘: {text}")
                .param("audience", audience)
                .param("text", text))
        .call()
        .content();
```

**흐름 타임라인**:
- 0ms: 사용자가 요청과 파라미터 전송
- 10ms: Controller가 값을 받음
- 20ms: Service가 프롬프트 템플릿 작성
- 30ms: `param()`으로 실제 값 치환
- 80ms+: AI 호출
- 120ms+: 문자열/객체/리스트 형태로 응답 변환

**한 줄 메모**:
- Day2의 핵심은 "AI를 부른다"에서 끝나지 않고, "원하는 모양으로 답하게 만든다"까지 간 것이다.

---
## [2026-07-03] Q: `ch03-prompt` 프로젝트를 분석해서 교육자료로 쓰자

**질문**: C:\Users\금정산2-PC02\p2-spring\spring-ai-study\ch03-prompt 여기 들어가서 분석해서 교육자료로 쓰자

**핵심 개념**:
- 프롬프트 기법 모음: prompt-template, multi-messages, zero-shot, few-shot, role-assignment, step-back, chain-of-thought, self-consistency를 각각 예제로 분리해둔 학습용 프로젝트
- Controller + Service + HTML 데모 구조: 버튼/폼으로 질문을 보내고, 각 Service가 다른 프롬프트 전략으로 AI를 호출함

**Python 비교**:
```python
def zero_shot(review):
    prompt = f"리뷰를 긍정/중립/부정으로 분류해줘: {review}"
    return call_ai(prompt)

def few_shot(order):
    prompt = f"예시를 보고 JSON으로 바꿔줘: {order}"
    return call_ai(prompt)
```

**Java/Spring 연결**:
```java
@PostMapping("/zero-shot-prompt")
public String zeroShotPrompt(@RequestParam("review") String review) {
    return aiService.zeroShotPrompt(review);
}
```

**흐름 타임라인**:
- 0ms: 사용자가 홈 화면에서 기법 선택
- 10ms: 해당 HTML 페이지가 열림
- 20ms: 폼 입력 후 `/ai/...` 엔드포인트 호출
- 30ms: Controller가 Service 메서드 실행
- 50ms: Service가 각 프롬프트 기법으로 AI 호출
- 200ms+: 응답을 문자열/스트림으로 반환

**한 줄 메모**:
- 이 프로젝트는 "Spring AI로 어떤 프롬프트 기법을 어떻게 코드에 옮기는지"를 데모별로 보여주는 교재형 프로젝트다.

---
## [2026-07-03] Q: 어제부터 오늘까지 한 내용을 한 눈에 시각화하고 싶은데 어떻게 하면 좋을까?

**질문**: 자 이제 어제부터 오늘까지 한 내용을 한 눈에 시각화하고 싶은데 어떻게 하면 좋을까?

**핵심 개념**:
- 학습 시각화: 시간순 흐름, 개념 지도, 프로젝트 구조를 한 장에 나누어 보여주면 복습이 쉬움
- 한 장 요약: "무엇을 배웠는지"보다 "어떻게 연결되는지"를 보이게 만드는 것이 중요함

**Python 비교**:
```python
study_map = {
    "Day1": ["Controller", "Service", "ChatClient"],
    "Day2": ["Prompt", "JSON", "record", "List"]
}
```

**Java/Spring 연결**:
```java
Controller -> Service -> ChatClient -> AI Response
```

**흐름 타임라인**:
- 0ms: 학습 내용을 날짜별로 나눔
- 10ms: 공통 흐름과 기능별 예제를 분리함
- 20ms: 다이어그램/표/로드맵 중 한 형식 선택
- 30ms: 핵심만 남겨 한 장 요약 제작

**한 줄 메모**:
- 지금 단계에서는 "전체 로드맵 1장 + 코드 흐름 1장" 조합이 가장 복습 효율이 좋다.

---
## [2026-07-03] Q: 어제부터 오늘까지 내용을 시각화하고 PPT도 만들고 싶어

**질문**: 응 그리고 PPT도 만들어.

**핵심 개념**:
- Markdown 시각화: Mermaid와 표로 흐름을 한눈에 정리
- 발표 자료화: 같은 구조를 PPT 슬라이드로 옮겨서 복습과 발표에 모두 쓰기

**Python 비교**:
```python
summary = {
    "roadmap": ["Day1", "Day2"],
    "slides": ["전체 흐름", "기능 비교", "문제 정리"]
}
```

**Java/Spring 연결**:
```java
Controller -> Service -> ChatClient -> Response
```

**흐름 타임라인**:
- 0ms: 어제/오늘 학습 내용을 범주별로 묶음
- 10ms: Markdown 시각화 초안 작성
- 20ms: 발표용 슬라이드 목차 구성
- 30ms: PPT 파일 생성

**한 줄 메모**:
- 복습용은 Markdown, 발표용은 PPT로 같은 구조를 두 번 재사용하는 게 효율적이다.

---
## [2026-07-03] Q: PPT를 팔란티어 스타일로 만들어줘

**질문**: 잠시만 PPT 팔란티어 스타일로 만들어줘.

**핵심 개념**:
- 슬라이드 톤앤매너: 같은 내용도 색, 여백, 카드 구조를 바꾸면 발표 인상이 달라짐
- 정보형 디자인: 짙은 배경 + 청록 포인트 + 짧은 문장 중심 구성이 학습 발표에 잘 맞음

**Python 비교**:
```python
theme = {
    "background": "dark",
    "accent": "teal",
    "layout": "clean cards"
}
```

**Java/Spring 연결**:
```java
Controller -> Service -> ChatClient
```

**흐름 타임라인**:
- 0ms: 기존 PPT 구조 확인
- 10ms: 디자인 톤을 다크/정보형으로 재설계
- 20ms: 생성 스크립트 수정
- 30ms: 새 스타일로 PPT 재생성

**한 줄 메모**:
- 발표 자료는 내용 정리와 별개로, 톤을 통일해주면 훨씬 더 “정리된 프로젝트”처럼 보인다.

---
## [2026-07-03] Q: PPT가 안 바뀐 것 같으니 팔란티어 스타일로 확실히 다시 만들어줘

**질문**: PPT 안 바꼈는데? 뭐가 바꼈다는 거야. 팔란티어 스타일로 만들어.

**핵심 개념**:
- 결과 확인: 파일 생성 후 크기와 수정 시각을 확인해야 실제 반영 여부를 알 수 있음
- 스타일 재설계: 기본 템플릿 느낌을 없애고, 정보 상황판형 레이아웃으로 다시 구성

**Python 비교**:
```python
deck_style = {
    "background": "near-black",
    "visuals": ["grid", "nodes", "metrics", "matrix"],
    "accent": "cyan"
}
```

**Java/Spring 연결**:
```java
Controller -> Service -> ChatClient -> AI
```

**흐름 타임라인**:
- 0ms: 기존 PPT가 눈에 띄게 바뀌지 않은 문제 확인
- 10ms: PPT 생성 스크립트 전면 재작성
- 20ms: 어두운 배경, 격자, 노드 맵, 기능 매트릭스 구성
- 30ms: 기존 파일과 새 스타일 파일을 모두 재생성
- 40ms: 파일 크기와 수정 시각으로 변경 확인

**한 줄 메모**:
- 시각 자료는 “생성했다”보다 “실제로 열었을 때 달라 보이는가”가 더 중요하다.

---
## [2026-07-03] Q: 오늘 자습 내용으로 `ch03-prompt` 프로젝트 분석을 누적 기록해줘

**질문**: 아니 오늘 자습내용이야. 누적기록해.

**핵심 개념**:
- `ch03-prompt`: Spring AI 프롬프트 기법을 기능별 데모로 모아둔 자습용 프로젝트
- 오늘 자습 포인트: Day1/Day2에서 배운 `Controller -> Service -> ChatClient` 흐름을 여러 프롬프트 전략에 적용해보기

**Python 비교**:
```python
def prompt_template(text, language):
    prompt = f"{text}를 {language}로 번역해줘"
    return call_ai(prompt)

def few_shot(order):
    examples = ["예시1", "예시2"]
    return call_ai(examples + [order])
```

**Java/Spring 연결**:
```java
@PostMapping("/ai/prompt-template")
public Flux<String> promptTemplate(@RequestParam String statement,
                                   @RequestParam String language) {
    return aiService.promptTemplate1(statement, language);
}
```

**흐름 타임라인**:
- 0ms: `ch03-prompt` 프로젝트 구조 확인
- 10ms: `HomeController`가 각 HTML 화면을 연결하는 구조 파악
- 20ms: 각 `AiController...`가 `/ai/...` 요청을 받는 구조 확인
- 30ms: 각 `AiService...`가 서로 다른 프롬프트 기법으로 `ChatClient`를 호출하는 구조 분석
- 40ms: `prompt-template`, `multi-messages`, `zero-shot`, `few-shot`, `role-assignment`, `step-back`, `chain-of-thought`, `self-consistency`를 자습 항목으로 정리

**오늘 자습 정리**:
- `prompt-template`: 빈칸이 있는 프롬프트 틀을 만들고 값만 바꿔 끼우는 방식
- `multi-messages`: 이전 대화 내용을 `chatMemory`로 보관해서 이어서 대화하는 방식
- `default-method`: `ChatClient.Builder`에 기본 시스템 메시지와 옵션을 미리 설정하는 방식
- `zero-shot-prompt`: 예시 없이 바로 분류나 작업을 시키는 방식
- `few-shot-prompt`: 예시를 몇 개 보여준 뒤 같은 형식으로 답하게 하는 방식
- `role-assignment`: AI에게 여행 가이드 같은 역할을 부여하고 답하게 하는 방식
- `step-back-prompt`: 큰 질문을 작은 질문들로 쪼갠 뒤 단계적으로 답을 만드는 방식
- `chain-of-thought`: 생각 과정을 단계적으로 풀어 쓰게 하는 방식
- `self-consistency`: 같은 질문을 여러 번 던져 다수결로 더 안정적인 답을 고르는 방식

**한 줄 메모**:
- 오늘 자습은 `ch03-prompt`를 통해 “같은 ChatClient라도 프롬프트 전략이 달라지면 AI의 답변 방식이 달라진다”는 점을 확인한 시간이다.

---
## [2026-07-03] Q: `prompt()` 같은 건 Spring이 제공하는 거야? 우리가 정의한 거야?

**질문**: 이런 함수가 Spring에서 제공하는 거야? 아니면 우리가 정의한 거야?

**핵심 개념**:
- 라이브러리 메서드: `prompt()`, `user()`, `call()`, `content()`는 Spring AI가 제공하는 기능
- 사용자 정의 메서드: `chat()`, `classify()`처럼 우리가 직접 클래스 안에 선언한 함수

**Python 비교**:
```python
def chat(message):
    return message.upper()

text = "hello"
result = text.upper()
```

**Java/Spring 연결**:
```java
public String chat(String message) {
    return chatClient.prompt()
            .user(message)
            .call()
            .content();
}
```

**흐름 타임라인**:
- 0ms: 우리가 만든 `chat()` 메서드 시작
- 10ms: Spring AI의 `prompt()` 호출
- 20ms: Spring AI의 `user()` 호출
- 30ms: Spring AI의 `call()` 호출
- 40ms: Spring AI의 `content()` 호출

**한 줄 메모**:
- 내가 직접 선언한 건 내 함수고, `import`해온 객체가 제공하는 메서드는 보통 라이브러리 함수다.

---
## [2026-07-03] Q: 라이브러리 함수와 내가 만든 함수를 코드에서 어떻게 구분해?

**질문**: 응

**핵심 개념**:
- 함수 선언 위치: 내 파일 안에 `public String ...`처럼 선언돼 있으면 내가 만든 함수
- 객체의 메서드: `chatClient.prompt()`처럼 점 앞 객체가 제공하면 라이브러리 메서드일 가능성이 큼

**Python 비교**:
```python
def chat(message):
    return message.upper().strip()
```

**Java/Spring 연결**:
```java
public String chat(String message) {
    return chatClient.prompt()
            .user(message)
            .call()
            .content();
}
```

**흐름 타임라인**:
- 0ms: 내가 만든 `chat()` 실행
- 10ms: 내부에서 라이브러리 메서드 `prompt()` 사용
- 20ms: `user()`, `call()`, `content()` 순서로 실행
- 40ms: 최종 결과를 내 메서드가 반환

**한 줄 메모**:
- 코드에서 "누가 선언했는가"를 보면 내 함수인지 라이브러리 함수인지 구분할 수 있다.

---
## [2026-07-03] Q: `chatClient` 자체는 어디서 왔는지 알려줘

**질문**: 응 알려줘. 그리고 지금 너가 준 답변이랑 내가 한 질문 STUDY_LOG.md에 넣을 수 있어?

**핵심 개념**:
- `ChatClient`: Spring이 준비해서 넣어주는 AI 호출 객체
- `ChatClient.Builder`: 그 `ChatClient`를 만들기 위한 조립 도구

**Python 비교**:
```python
builder = ClientBuilder()
client = builder.build()
service = ChatService(client)
```

**Java/Spring 연결**:
```java
private final ChatClient chatClient;

public ChatService(ChatClient.Builder builder) {
    this.chatClient = builder.build();
}
```

**흐름 타임라인**:
- 0ms: Spring Boot 시작
- 10ms: Spring AI 자동설정 실행
- 20ms: `ChatClient.Builder` 준비
- 30ms: `ChatService` 생성자 호출
- 40ms: `builder.build()`로 `chatClient` 생성
- 50ms 이후: `chatClient`로 실제 AI 호출

**한 줄 메모**:
- `chatClient`는 내가 직접 만든 게 아니라, Spring이 준비해 준 AI 호출 도구다.

---
## [2026-07-06] Q: Spring AI Day3 `Advisor / Chat Memory` 수업 시작

**질문**: 오늘은 이 수업을 할 거야.

**핵심 개념**:
- `Advisor`: AI 호출 전후에 공통 작업을 붙이는 인터셉터
- `Chat Memory`: 이전 대화를 다음 호출에 다시 넣어 맥락을 유지하는 장치
- `conversationId`: 어떤 대화방의 기억인지 구분하는 필수 키

**Python 비교**:
```python
# Advisor 느낌
def middleware(question):
    print("요청 로그")
    answer = llm_call(question)
    print("응답 로그")
    return answer

# Chat Memory 느낌
memory = {
    "room1": [
        "내 이름은 민준이야",
        "알겠습니다"
    ]
}
```

**Java/Spring 연결**:
```java
chatClient.prompt()
        .user(question)
        .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, conversationId))
        .call()
        .content();
```

**흐름 타임라인**:
- 0ms: 사용자가 질문과 `conversationId`를 보냅니다.
- 10ms: Controller가 요청을 받습니다.
- 20ms: Service가 `chatClient.prompt()`를 시작합니다.
- 30ms: Advisor 전처리가 순서대로 실행됩니다.
- 40ms: Memory Advisor가 이전 대화 목록을 꺼내 프롬프트에 붙입니다.
- 50ms: LLM이 최종 프롬프트를 받고 답변합니다.
- 60ms: Advisor 후처리가 역순으로 실행됩니다.
- 70ms: 이번 질문/응답이 다시 memory에 저장됩니다.

**한 줄 메모**:
- Day3의 핵심은 "프롬프트 내용을 더 잘 쓰는 것"이 아니라 "AI 호출 과정을 관리하는 것"입니다.

---
## [2026-07-06] Q: IntelliJ에서 `day03-advisor-memory` 클래스 생성이 안 되는 이유 확인

**질문**: IntelliJ에서 지금 클래스가 생성 안디는데 day-03-advisor-memory에 문제가 있는 지 학인해줘

**핵심 개념**:
- 독립 Gradle 프로젝트: `spring-ai-study` 루트는 멀티모듈 Gradle 프로젝트가 아니라, day01/day02/day03가 각각 따로 존재하는 구조
- Source Root: IntelliJ가 `src/main/java`를 Java 소스 폴더로 인식해야 클래스 생성 메뉴가 정상 동작함
- Gradle import: 빌드는 성공해도 IntelliJ import가 덜 되면 에디터 기능이 비정상일 수 있음

**Python 비교**:
```python
# 파이썬으로 비유하면
# 폴더는 맞는데 IDE가 "여기가 패키지 루트"라고 아직 모르는 상태
project/
    app/
        main.py
```

**Java/Spring 연결**:
```java
src/main/java/com/study/day03advisormemory
```

**흐름 타임라인**:
- 0ms: IntelliJ가 상위 폴더 `spring-ai-study`를 엽니다.
- 10ms: day01, day02, day03를 각각 독립 프로젝트로 읽으려 합니다.
- 20ms: day03는 Gradle 링크는 되었지만 소스 루트 import가 완전히 생성되지 않습니다.
- 30ms: 사용자가 패키지에서 새 클래스 생성을 시도합니다.
- 40ms: IntelliJ가 해당 폴더를 Java source root로 못 봐서 클래스 생성이 막히거나 이상 동작합니다.

**한 줄 메모**:
- `day03-advisor-memory` 코드는 빌드가 성공했으므로, 문제의 핵심은 코드보다 IntelliJ 프로젝트 인식입니다.

---
## [2026-07-06] Q: `day03-advisor-memory`를 IntelliJ가 제대로 인식하도록 직접 수정

**질문**: 너가 만들어줘

**핵심 개념**:
- `.iml` 파일: IntelliJ가 모듈과 소스 루트를 기억하는 설정 파일
- Source Root: `src/main/java`를 Java 코드 위치로 표시해야 새 클래스 생성이 자연스럽게 동작
- Spring 구조 분리: `Application`, `Service`, `Controller`는 파일을 나눠야 IDE와 컴파일러가 안정적으로 인식

**Python 비교**:
```python
# 파이썬으로 치면
# main.py 안에 Flask app, service 함수, route 클래스를
# 뒤섞어 넣지 말고 파일을 나누는 것과 비슷하다.
```

**Java/Spring 연결**:
```java
@RestController
@RequestMapping("/api")
public class AiController {
    private final AiService aiService;
}
```

**흐름 타임라인**:
- 0ms: IntelliJ가 `day03` 모듈 설정 파일을 읽습니다.
- 10ms: `src/main/java`가 source root로 잡힙니다.
- 20ms: `AiController.java`, `AiService.java`, `Day03AdvisorMemoryApplication.java`를 각각 독립 Java 파일로 인식합니다.
- 30ms: 새 클래스 생성 메뉴가 정상 동작할 가능성이 높아집니다.
- 40ms: Gradle `classes` 빌드가 성공해 코드 구조도 정상임이 확인됩니다.

**한 줄 메모**:
- 이번 문제는 "코드 문법 오류"와 "IntelliJ 모듈 인식 오류"가 동시에 섞여 있었고, 둘 다 분리해서 해결했습니다.

---
## [2026-07-06] Q: `day03`이 실행조차 안 되는 문제의 정체

**질문**: day03은 실행조차 할 수 없는 문제가 뭐야

**핵심 개념**:
- 포트 충돌: Spring Boot 기본 포트 `8080`을 이미 다른 프로세스가 쓰고 있으면 새 실행이 실패함
- 컴파일 오류와 실행 오류는 다름: 컴파일이 깨져도 실행 안 되고, 이미 떠 있는 서버가 있어도 새 실행이 안 됨
- 현재 상태 검증: `bootRun`과 HTTP 응답까지 확인해야 진짜 실행 여부를 알 수 있음

**Python 비교**:
```python
# Flask도 비슷하다
app.run(port=8080)

# 이미 8080을 다른 서버가 쓰고 있으면
# "Address already in use" 같은 에러가 난다.
```

**Java/Spring 연결**:
```yaml
spring:
  ai:
    google:
      genai:
        api-key: ${GOOGLE_API_KEY}
```

**흐름 타임라인**:
- 0ms: IntelliJ가 `day03` 실행을 시도합니다.
- 10ms: Java 클래스와 Spring Bean을 읽습니다.
- 20ms: 이전에는 코드 구조 오류 때문에 컴파일 단계에서 막혔습니다.
- 30ms: 그 오류를 고친 뒤에는 서버가 실제로 8080 포트에 올라갑니다.
- 40ms: 이미 실행 중인 상태에서 다시 실행하면 포트 충돌로 또 실패할 수 있습니다.
- 50ms: `http://localhost:8080/api/ask?question=test` 호출 시 200 응답이 오면 서버는 이미 살아 있는 상태입니다.

**한 줄 메모**:
- 지금 `day03`의 핵심 문제는 "영원히 실행 불가"가 아니라, 이전 코드 오류를 고친 뒤에는 이미 서버가 떠 있어서 다시 실행할 때 포트 충돌처럼 보일 수 있다는 점입니다.

---
## [2026-07-06] Q: `day03` 실행 불가의 진짜 원인 재파악

**질문**: 아니야 문제 파악해봐

**핵심 개념**:
- IntelliJ 모듈 인식 문제: 앱은 실행 가능한데 IDE가 `day03`을 완전한 Spring Boot 실행 모듈로 못 보고 있음
- Run Configuration: 실행 버튼은 설정된 모듈과 메인 클래스를 기준으로 동작함
- 독립 Gradle 프로젝트 묶음: `spring-ai-study`는 하나의 멀티모듈 Gradle이 아니라, day01/day02/day03가 따로 존재하는 구조

**Python 비교**:
```python
# 비유하면 Flask 앱은 정상인데
# VS Code 실행 설정이 다른 폴더를 바라보는 상태와 비슷하다.
```

**Java/Spring 연결**:
```java
public class Day03AdvisorMemoryApplication {
    public static void main(String[] args) {
        SpringApplication.run(Day03AdvisorMemoryApplication.class, args);
    }
}
```

**흐름 타임라인**:
- 0ms: IntelliJ가 `Run` 버튼을 누를 때 Run Configuration을 찾습니다.
- 10ms: day01/day02는 Spring Boot 설정이 보이지만 day03는 같은 수준으로 등록되어 있지 않습니다.
- 20ms: `workspace.xml`에는 day03 실행 흔적은 있지만 실제 `RunManager` 설정 본문은 없습니다.
- 30ms: `.idea/modules` 폴더에는 day02용 `.main/.test` 모듈만 있고 day03용은 없습니다.
- 40ms: 그래서 앱 자체는 돌아갈 수 있어도, IntelliJ에서는 실행 대상을 정확히 못 잡아 "실행이 안 된다"처럼 느껴집니다.

**한 줄 메모**:
- 이번 핵심 원인은 Spring 코드보다 `IntelliJ가 day03를 완전한 실행 모듈로 가져오지 못한 것`입니다.

---
## [2026-07-06] Q: `day03` 실행 문제 해결과 기록

**질문**: 문제 해결하고 기록해줘

**핵심 개념**:
- Run Configuration: IntelliJ에서 실행 버튼이 무엇을 띄울지 정하는 설정
- 컴파일 차단점: 실행 전 단계에서 Java 컴파일 오류가 있으면 IntelliJ 실행도 실패함
- 로거 방식: Lombok을 쓰려면 의존성이 필요하고, 없으면 일반 `Logger`로 직접 선언해야 함

**Python 비교**:
```python
# 비유하면
# run.py 실행 버튼은 만들었는데
# import 에러가 하나 남아 있으면 서버가 안 뜬다.
#
# 그래서
# 1) 실행 설정
# 2) import/코드 오류
# 둘 다 잡아야 한다.
```

**Java/Spring 연결**:
```java
private static final Logger log = LoggerFactory.getLogger(AdvisorA.class);
```

**흐름 타임라인**:
- 0ms: IntelliJ가 `Day03AdvisorMemoryApplication` 실행 설정을 찾습니다.
- 10ms: `workspace.xml`과 `.run` 설정에서 day03 실행 대상을 확인합니다.
- 20ms: Java 컴파일 단계에서 `AdvisorA.java`의 Lombok 미설정 문제가 있으면 즉시 중단됩니다.
- 30ms: Lombok 코드를 일반 `Logger` 방식으로 바꾸면 컴파일이 통과합니다.
- 40ms: `gradlew classes` 성공
- 50ms: `bootRun --server.port=8081`로 실제 기동 테스트
- 60ms: `/api/ask?question=test` 요청에서 200 응답 확인

**한 줄 메모**:
- 이번에는 IDE 실행 설정 문제와 Java 컴파일 오류가 함께 있었고, 둘 다 해결한 뒤에야 day03이 안정적으로 실행되었습니다.

---
## [2026-07-06] Q: 강사님 day03 파일 가져온 뒤 상태 확인

**질문**: 강사님 파일 가져왔어

**핵심 개념**:
- 기준 파일: 실습은 강사님 배포본을 기준으로 맞추는 게 가장 안전함
- 상태 점검: 새 파일을 가져오면 먼저 구조, 컴파일, 실행 가능 여부를 다시 확인해야 함
- 현재 상태: `day03-advisor-memory`는 컴파일 성공 상태

**Python 비교**:
```python
# 강사님 예제 파일을 새로 덮어온 뒤에는
# 먼저 import 에러 없는지 한 번 실행해보는 것과 비슷하다.
```

**Java/Spring 연결**:
```java
./gradlew.bat classes
```

**흐름 타임라인**:
- 0ms: 강사님 파일을 프로젝트에 반영합니다.
- 10ms: 파일 구조와 패키지를 다시 확인합니다.
- 20ms: Gradle `classes` 빌드를 실행합니다.
- 30ms: 컴파일이 성공하면 이제 그 파일 기준으로 수업을 이어갈 수 있습니다.

**한 줄 메모**:
- 강사님 파일 기준 현재 day03는 적어도 컴파일 단계에서는 정상입니다.

---
## [2026-07-06] Q: IntelliJ에서 `package org... does not exist`가 쏟아진 이유와 해결

**질문**: 빌드이(가) 실패했습니다. `package org.slf4j does not exist` ...

**핵심 개념**:
- 클래스패스(classpath): 외부 라이브러리를 Java가 찾는 길목
- Gradle 프로젝트 인식: IntelliJ가 프로젝트를 Gradle로 인식해야 Spring/SLF4J/Spring AI 라이브러리를 붙여줌
- 루트 워크스페이스: 여러 day 프로젝트를 한 폴더에서 열 때, 루트가 Gradle 프로젝트가 아니면 일부 모듈이 plain Java처럼 잡힐 수 있음

**Python 비교**:
```python
# 파이썬으로 비유하면
# requirements.txt는 있는데
# IDE가 그 가상환경을 연결 안 해서
# import flask, import requests가 전부 빨갛게 뜨는 상황과 비슷하다.
```

**Java/Spring 연결**:
```java
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.ai.chat.client.ChatClientRequest;
```

**흐름 타임라인**:
- 0ms: IntelliJ가 `spring-ai-study` 루트를 엽니다.
- 10ms: 루트가 Gradle 프로젝트가 아니면 day03를 일반 Java 모듈처럼 다룰 수 있습니다.
- 20ms: 그러면 `org.slf4j`, `org.springframework`, `org.springframework.ai` 라이브러리 클래스패스를 못 붙입니다.
- 30ms: 그래서 `package ... does not exist`가 한꺼번에 발생합니다.
- 40ms: 루트에 `settings.gradle`, `build.gradle`, Gradle wrapper를 만들어 Gradle 워크스페이스로 바꿉니다.
- 50ms: 루트 기준 `:day03-advisor-memory:classes` 빌드가 성공합니다.

**한 줄 메모**:
- 이번 오류는 Java 문법 문제보다 "IDE가 day03를 Gradle 프로젝트로 못 보고 있었다"가 본질이었습니다.

---
## [2026-07-06] Q: 8080 포트 종료

**질문**: 응 8080 좀 꺼봐

**핵심 개념**:
- 포트: 프로그램이 네트워크 요청을 받는 자리
- PID: 그 포트를 실제로 점유하고 있는 프로세스 번호
- 종료 확인: 프로세스를 죽인 뒤 포트가 비었는지 다시 확인해야 함

**Python 비교**:
```python
# Flask가 8080에서 돌고 있으면
# 그 프로세스를 끄기 전까지는 같은 포트로 새 서버를 못 띄운다.
```

**Java/Spring 연결**:
```powershell
Get-NetTCPConnection -LocalPort 8080
Stop-Process -Id 14460 -Force
```

**흐름 타임라인**:
- 0ms: `8080` 포트를 조회합니다.
- 10ms: `PID 14460` Java 프로세스가 점유 중인 것을 확인합니다.
- 20ms: 해당 프로세스를 강제 종료합니다.
- 30ms: 다시 포트 상태를 조회합니다.
- 40ms: `PORT_8080_CLEARED`로 비워진 상태를 확인합니다.

**한 줄 메모**:
- 2026-07-06 기준 `8080`을 점유하던 Java 프로세스(`PID 14460`)를 종료했고, 포트가 비워졌습니다.

---
## [2026-07-06] Q: Day3 오늘 수업과정 전체 코드 흐름 설명

**질문**: 오늘 수업과정 전체 코드흐름을 설명해줘

**핵심 개념**:
- Spring Boot 시작: `main()`이 서버와 Spring 컨테이너를 올림
- Controller: HTTP 요청을 받는 입구
- Service: AI 호출 로직이 실제로 들어 있는 곳
- Advisor: AI 요청/응답 앞뒤에서 공통 작업을 끼워 넣는 장치
- Chat Memory: 이전 대화를 다시 프롬프트에 실어 보내는 장치

**Python 비교**:
```python
# Flask/FastAPI 느낌
@app.get("/api/ask")
def ask(question: str):
    return ai_service.ask(question)

def ask_service(question):
    # middleware 비슷한 처리
    answer = llm_call(question)
    return answer
```

**Java/Spring 연결**:
```java
@GetMapping("/api/ask")
public String ask(@RequestParam String question) {
    return aiService.ask(question);
}
```

**흐름 타임라인**:
- 0ms: `Day03AdvisorMemoryApplication`이 Spring Boot 서버를 시작합니다.
- 10ms: Spring이 `AiController`, `AiService` 같은 Bean을 만듭니다.
- 20ms: 사용자가 `/api/ask?question=...` 요청을 보냅니다.
- 30ms: `AiController`가 `question` 값을 받습니다.
- 40ms: Controller가 `aiService.ask(question)`를 호출합니다.
- 50ms: `AiService`가 `chatClient.prompt()`로 AI 요청을 준비합니다.
- 60ms: `.advisors(new AdvisorA())`가 실행되어 Advisor 체인에 끼어듭니다.
- 70ms: `.user(question)`으로 사용자 질문이 프롬프트에 들어갑니다.
- 80ms: `.call()`이 실제 LLM 호출을 보냅니다.
- 90ms: Advisor가 응답 앞뒤에서 동작합니다.
- 100ms: `.content()`로 최종 문자열 응답만 꺼냅니다.
- 110ms: Controller가 그 문자열을 브라우저나 Bruno에 반환합니다.

**한 줄 메모**:
- 현재 우리 day03 코드는 Day3 수업 중 `Advisor` 부분은 연결되어 있고, `Chat Memory`는 아직 실제 코드에 붙어 있지 않습니다.

---
## [2026-07-06] Q: Day3 PDF 기준으로 전체 수업 코드 흐름 다시 설명

**질문**: 너가 첨부한 SpringAI Day3 문서 보고 답변해.

**핵심 개념**:
- `Advisor`: AI 호출 앞뒤에 공통 처리를 끼워 넣는 인터셉터
- `MessageChatMemoryAdvisor`: 이전 대화를 프롬프트에 다시 실어 보내는 메모리 장치
- `conversationId`: 어느 대화방의 기억을 꺼낼지 정하는 필수 키

**Python 비교**:
```python
# middleware 느낌
def advisor(question):
    print("전처리")
    answer = llm_call(question)
    print("후처리")
    return answer

# 대화 기억 느낌
memory["room1"] = [
    "내 이름은 민준이야",
    "네, 기억할게요"
]
```

**Java/Spring 연결**:
```java
return chatClient.prompt()
        .user(question)
        .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, conversationId))
        .call()
        .content();
```

**흐름 타임라인**:
- 0ms: Spring Boot가 `ApiController`, `AssistantService`, Advisor, Memory Bean을 준비합니다.
- 10ms: 사용자가 `/api/ask` 또는 `/api/chat-memory`로 요청을 보냅니다.
- 20ms: Controller가 요청 파라미터를 받습니다.
- 30ms: Service가 `chatClient.prompt()`를 시작합니다.
- 40ms: 기본 Advisor들이 전처리를 순서대로 실행합니다.
- 50ms: Memory Advisor가 `conversationId` 기준으로 이전 대화를 꺼냅니다.
- 60ms: 최종 프롬프트가 LLM으로 전송됩니다.
- 70ms: 응답이 돌아오면 Advisor 후처리가 역순으로 실행됩니다.
- 80ms: 이번 질문/응답이 memory 저장소에 기록됩니다.
- 90ms: Controller가 최종 문자열 응답을 반환합니다.

**한 줄 메모**:
- Day3 PDF의 핵심은 "질문 문장 자체"보다 "AI 호출 과정과 대화 맥락을 어떻게 관리하느냐"입니다.

---
## [2026-07-06] Q: `AdvisorA.java`의 `package lombok.extern.slf4j does not exist` 오류 해결

**질문**: 실패 / `:day03-advisor-memory:compileJava` / `AdvisorA.java package lombok.extern.slf4j does not exist`

**핵심 개념**:
- `import`: 실제로 쓰는 라이브러리만 가져와야 함
- Lombok: `@Slf4j`를 쓸 때 필요한 라이브러리
- 현재 상태: `AdvisorA.java`는 Lombok을 쓰지 않고 일반 `Logger`를 직접 쓰는 구조였음

**Python 비교**:
```python
# 파이썬으로 치면
# import pandas 해놓고 실제로는 안 쓰는데
# pandas가 설치 안 되어 있어서 실행이 막히는 상황과 비슷하다.
```

**Java/Spring 연결**:
```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
```

**흐름 타임라인**:
- 0ms: IntelliJ/Gradle이 `AdvisorA.java`를 컴파일합니다.
- 10ms: `lombok.extern.slf4j.Slf4j` import를 찾으려 합니다.
- 20ms: 현재 프로젝트에 Lombok 설정이 없어서 컴파일이 실패합니다.
- 30ms: 불필요한 Lombok import를 제거합니다.
- 40ms: 기존 `LoggerFactory` 기반 로거만 남깁니다.
- 50ms: `gradlew classes`가 다시 성공합니다.

**한 줄 메모**:
- 이번 오류는 "Lombok이 필요해서"가 아니라, "안 쓰는 Lombok import가 남아 있어서" 생긴 문제였습니다.

---
## [2026-07-06] Q: Day3 PDF 전체 코드에 한 줄씩 주석 달아 설명 요청

**질문**: 오늘 교육자료에 나오는 코드를 전부 가져와서 한 줄씩 주석달고 설명해줘

**핵심 개념**:
- 슬라이드 코드 정리: 흩어진 코드 조각을 한 문서에 모아 다시 읽을 수 있게 만드는 작업
- 코드 주석 학습: 실행되는 줄마다 "왜 이 줄이 필요한가"를 설명하며 이해하는 방식
- Day3 핵심: Advisor, Memory, conversationId, JDBC 전환

**Python 비교**:
```python
# 수업 슬라이드의 코드 조각들을
# 노트에 다시 모아놓고
# 줄마다 메모를 붙여 복습하는 방식과 같다.
```

**Java/Spring 연결**:
```java
chatClient.prompt()
        .user(question)
        .call()
        .content();
```

**흐름 타임라인**:
- 0ms: PDF에서 코드가 있는 슬라이드만 추출합니다.
- 10ms: 중복과 축약된 부분을 구분합니다.
- 20ms: 슬라이드 순서대로 Advisor, Memory, JDBC 코드를 재정리합니다.
- 30ms: 각 줄 위에 주석을 붙여 학습용 문서로 만듭니다.
- 40ms: 최종 문서를 `DAY3_PDF_CODE_ANNOTATED.md`로 저장합니다.

**한 줄 메모**:
- Day3 PDF 전체 코드 주석본은 `DAY3_PDF_CODE_ANNOTATED.md` 파일로 정리했습니다.

---
## [2026-07-06] Q: Day3를 교육자료 첫 단계부터 같이 진행

**질문**: 주석은 비효율적인 거 같애. 나랑 같이 교육자료대로 첫단계부터 가자

**핵심 개념**:
- 보일러플레이트 확인: 새 기능 붙이기 전에 "원래 이미 되는 최소 코드"를 먼저 이해해야 함
- Day3 1단계: `day03-advisor-memory-starter`의 기본 흐름 파악
- 기본 흐름: `Controller -> Service -> ChatClient -> AI 응답`

**Python 비교**:
```python
# 먼저 기본 Flask/FastAPI 엔드포인트 하나가
# 정상 동작하는지 이해한 뒤
# middleware나 session을 붙이는 것과 같다.
```

**Java/Spring 연결**:
```java
@GetMapping("/api/ask")
public String ask(@RequestParam String question) {
    return aiService.ask(question);
}
```

**흐름 타임라인**:
- 0ms: Spring Boot 서버가 시작됩니다.
- 10ms: Controller와 Service Bean이 생성됩니다.
- 20ms: 사용자가 `/api/ask`로 질문을 보냅니다.
- 30ms: Controller가 질문을 받습니다.
- 40ms: Service가 `ChatClient`로 AI를 호출합니다.
- 50ms: AI 응답 문자열이 반환됩니다.

**한 줄 메모**:
- Day3 첫 단계는 "Advisor를 붙이기 전, 기본 AI 호출 한 줄기가 어떻게 도는지"를 이해하는 것입니다.

---
## [2026-07-06] Q: Day3를 Bruno에서 어떻게 테스트하는지

**질문**: 내가 day3를 브루노에서 테스트할려면 어떻게 해야해?

**핵심 개념**:
- Bruno 테스트 기본 순서: 서버 실행 -> URL 확인 -> 요청 보내기
- 현재 엔드포인트: 지금 코드 기준으로는 `GET /api/ask` 하나가 열려 있음
- 쿼리 파라미터: `question` 값을 URL에 붙여서 보냄

**Python 비교**:
```python
# 브라우저나 requests로 이렇게 보내는 느낌
requests.get("http://localhost:8080/api/ask", params={"question": "안녕"})
```

**Java/Spring 연결**:
```java
@GetMapping("/api/ask")
public String ask(@RequestParam String question) {
    return aiService.ask(question);
}
```

**흐름 타임라인**:
- 0ms: Spring Boot 서버를 실행합니다.
- 10ms: Bruno에서 새 GET 요청을 만듭니다.
- 20ms: 주소를 `http://localhost:8080/api/ask`로 설정합니다.
- 30ms: `question` 쿼리 파라미터를 넣습니다.
- 40ms: Send를 누릅니다.
- 50ms: Controller -> Service -> ChatClient -> Advisor -> AI 응답 흐름이 실행됩니다.

**한 줄 메모**:
- 지금 day03 코드 기준 Bruno 테스트 시작점은 `GET /api/ask?question=...` 입니다.

---
## [2026-07-06] Q: `AdvisorA.java` Lombok import 재발 해결

**질문**: 실패 / `:day03-advisor-memory:compileJava` / `AdvisorA.java package lombok.extern.slf4j does not exist`

**핵심 개념**:
- 재발 버그: 강사님 파일을 다시 가져오면 이전 import가 다시 들어올 수 있음
- Lombok 미설정: `build.gradle`에 Lombok이 없으면 `import lombok...`에서 즉시 컴파일 실패
- 최소 수정: 안 쓰는 import 제거가 가장 빠른 해결

**Python 비교**:
```python
# 설치 안 된 라이브러리 import가
# 파일에 다시 들어오면 똑같이 import error가 반복되는 것과 같다.
```

**Java/Spring 연결**:
```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
```

**흐름 타임라인**:
- 0ms: `AdvisorA.java`를 다시 확인합니다.
- 10ms: `import lombok.extern.slf4j.Slf4j;`가 다시 들어온 것을 발견합니다.
- 20ms: 불필요한 Lombok import를 제거합니다.
- 30ms: 로거 필드를 `private static final Logger`로 정리합니다.
- 40ms: `gradlew classes`를 재실행합니다.
- 50ms: 빌드 성공을 확인합니다.

**한 줄 메모**:
- 이번 재발은 Lombok 기능이 필요한 문제가 아니라, 안 쓰는 Lombok import가 다시 들어온 문제였습니다.

---
## [2026-07-06] Q: ChatMemory를 메모리에 저장하는 단계까지 자세한 설명

**질문**: 방금까지 ChatMemory를 메모리에 저장하는 것까지 다뤘어. 자세히 설명해봐

**핵심 개념**:
- `ChatMemory`: 이전 대화를 다음 호출에 다시 넣어 맥락을 유지하는 장치
- `InMemoryChatMemoryRepository`: 대화를 서버 메모리(RAM)에 저장하는 저장소
- `MessageWindowChatMemory`: 최근 메시지 몇 개만 남기는 메모리 구현체
- `conversationId`: 어떤 대화방의 기억인지 구분하는 필수 키

**Python 비교**:
```python
memory = {
    "room1": [
        "내 이름은 민준이야",
        "네, 기억할게요"
    ]
}
```

**Java/Spring 연결**:
```java
ChatMemory chatMemory = MessageWindowChatMemory.builder()
        .chatMemoryRepository(new InMemoryChatMemoryRepository())
        .maxMessages(20)
        .build();
```

**흐름 타임라인**:
- 0ms: 사용자가 `conversationId`와 질문을 보냅니다.
- 10ms: Controller가 요청을 받습니다.
- 20ms: Service가 `chatClient.prompt()`를 시작합니다.
- 30ms: `MessageChatMemoryAdvisor`가 `conversationId`에 맞는 이전 대화를 메모리에서 꺼냅니다.
- 40ms: 이전 대화 + 이번 질문을 합쳐 LLM으로 보냅니다.
- 50ms: LLM이 응답을 만듭니다.
- 60ms: 이번 질문과 응답이 다시 메모리에 저장됩니다.
- 70ms: 다음 질문 때 이 저장된 대화가 다시 사용됩니다.

**한 줄 메모**:
- In-Memory ChatMemory는 "서버가 살아 있는 동안만 유지되는 대화 메모장"입니다.

---
## [2026-07-06] Q: `AdvisorA.java`, `ChatMemoryConfig.java` 컴파일 실패 해결

**질문**: 실패 / `AdvisorA.java package lombok.extern.slf4j does not exist` / `ChatMemoryConfig.java`

**핵심 개념**:
- `AdvisorA`: 안 쓰는 Lombok import 때문에 컴파일 실패
- `ChatMemoryConfig`: JDBC 메모리 저장소 코드는 있는데 `build.gradle`에 JDBC/H2 의존성이 없어서 컴파일 실패
- Day3 흐름: In-Memory만 하다가 JDBC 단계 코드까지 들어오면 의존성도 같이 추가해야 함

**Python 비교**:
```python
# import는 있는데 라이브러리 설치가 안 되어 있으면
# import error가 나는 상황과 같다.
```

**Java/Spring 연결**:
```gradle
implementation 'org.springframework.boot:spring-boot-starter-jdbc'
implementation 'org.springframework.ai:spring-ai-starter-model-chat-memory-repository-jdbc'
runtimeOnly 'com.h2database:h2'
```

**흐름 타임라인**:
- 0ms: `AdvisorA.java`와 `ChatMemoryConfig.java` 오류를 확인합니다.
- 10ms: `AdvisorA`의 Lombok import가 불필요하다는 것을 확인합니다.
- 20ms: `ChatMemoryConfig`가 `JdbcChatMemoryRepository`를 쓰고 있는데, 관련 의존성이 없다는 것을 확인합니다.
- 30ms: `AdvisorA`에서 Lombok import를 제거합니다.
- 40ms: `build.gradle`에 JDBC 메모리 저장소와 H2 의존성을 추가합니다.
- 50ms: `gradlew classes`를 다시 실행합니다.
- 60ms: 전체 컴파일 성공을 확인합니다.

**한 줄 메모**:
- 이번 빌드 실패는 코드 문법보다 "안 쓰는 import 한 줄 + 빠진 의존성 3개"가 원인이었습니다.

---
## [2026-07-06] Q: "아직 실행이 안돼" 원인 재확인

**질문**: 아직 실행이 안돼

**핵심 개념**:
- H2 파일 잠금: 같은 `chatmemory.mv.db` 파일을 두 개의 서버 프로세스가 동시에 열 수 없음
- 이전 인스턴스 잔존: 예전 day03 서버가 살아 있으면 새 실행이 같은 DB 파일에서 실패함
- 현재 상태 검증: 로그가 아니라 실제 `bootRun` + HTTP 응답으로 확인해야 함

**Python 비교**:
```python
# sqlite 파일을 이미 한 프로세스가 잡고 있는데
# 다른 프로세스가 같은 파일 DB를 동시에 강하게 열려고 해서 충돌나는 느낌과 비슷하다.
```

**Java/Spring 연결**:
```yaml
spring:
  datasource:
    url: jdbc:h2:file:./data/chatmemory
```

**흐름 타임라인**:
- 0ms: day03가 시작됩니다.
- 10ms: H2 파일 DB `chatmemory.mv.db`에 연결을 시도합니다.
- 20ms: 이미 살아 있던 이전 day03 서버가 같은 파일을 잡고 있으면 잠금 충돌이 납니다.
- 30ms: 새 서버가 `Database may be already in use`로 종료됩니다.
- 40ms: 기존 day03 Java 프로세스를 종료합니다.
- 50ms: 다시 `bootRun` 실행
- 60ms: 서버 시작 성공, `/api/ask` 200 응답 확인

**한 줄 메모**:
- 이번 실행 실패의 진짜 원인은 코드가 아니라 "이전 day03 서버가 H2 파일 DB를 잡고 있어서 생긴 잠금 충돌"이었습니다.

---
## [2026-07-06] Q: `build.gradle`의 `runtimeOnly spring-boot-h2console` 오류 해결

**질문**: 실패 / `Could not find method runtimeOnly() for arguments [org.springframework.boot:spring-boot-h2console]`

**핵심 개념**:
- `dependencies {}` 블록 안과 밖은 다르게 해석됨
- H2 콘솔은 별도 `spring-boot-h2console` 의존성을 넣는 방식이 아님
- `runtimeOnly`는 `dependencies {}` 안에서만 써야 함

**Python 비교**:
```python
# requirements.txt에 없는 패키지 이름을 쓰거나
# import 문을 엉뚱한 위치에 둔 것과 비슷한 실수다.
```

**Java/Spring 연결**:
```gradle
runtimeOnly 'com.h2database:h2'
```

**흐름 타임라인**:
- 0ms: Gradle이 `build.gradle`을 읽습니다.
- 10ms: `dependencies {}` 바깥의 `runtimeOnly ...`를 프로젝트 메서드처럼 해석합니다.
- 20ms: `Could not find method runtimeOnly()` 에러가 발생합니다.
- 30ms: `spring-boot-h2console` 줄을 제거합니다.
- 40ms: 맨 아래에 튀어나온 `runtimeOnly` 한 줄도 제거합니다.
- 50ms: `gradlew classes`를 다시 실행합니다.
- 60ms: 빌드 성공을 확인합니다.

**한 줄 메모**:
- 이번 오류는 H2 라이브러리 부족이 아니라, 잘못된 의존성 이름과 잘못된 위치 때문에 생긴 Gradle 문법 오류였습니다.

---
## [2026-07-06] Q: `http://localhost:8080/h2-console` 화면이 안 뜨는 이유

**질문**: http://localhost:8080/h2-console 왜 화면이 안 뜰까

**핵심 개념**:
- H2 콘솔 모듈: Spring Boot 4.1에서는 H2 콘솔용 모듈이 classpath에 있어야 콘솔이 등록됨
- 정확한 경로: 실제 접근은 `/h2-console/`처럼 끝 슬래시가 붙는 경로가 더 안정적
- 오래된 서버: 예전 8080 서버가 떠 있으면 새 설정이 반영되지 않음

**Python 비교**:
```python
# Flask 라우트가 /admin/ 으로 잡혀 있는데
# /admin 만 쳐서 리다이렉트가 꼬이는 상황과 비슷하다.
```

**Java/Spring 연결**:
```yaml
spring:
  h2:
    console:
      enabled: true
```

**흐름 타임라인**:
- 0ms: `application.yaml`에 `spring.h2.console.enabled: true`를 확인합니다.
- 10ms: `spring-boot-h2console` 모듈을 `build.gradle`에 추가합니다.
- 20ms: 기존 8080 서버를 종료합니다.
- 30ms: 새 서버를 다시 실행합니다.
- 40ms: `/h2-console`은 리다이렉트가 발생합니다.
- 50ms: `/h2-console/`로 접근하면 H2 Console HTML이 200 응답으로 열립니다.

**한 줄 메모**:
- 현재 H2 콘솔은 `http://localhost:8080/h2-console/` 경로로 열리는 것을 확인했습니다.

---
## [2026-07-06] Q: H2 콘솔 화면에서 무엇을 해야 하고, DBeaver에서는 어떻게 확인하는지

**질문**: 여기서 뭘 해야하고 Dbeaver에서는 어떻게 정보를 확인할 수 있어? 전체 플로우 말해봐.

**핵심 개념**:
- H2 콘솔: 앱 안에서 같은 프로세스로 H2 파일 DB를 보는 브라우저 도구
- DBeaver: 외부 프로그램에서 H2 파일 DB를 여는 도구
- 현재 DB 파일: `day03-advisor-memory/data/chatmemory.mv.db`
- 실제 저장 대상: JDBC 메모리 엔드포인트 `/api/chat-persistent`

**Python 비교**:
```python
# 메모리 버전은 dict에 저장되고,
# persistent 버전은 sqlite 파일 같은 곳에 저장된다고 생각하면 된다.
```

**Java/Spring 연결**:
```java
@GetMapping("/api/chat-persistent")
public String chatPersistent(@RequestParam String question, @RequestParam String conversationId){
    return persistentChatService.chat(question, conversationId);
}
```

**흐름 타임라인**:
- 0ms: Spring Boot 서버를 실행합니다.
- 10ms: `/api/chat-persistent`로 같은 `conversationId`를 써서 질문을 여러 번 보냅니다.
- 20ms: `PersistentChatService`가 JDBC 기반 `ChatMemory`에 대화를 저장합니다.
- 30ms: 브라우저에서 `/h2-console/`로 접속합니다.
- 40ms: JDBC URL을 실제 DB 파일 경로로 바꾸고 로그인합니다.
- 50ms: `SPRING_AI_CHAT_MEMORY` 테이블을 조회합니다.
- 60ms: DBeaver에서는 서버를 끄고 같은 H2 파일에 연결해 테이블과 데이터를 확인합니다.

**한 줄 메모**:
- 지금 프로젝트에서 DB에 실제로 쌓이는 쪽은 `/api/chat-memory`가 아니라 `/api/chat-persistent` 입니다.

---
## [2026-07-06] Q: Day3 기반 케어링크 연관 미니 프로젝트 아이디어

**질문**: 자 이제 오늘 배운 내용을 기반으로 미니 프로젝트를 해보자. 어떤 게좋을까? 케어링크와 연관되면 좋을 것 같아.

**핵심 개념**:
- Day3 활용 포인트: `Advisor` + `Chat Memory`
- CareLink 연결 포인트: 보호자/요양보호사/상담 문의 응대
- 미니 프로젝트 기준: 지금 배운 기술이 바로 보이는 작고 명확한 문제를 고르는 게 좋음

**Python 비교**:
```python
# 그냥 단발 질문 답변봇보다
# 이전 대화를 기억하고
# 금칙어/로그/길이제한 같은 공통 규칙이 붙는 챗봇이
# 오늘 수업 복습에 더 맞다.
```

**Java/Spring 연결**:
```java
defaultAdvisors(...)
MessageChatMemoryAdvisor.builder(chatMemory).build()
```

**흐름 타임라인**:
- 0ms: 사용자가 CareLink 관련 질문을 보냅니다.
- 10ms: Controller가 요청을 받습니다.
- 20ms: Service가 ChatClient를 시작합니다.
- 30ms: Advisor가 공통 규칙(로그, 길이 제한, 민감 단어 차단)을 적용합니다.
- 40ms: Memory가 이전 상담 내용을 불러옵니다.
- 50ms: AI가 이어지는 답변을 생성합니다.
- 60ms: 필요하면 DB(H2)에 대화가 저장됩니다.

**한 줄 메모**:
- Day3 미니 프로젝트는 "상담형 챗봇"이 가장 자연스럽고, 그중 CareLink와 가장 잘 맞는 건 보호자/요양 상담 도우미입니다.
---
## [2026-07-06] Q: H2 대신 MariaDB로 별도 Spring AI 미니 프로젝트를 0부터 시작하기

**질문**: 그래 좋은 주제야. 대신 정보 저장은 MariaDB에 하자.
되돌리고 별도 디렉터리 만들어서 처음부터 다시 시작하자. 0 to 1
아니 처음부터 같이 하자

**핵심 개념**:
- 별도 디렉터리: 기존 day03 프로젝트를 건드리지 않고 새 프로젝트를 독립적으로 만든다.
- JDBC Chat Memory: 대화 기록을 메모리가 아니라 DB에 저장할 수 있다.
- MariaDB datasource: Spring Boot가 MariaDB에 접속해서 Spring AI 메모리 테이블을 자동 생성할 수 있다.
- 최소 실행 단위: 질문 받기, AI 호출, conversationId로 대화 이어가기, DB 저장까지 먼저 만든다.

**Python 비교**:
```python
# 기존 실습 파일을 덮어쓰지 않고
# 새 폴더에 app.py를 하나 새로 만드는 느낌이다.

conversation_store = {}  # 메모리 버전
# 이걸 나중에는 MariaDB 테이블에 저장하는 구조로 바꾼 셈이다.
```

**Java/Spring 연결**:
```java
@GetMapping("/api/guardian-chat")
public GuardianChatResponse chat(@RequestParam String question,
                                 @RequestParam String conversationId) {
    return guardianChatService.chat(question, conversationId);
}
```

**흐름 타임라인**:
- 0ms: 새 프로젝트 폴더 `carelink-ai-mariadb`를 만든다.
- 10ms: `build.gradle`에서 Spring Boot, Spring AI, JDBC, MariaDB 드라이버를 연결한다.
- 20ms: `application.yaml`에서 MariaDB 접속 정보를 받도록 설정한다.
- 30ms: Controller가 질문과 conversationId를 받는다.
- 40ms: Service가 `ChatClient`로 AI 호출을 준비한다.
- 50ms: `MessageChatMemoryAdvisor`가 같은 conversationId의 이전 대화를 DB에서 이어 붙인다.
- 60ms: AI 응답이 생성되고 `SPRING_AI_CHAT_MEMORY` 테이블에 대화가 저장된다.

**한 줄 메모**:
- 이번에는 day03를 복사해서 덮는 방식이 아니라, `carelink-ai-mariadb`라는 새 폴더에서 독립적으로 시작했고 `classes` 빌드까지 성공했다.
---
## [2026-07-06] Q: carelink-ai-mariadb 프로젝트 코드를 하나씩 설명하기

**질문**: 코드 하나 하나 다 설명해봐

**핵심 개념**:
- 엔트리포인트: Spring Boot 앱은 main 메서드에서 시작된다.
- Controller: URL 요청을 받는 입구다.
- Service: 실제 AI 호출과 대화 메모리 처리 로직이 들어간다.
- Advisor: AI 호출 전후에 공통 규칙을 끼워 넣는 장치다.
- ChatMemory: conversationId 기준으로 이전 대화를 이어주는 기억 장치다.
- application.yaml: AI 모델, DB, 로그 같은 실행 설정을 적는 파일이다.

**Python 비교**:
```python
# Flask + service 함수 + 전역 설정 파일을 나눠놓은 구조와 비슷하다.

@app.get('/api/guardian-chat')
def chat(question, conversation_id):
    return guardian_chat_service.chat(question, conversation_id)
```

**Java/Spring 연결**:
```java
String answer = chatClient.prompt()
        .system(SYSTEM_PROMPT)
        .user(question)
        .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, conversationId))
        .call()
        .content();
```

**흐름 타임라인**:
- 0ms: 사용자가 `/api/guardian-chat`로 질문과 conversationId를 보낸다.
- 10ms: `GuardianChatController`가 요청을 받는다.
- 20ms: `GuardianChatService`로 question, conversationId를 넘긴다.
- 30ms: `RequestLoggingAdvisor`가 호출 시작 로그를 남긴다.
- 40ms: `MessageChatMemoryAdvisor`가 같은 conversationId의 이전 대화를 DB에서 읽는다.
- 50ms: `ChatClient`가 system prompt와 user question을 합쳐 AI에 보낸다.
- 60ms: AI 응답이 오면 메모리가 MariaDB에 저장된다.
- 70ms: `GuardianChatResponse` 형태로 JSON 응답이 반환된다.

**한 줄 메모**:
- 이 프로젝트는 “보호자 질문 -> Spring Controller -> Service -> ChatClient -> Advisor + MariaDB 메모리 -> JSON 응답” 흐름으로 이해하시면 된다.
---
## [2026-07-06] Q: GuardianChatService.java를 한 줄씩 깊게 이해하기

**질문**: 응 해봐

**핵심 개념**:
- Service 계층: Controller가 받은 값을 실제로 처리하는 작업 공간이다.
- ChatClient.Builder: AI 호출기를 바로 쓰는 게 아니라 설정을 붙여서 만든다.
- MessageChatMemoryAdvisor: conversationId를 기준으로 이전 대화를 붙여준다.
- System Prompt: AI의 역할, 말투, 안전 규칙을 먼저 고정하는 지시문이다.
- DTO 반환: 최종 응답을 JSON 구조로 깔끔하게 묶어서 돌려준다.

**Python 비교**:
```python
SYSTEM_PROMPT = """
너는 보호자 상담 도우미야.
한국어로 답해.
이전 대화 맥락도 이어서 답해.
"""

def chat(question, conversation_id):
    answer = ai_client.ask(
        system=SYSTEM_PROMPT,
        user=question,
        conversation_id=conversation_id,
    )
    return {
        "conversationId": conversation_id,
        "answer": answer,
    }
```

**Java/Spring 연결**:
```java
String answer = chatClient.prompt()
        .system(SYSTEM_PROMPT)
        .user(question)
        .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, conversationId))
        .call()
        .content();
```

**흐름 타임라인**:
- 0ms: Controller가 `question`, `conversationId`를 `GuardianChatService`로 넘긴다.
- 10ms: `chatClient.prompt()`로 AI 대화 1회를 시작한다.
- 20ms: `system()`으로 AI 역할 규칙을 붙인다.
- 30ms: `user()`로 실제 보호자 질문을 넣는다.
- 40ms: `conversationId`를 advisor에 넘겨 같은 대화방 기록을 찾게 한다.
- 50ms: AI 호출이 실행되고 이전 대화 + 새 질문 기준으로 응답이 생성된다.
- 60ms: 응답 본문만 꺼내서 `GuardianChatResponse`에 담아 반환한다.

**한 줄 메모**:
- `GuardianChatService`는 이 프로젝트의 핵심 엔진이고, 질문 + 역할 지시 + 대화 기억 + 응답 포장을 한 번에 담당한다.
---
## [2026-07-06] Q: GuardianChatController.java와 @RequestParam 흐름 이해하기

**질문**: 응 확인해봐

**핵심 개념**:
- Controller: HTTP 요청을 가장 먼저 받는 입구다.
- @RestController: 이 클래스가 웹 요청을 받고 응답을 바로 돌려주는 창구라는 표시다.
- @RequestMapping: 클래스 공통 URL 앞부분을 정한다.
- @GetMapping: 특정 GET 주소를 이 메서드에 연결한다.
- @RequestParam: URL의 쿼리스트링 값을 자바 변수로 꺼내온다.
- Service 위임: Controller는 직접 AI 호출을 하지 않고 Service에 일을 넘긴다.

**Python 비교**:
```python
@app.get('/api/guardian-chat')
def chat():
    question = request.args.get('question')
    conversation_id = request.args.get('conversationId')
    return guardian_chat_service.chat(question, conversation_id)
```

**Java/Spring 연결**:
```java
@GetMapping("/guardian-chat")
public GuardianChatResponse chat(@RequestParam String question,
                                 @RequestParam String conversationId) {
    return guardianChatService.chat(question, conversationId);
}
```

**흐름 타임라인**:
- 0ms: 브루노가 `/api/guardian-chat?question=...&conversationId=guardian-1` 요청을 보낸다.
- 10ms: Spring이 `GuardianChatController`의 `chat()` 메서드가 이 URL 담당이라는 걸 찾는다.
- 20ms: URL에서 `question` 값을 꺼내 자바의 `question` 변수에 넣는다.
- 30ms: URL에서 `conversationId` 값을 꺼내 자바의 `conversationId` 변수에 넣는다.
- 40ms: Controller가 `guardianChatService.chat(question, conversationId)`를 호출한다.
- 50ms: Service가 AI 호출과 메모리 처리를 한다.
- 60ms: 결과가 `GuardianChatResponse`로 돌아오고 JSON 응답이 된다.

**한 줄 메모**:
- `GuardianChatController`는 데이터를 직접 처리하는 곳이 아니라, URL에서 값을 꺼내 Service로 전달하는 접수창구다.
---
## [2026-07-06] Q: ChatMemoryConfig.java로 MariaDB 메모리 구조 이해하기

**질문**: 보자

**핵심 개념**:
- @Configuration: Spring용 부품 조립실이다.
- @Bean: Spring이 관리할 객체를 직접 만들어 등록하는 방법이다.
- ChatMemory: 대화 기억 기능의 공통 타입이다.
- JdbcChatMemoryRepository: 메모리를 실제 DB에 저장하는 저장소다.
- MessageWindowChatMemory: 이전 대화를 무한정이 아니라 최근 N개만 기억하는 메모리 구현체다.
- application.yaml 연동: DB 주소와 드라이버는 YAML 설정에서 받고, Config는 그걸 이용해 메모리 객체를 만든다.

**Python 비교**:
```python
# Python으로 치면
# DB 연결 객체를 받아서
# 최근 20개 대화를 기억하는 memory 객체를 생성하는 함수 느낌이다.

def make_chat_memory(db_repo):
    return MessageWindowChatMemory(
        repository=db_repo,
        max_messages=20,
    )
```

**Java/Spring 연결**:
```java
@Bean("jdbcChatMemory")
public ChatMemory jdbcChatMemory(JdbcChatMemoryRepository jdbcChatMemoryRepository) {
    return MessageWindowChatMemory.builder()
            .chatMemoryRepository(jdbcChatMemoryRepository)
            .maxMessages(20)
            .build();
}
```

**흐름 타임라인**:
- 0ms: Spring Boot가 시작된다.
- 10ms: `application.yaml`에서 MariaDB 접속 정보와 JDBC 메모리 초기화 설정을 읽는다.
- 20ms: Spring AI가 `JdbcChatMemoryRepository`를 준비한다.
- 30ms: `ChatMemoryConfig`의 `jdbcChatMemory()` 메서드가 호출된다.
- 40ms: `MessageWindowChatMemory`가 생성된다.
- 50ms: 이 객체는 내부적으로 MariaDB 저장소를 사용하게 된다.
- 60ms: 나중에 `GuardianChatService`가 이 Bean을 받아서 conversationId 기반 대화 기억에 사용한다.

**한 줄 메모**:
- `ChatMemoryConfig`는 “기억을 한다”가 아니라 “기억을 어떤 방식으로, 어느 저장소에, 몇 개까지 할지 조립하는 곳”이다.
---
## [2026-07-06] Q: application.yaml 한 줄씩 이해하기

**질문**: 보자

**핵심 개념**:
- application.yaml: Spring Boot 앱의 실행 설정 파일이다.
- spring.ai.google.genai: 어떤 AI 모델과 API 키를 쓸지 정한다.
- spring.ai.chat.memory.repository.jdbc: 대화 메모리를 JDBC 방식으로 저장하도록 켠다.
- spring.datasource: 어떤 DB에 접속할지 주소, 아이디, 비밀번호, 드라이버를 정한다.
- 환경변수 기본값 문법: `${VAR:default}` 는 환경변수가 없으면 기본값을 사용한다.
- logging.level: 로그를 어느 정도 자세히 볼지 정한다.

**Python 비교**:
```python
# Python이라면 config.py 또는 .env 읽는 부분에 해당한다.
MODEL = os.getenv('MODEL', 'gemini-3.1-flash-lite')
API_KEY = os.getenv('GOOGLE_API_KEY')
DB_URL = os.getenv('MARIADB_URL', 'jdbc:mariadb://localhost:3306/carelink_ai')
```

**Java/Spring 연결**:
```yaml
spring:
  datasource:
    url: ${MARIADB_URL:jdbc:mariadb://localhost:3306/carelink_ai}
    username: ${MARIADB_USERNAME:root}
    password: ${MARIADB_PASSWORD:}
    driver-class-name: org.mariadb.jdbc.Driver
```

**흐름 타임라인**:
- 0ms: Spring Boot가 시작되면서 `application.yaml`을 읽는다.
- 10ms: 앱 이름, AI 모델, API 키 설정을 메모리에 올린다.
- 20ms: MariaDB 접속 주소와 계정을 읽는다.
- 30ms: Spring AI가 JDBC 메모리 저장소를 준비한다.
- 40ms: `initialize-schema: always`에 따라 메모리 테이블 생성 여부를 확인한다.
- 50ms: `ChatMemoryConfig`가 DB 저장소를 써서 `ChatMemory` Bean을 만든다.
- 60ms: 이후 Controller/Service가 실행될 때 이 설정값들을 기반으로 앱이 동작한다.

**한 줄 메모**:
- `application.yaml`은 코드가 아니라 설정이지만, 실제로는 “어떤 AI를 쓰고 어느 DB에 저장할지”를 결정하는 실행 중심 파일이다.
---
## [2026-07-06] Q: RequestLoggingAdvisor.java로 Advisor 개념 이해하기

**질문**: 응 보자

**핵심 개념**:
- Advisor: AI 호출 전후에 공통 로직을 끼워 넣는 장치다.
- CallAdvisor: 실제 AI 호출을 감싸는 인터페이스다.
- CallAdvisorChain: 다음 advisor 또는 실제 AI 호출로 넘기는 통로다.
- Logging: AI 요청이 언제 시작되고 끝나는지 로그로 남길 수 있다.
- getOrder(): 여러 advisor가 있을 때 실행 순서를 정한다.

**Python 비교**:
```python
# Python 미들웨어나 데코레이터 느낌이다.

def logging_wrapper(next_call):
    print('advisor start')
    response = next_call()
    print('advisor end')
    return response
```

**Java/Spring 연결**:
```java
public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
    log.info("[guardian-chat] advisor start");
    ChatClientResponse response = callAdvisorChain.nextCall(chatClientRequest);
    log.info("[guardian-chat] advisor end");
    return response;
}
```

**흐름 타임라인**:
- 0ms: `GuardianChatService`가 `ChatClient`에 `RequestLoggingAdvisor`를 기본 advisor로 등록한다.
- 10ms: 사용자가 질문을 보내고 `chatClient.call()`이 실행된다.
- 20ms: `RequestLoggingAdvisor`가 먼저 끼어든다.
- 30ms: 시작 로그를 남긴다.
- 40ms: `callAdvisorChain.nextCall(...)`로 다음 advisor 또는 실제 AI 호출로 넘긴다.
- 50ms: AI 응답이 돌아온다.
- 60ms: 종료 로그를 남긴 뒤 응답을 그대로 반환한다.

**한 줄 메모**:
- `RequestLoggingAdvisor`는 답변 내용을 바꾸는 역할이 아니라, AI 호출 흐름 앞뒤에 공통 처리를 붙이는 첫 연습용 advisor다.
---
## [2026-07-06] Q: 앱 시작 파일과 응답 DTO 이해하기

**질문**: 보자

**핵심 개념**:
- @SpringBootApplication: Spring Boot 앱의 시작점 표시다.
- main 메서드: 자바 프로그램 실행 진입점이다.
- SpringApplication.run(): Spring 컨테이너를 띄우고 Controller, Service, Config를 자동 등록한다.
- record: 데이터를 간단히 담는 자바 문법이다.
- DTO: 응답용 데이터 구조를 분리해서 JSON 형태를 명확하게 만든다.

**Python 비교**:
```python
if __name__ == '__main__':
    app.run()

response = {
    'conversationId': 'guardian-1',
    'answer': '응답 내용'
}
```

**Java/Spring 연결**:
```java
@SpringBootApplication
public class CarelinkAiMariadbApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarelinkAiMariadbApplication.class, args);
    }
}
```

```java
public record GuardianChatResponse(String conversationId, String answer) {
}
```

**흐름 타임라인**:
- 0ms: `main()`이 실행된다.
- 10ms: `SpringApplication.run(...)`이 Spring Boot 앱을 시작한다.
- 20ms: Spring이 Controller, Service, Advisor, Config Bean을 자동으로 스캔한다.
- 30ms: 사용자가 `/api/guardian-chat` 요청을 보낸다.
- 40ms: 전체 처리 후 `GuardianChatResponse` 객체가 만들어진다.
- 50ms: Spring이 이 객체를 JSON으로 바꿔 응답한다.

**한 줄 메모**:
- `CarelinkAiMariadbApplication`은 앱의 시동 버튼이고, `GuardianChatResponse`는 최종 응답 상자다.
---
## [2026-07-06] Q: carelink-ai-mariadb 전체 실행 흐름을 하나로 묶어보기

**질문**: 좋아 묶자

**핵심 개념**:
- Spring Boot 시작점: main 메서드에서 앱이 켜진다.
- Controller -> Service -> Advisor -> AI -> DB -> Response 순서로 흐른다.
- application.yaml: AI 모델과 MariaDB 연결 정보를 제공한다.
- ChatMemory: conversationId를 기준으로 이전 대화를 이어준다.
- DTO 응답: 최종 결과를 JSON 구조로 돌려준다.

**Python 비교**:
```python
# 큰 흐름만 보면 이런 느낌이다.
# app 시작 -> 라우트 등록 -> 요청 받기 -> 서비스 호출 -> DB 메모리 읽기 -> AI 호출 -> 응답 반환

@app.get('/api/guardian-chat')
def chat(question, conversation_id):
    history = db.load(conversation_id)
    answer = ai.ask(system=SYSTEM_PROMPT, user=question, history=history)
    db.save(conversation_id, question, answer)
    return {
        'conversationId': conversation_id,
        'answer': answer,
    }
```

**Java/Spring 연결**:
```java
@GetMapping("/guardian-chat")
public GuardianChatResponse chat(@RequestParam String question,
                                 @RequestParam String conversationId) {
    return guardianChatService.chat(question, conversationId);
}
```

**흐름 타임라인**:
- 0ms: `CarelinkAiMariadbApplication.main()`이 실행된다.
- 10ms: Spring Boot가 `application.yaml`을 읽고 AI, MariaDB, 로그 설정을 로드한다.
- 20ms: Spring이 Controller, Service, Config, Advisor Bean을 생성한다.
- 30ms: `ChatMemoryConfig`가 JDBC 기반 `ChatMemory`를 만든다.
- 40ms: 사용자가 `/api/guardian-chat?question=...&conversationId=...` 요청을 보낸다.
- 50ms: `GuardianChatController`가 URL 파라미터를 꺼내 `GuardianChatService`로 넘긴다.
- 60ms: `RequestLoggingAdvisor`가 호출 시작 로그를 남긴다.
- 70ms: `MessageChatMemoryAdvisor`가 같은 `conversationId`의 이전 대화를 MariaDB에서 읽는다.
- 80ms: `ChatClient`가 system prompt와 user question을 AI에 보낸다.
- 90ms: AI 응답이 돌아온다.
- 100ms: 새 대화 내용이 MariaDB 메모리 저장소에 기록된다.
- 110ms: `GuardianChatResponse`가 만들어진다.
- 120ms: Spring이 응답 객체를 JSON으로 바꿔 사용자에게 돌려준다.

**한 줄 메모**:
- 이 프로젝트는 “보호자 질문을 받아서, 이전 대화를 DB에서 이어 붙이고, AI 답변을 만든 뒤, 다시 DB에 저장하고 JSON으로 반환하는 구조”다.
---
## [2026-07-06] Q: carelink-ai-mariadb 프로젝트가 지금 실행되는지 확인하기

**질문**: 그건 나중에 하고 지금 실행이 되는 거야?

**핵심 개념**:
- compile 성공과 bootRun 성공은 다르다.
- Spring Boot는 실행 도중 DB 연결이 안 되면 앱 시작 자체가 실패할 수 있다.
- connection refused는 보통 DB 서버가 안 떠 있거나 포트가 닫혀 있다는 뜻이다.
- `Failed to determine DatabaseDriver`는 이번 경우 근본 원인이 아니라, DB 연결 실패 뒤에 따라온 연쇄 오류다.

**Python 비교**:
```python
# import는 성공했는데
# app.run() 중에 DB.connect()에서 죽는 상황과 비슷하다.

app = create_app()
app.run()  # 여기서 DB 연결 실패로 종료
```

**Java/Spring 연결**:
```yaml
spring:
  datasource:
    url: ${MARIADB_URL:jdbc:mariadb://localhost:3306/carelink_ai}
```

**흐름 타임라인**:
- 0ms: `./gradlew.bat classes`는 성공했다.
- 10ms: `./gradlew.bat bootRun`으로 실제 실행을 시도했다.
- 20ms: Tomcat 8080 초기화까지는 진행됐다.
- 30ms: Spring이 MariaDB로 JDBC 연결을 시도했다.
- 40ms: `localhost:3306` 연결이 거부되었다.
- 50ms: ChatMemory용 JDBC 초기화가 실패했다.
- 60ms: 앱 전체가 시작 실패로 종료되었다.

**한 줄 메모**:
- 현재 상태는 “코드 컴파일 성공, 실제 실행 실패”이고 원인은 `localhost:3306` MariaDB 연결 거부다.
---
## [2026-07-06] Q: H2로 전환하고 DBeaver 연동 가능한 상태 만들기

**질문**: 그러면 h2로 하고 Dbeaver로 연동되게끔 해보자.

**핵심 개념**:
- H2 file DB: 학습용으로 가볍고 무료이며 파일 하나로 저장된다.
- AUTO_SERVER=TRUE: 앱과 외부 도구가 같은 H2 파일에 접근할 수 있게 돕는 옵션이다.
- H2 Console: 브라우저에서 H2 DB를 확인하는 내장 화면이다.
- DBeaver 연결: H2 파일 경로와 같은 JDBC URL을 써서 연결한다.
- compile 성공과 bootRun 성공은 다르며, 이번에는 실제 bootRun까지 성공했다.

**Python 비교**:
```python
# sqlite 파일을 앱에서도 쓰고
# 외부 DB 툴에서도 여는 느낌과 비슷하다.
DB_URL = 'jdbc:h2:file:./data/chatmemory;AUTO_SERVER=TRUE'
```

**Java/Spring 연결**:
```yaml
spring:
  datasource:
    url: jdbc:h2:file:./data/chatmemory;AUTO_SERVER=TRUE
    username: sa
    password:
    driver-class-name: org.h2.Driver
  h2:
    console:
      enabled: true
      path: /h2-console
```

**흐름 타임라인**:
- 0ms: `build.gradle`에서 MariaDB 드라이버를 빼고 H2 드라이버와 H2 console 의존성을 추가했다.
- 10ms: `application.yaml`을 H2 file DB 설정으로 바꿨다.
- 20ms: `./gradlew.bat classes`가 성공했다.
- 30ms: 첫 bootRun 실패 원인을 H2 URL 옵션 조합 문제로 확인했다.
- 40ms: `DB_CLOSE_ON_EXIT=FALSE`를 제거해 URL을 수정했다.
- 50ms: 다시 `bootRun`을 실행했고 H2 연결이 성공했다.
- 60ms: `/h2-console/` HTTP 200 응답을 확인했다.
- 70ms: `/api/guardian-chat` 호출이 HTTP 200으로 성공했다.
- 80ms: `data/chatmemory.mv.db` 파일 생성까지 확인했다.

**한 줄 메모**:
- 현재 carelink-ai 프로젝트는 H2 file DB로 실제 실행 중이며, H2 콘솔과 DBeaver 연결을 시도할 수 있는 상태다.
---
## [2026-07-06] Q: DBeaver에서 H2 파일 DB 연결하기

**질문**: 그러자

**핵심 개념**:
- H2 file DB는 `.mv.db` 파일로 저장되지만, 연결할 때는 확장자를 뺀 경로를 쓴다.
- DBeaver는 H2 드라이버를 통해 같은 파일 DB를 열 수 있다.
- `AUTO_SERVER=TRUE` 덕분에 Spring Boot 앱이 실행 중이어도 외부 도구가 같은 DB 파일에 붙을 수 있다.
- 연결 후에는 `SPRING_AI_CHAT_MEMORY` 테이블에서 대화 기록을 조회하면 된다.

**Python 비교**:
```python
# sqlite 파일을 DB Browser로 여는 것과 비슷한 흐름이다.
# 다만 H2는 JDBC URL 형식으로 경로를 적는다.
```

**Java/Spring 연결**:
```yaml
spring:
  datasource:
    url: jdbc:h2:file:./data/chatmemory;AUTO_SERVER=TRUE
    username: sa
```

**흐름 타임라인**:
- 0ms: Spring Boot 앱이 실행 중인지 확인한다.
- 10ms: DBeaver에서 새 데이터베이스 연결을 만든다.
- 20ms: DB 종류를 H2로 선택한다.
- 30ms: JDBC URL에 H2 파일 경로를 입력한다.
- 40ms: 사용자 `sa`, 비밀번호 빈칸으로 설정한다.
- 50ms: Test Connection으로 접속을 확인한다.
- 60ms: 연결 후 `SPRING_AI_CHAT_MEMORY` 테이블을 연다.
- 70ms: SQL Editor에서 `SELECT * FROM SPRING_AI_CHAT_MEMORY;`를 실행해 기록을 본다.

**한 줄 메모**:
- 지금 앱은 실행 중이고 H2 파일도 생성되어 있어서, DBeaver에서는 URL만 정확히 넣으면 된다.
---
## [2026-07-06] Q: 이미 떠 있는 carelink-ai-h2 프로세스 종료하기

**질문**: 니꺼 꺼

**핵심 개념**:
- Port 8080 충돌은 같은 앱이 이미 떠 있을 때 자주 생긴다.
- `LISTEN` 상태가 사라지면 새 실행을 다시 시도할 수 있다.

**Python 비교**:
```python
# 이미 app.run() 중인 프로세스를 종료하고 다시 실행하는 상황과 비슷하다.
```

**Java/Spring 연결**:
```powershell
Stop-Process -Id 22940
```

**흐름 타임라인**:
- 0ms: 8080 점유 프로세스 PID 22940를 확인했다.
- 10ms: 해당 java 프로세스를 종료했다.
- 20ms: `LISTEN` 상태가 사라진 것을 확인했다.

**한 줄 메모**:
- 제가 띄운 carelink-ai-h2 프로세스는 종료했고, 이제 IntelliJ에서 다시 실행 시도하면 된다.
---
## [2026-07-06] Q: Whitelabel Error Page 404가 왜 뜨는지 확인하기

**질문**: Whitelabel Error Page ... There was an unexpected error (type=Not Found, status=404). 왜 안 뜰까

**핵심 개념**:
- Whitelabel 404는 서버가 죽은 게 아니라 요청한 주소에 매핑이 없다는 뜻이다.
- `/` 루트 주소와 `/api/guardian-chat`, `/h2-console/`는 서로 다르다.
- 현재 프로젝트는 API와 H2 콘솔은 있지만, 브라우저 첫 화면용 `/` 페이지는 없다.

**Python 비교**:
```python
@app.get('/api/guardian-chat')
def chat(...):
    ...

# 그런데 @app.get('/') 가 없으면
# localhost:8080/ 접속 시 404가 난다.
```

**Java/Spring 연결**:
```java
@GetMapping("/guardian-chat")
public GuardianChatResponse chat(@RequestParam String question,
                                 @RequestParam String conversationId) {
    return guardianChatService.chat(question, conversationId);
}
```

**흐름 타임라인**:
- 0ms: 앱이 8080 포트에서 정상 실행 중임을 확인했다.
- 10ms: `http://localhost:8080/` 접속 결과는 404였다.
- 20ms: `http://localhost:8080/h2-console/` 접속 결과는 200이었다.
- 30ms: `http://localhost:8080/api/guardian-chat?...` 호출 결과도 200이었다.
- 40ms: 따라서 문제는 서버 다운이 아니라 `/` 루트 매핑 부재로 확정되었다.

**한 줄 메모**:
- 지금 프로젝트는 API 프로젝트라서 `/` 첫 화면이 없고, 브라우저에서는 `/h2-console/` 또는 API 주소로 들어가야 한다.
---
## [2026-07-06] Q: 루트 홈 화면을 팔란티어 스타일로 추가하고 왜 8080에서 바로 안 보이는지 확인하기

**질문**: 응 팔란티어 스타일로 부탁해

**핵심 개념**:
- `static/index.html`을 추가하면 Spring Boot 루트 `/`에 웰컴 페이지를 붙일 수 있다.
- 이미 떠 있던 프로세스는 새 정적 리소스를 자동 반영하지 않을 수 있어서 재시작이 필요할 수 있다.
- `WelcomePageHandlerMapping` 로그가 보이면 `/` 루트 홈 화면이 연결된 것이다.

**Python 비교**:
```python
# Flask에서 static/index.html 또는 templates/index.html을 새로 추가한 뒤
# 서버를 재시작해야 새 화면이 보이는 상황과 비슷하다.
```

**Java/Spring 연결**:
```text
src/main/resources/static/index.html
```

**흐름 타임라인**:
- 0ms: 팔란티어 느낌의 대시보드형 `index.html`을 추가했다.
- 10ms: `./gradlew.bat classes`로 리소스를 빌드했다.
- 20ms: 기존 8080 프로세스에서는 `/`가 여전히 404였다.
- 30ms: 임시로 8081 포트에서 새 앱을 실행해 검증했다.
- 40ms: 로그에서 `Adding welcome page: class path resource [static/index.html]`를 확인했다.
- 50ms: `http://localhost:8081/`가 HTTP 200으로 응답했고, 새 홈 화면 콘텐츠도 확인됐다.
- 60ms: 임시 8081 검증 프로세스는 종료했다.

**한 줄 메모**:
- 새 팔란티어 스타일 홈 화면은 정상 구현됐고, 사용자가 보는 8080 앱에서는 재시작 후 반영된다.
---
## [2026-07-06] Q: carelink-ai-h2 프로젝트를 발표용으로 설명하기

**질문**: 잘햇네 우리가 만든 거 발표할 수 있게 설명해봐.

**핵심 개념**:
- 미니 프로젝트 주제: CareLink 보호자 상담 AI 콘솔
- 핵심 기술: Spring Boot, Spring AI, Advisor, H2 file DB, H2 Console, 정적 대시보드 UI
- 차별점: 같은 conversationId로 이전 대화를 기억하는 메모리형 상담 흐름
- 발표 포인트: 단순 챗봇이 아니라 대화 기억과 DB 확인까지 가능한 학습형 데모

**Python 비교**:
```python
# Flask + service 함수 + sqlite 대화기억 + 간단한 dashboard UI를
# Spring Boot와 Spring AI 스타일로 옮긴 프로젝트라고 설명할 수 있다.
```

**Java/Spring 연결**:
```java
String answer = chatClient.prompt()
        .system(SYSTEM_PROMPT)
        .user(question)
        .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, conversationId))
        .call()
        .content();
```

**흐름 타임라인**:
- 0ms: 사용자가 루트 대시보드에서 질문을 입력한다.
- 10ms: Controller가 question, conversationId를 받는다.
- 20ms: Service가 Advisor와 ChatMemory를 포함한 ChatClient로 AI 호출을 준비한다.
- 30ms: 이전 대화가 H2 DB에서 불러와진다.
- 40ms: AI가 보호자 상담 답변을 생성한다.
- 50ms: 결과가 H2 DB에 저장된다.
- 60ms: 화면에 응답이 보이고, H2 Console 또는 DBeaver에서 저장 결과를 확인할 수 있다.

**한 줄 메모**:
- 발표 때는 “AI 응답 생성”보다 “대화 기억을 DB에 남기고 직접 검증할 수 있다”를 핵심 가치로 강조하면 좋다.
---
## [2026-07-06] Q: DBeaver와 H2 콘솔에서 대화 기록 확인하는 방법

**질문**: d비버에서 어떻게확인하고 H2 콘솔은 어떻게 확인해?

**핵심 개념**:
- H2 콘솔은 브라우저에서 H2 DB를 보는 방법이다.
- DBeaver는 외부 DB 툴로 같은 H2 파일 DB를 확인하는 방법이다.
- 연결할 때는 `.mv.db` 전체 파일명이 아니라 확장자를 뺀 DB 경로를 사용한다.
- 대화 기록은 `SPRING_AI_CHAT_MEMORY` 테이블에 저장된다.

**Python 비교**:
```python
# sqlite 파일을 앱에서 쓰고
# DB Browser 같은 툴로 같은 파일을 열어보는 흐름과 비슷하다.
```

**Java/Spring 연결**:
```yaml
spring:
  datasource:
    url: jdbc:h2:file:./data/chatmemory;AUTO_SERVER=TRUE
```

**흐름 타임라인**:
- 0ms: 앱이 8080에서 실행 중인지 확인한다.
- 10ms: 브라우저에서 `/h2-console/`로 접속한다.
- 20ms: H2 콘솔에 JDBC URL, user, password를 입력해 로그인한다.
- 30ms: `SPRING_AI_CHAT_MEMORY` 테이블을 열거나 SQL을 실행한다.
- 40ms: DBeaver에서는 H2 새 연결을 만들고 같은 JDBC URL로 접속한다.
- 50ms: `SELECT * FROM SPRING_AI_CHAT_MEMORY;`로 대화 기록을 확인한다.

**한 줄 메모**:
- 지금 프로젝트에서는 H2 콘솔과 DBeaver 둘 다 같은 H2 파일 DB를 보는 도구이고, 핵심 확인 대상은 `SPRING_AI_CHAT_MEMORY` 테이블이다.
---
## [2026-07-06] Q: H2 콘솔에서 절대경로 URL이 계속 실패하는 이유

**질문**: 아니야 이렇게 넣었어. jdbc:h2:file:C:/Users/금정산2-PC02/p2-spring/spring-ai-study/carelink-ai-mariadb/data/chatmemory;AUTO_SERVER=TRUE
그런데 계속 이렇게 뜨네
org.h2.jdbc.JdbcSQLNonTransientException: IO Exception: ...

**핵심 개념**:
- 실행 중인 Spring Boot 앱은 이미 `jdbc:h2:file:./data/chatmemory;AUTO_SERVER=TRUE`로 DB를 열고 있다.
- H2 콘솔에서는 같은 앱 안에 붙는 것이므로 절대경로보다 상대경로 `jdbc:h2:file:./data/chatmemory`가 더 안전하다.
- `.mv.db`를 직접 쓰지 않는다는 원칙은 그대로 유지된다.
- DBeaver용 URL과 H2 콘솔용 URL은 같을 수도 있지만, 이번 환경에서는 H2 콘솔은 상대경로가 더 안정적이다.

**Python 비교**:
```python
# sqlite 파일을 앱 내부에서 열 때는 상대경로가 잘 되는데,
# 외부 도구에서 절대경로로 접근할 때 경로 파싱이 꼬이는 상황과 비슷하다.
```

**Java/Spring 연결**:
```yaml
spring:
  datasource:
    url: jdbc:h2:file:./data/chatmemory;AUTO_SERVER=TRUE
```

**흐름 타임라인**:
- 0ms: 앱은 이미 상대경로 기반 H2 file DB로 실행 중이다.
- 10ms: 사용자는 H2 콘솔에서 절대경로 URL을 넣었다.
- 20ms: H2 콘솔이 경로를 처리하는 과정에서 Windows 경로 구문 오류가 발생했다.
- 30ms: 해결 방향은 앱과 동일한 상대경로 URL로 접속하는 것이다.

**한 줄 메모**:
- H2 콘솔에서는 `jdbc:h2:file:./data/chatmemory`로 접속하고, DBeaver에서만 절대경로 + `AUTO_SERVER=TRUE`를 쓰는 쪽이 가장 안전하다.
---
## [2026-07-06] Q: 현재 수업 방식을 다른 AI에게 이식할 수 있는 프롬프트 만들기

**질문**: 우리가 하고 있는 수업방식을 다른 애한테 이식할 수 잇도록 프롬프트 제공해줘

**핵심 개념**:
- 메타 프롬프트: 원하는 설명 방식과 수업 규칙을 다른 AI에게 강하게 전달하는 문장이다.
- 튜터링 스타일 이식: 말투, 설명 순서, Python 비교, 코드 해설 방식, 기록 규칙을 함께 넘겨야 한다.
- 플레이스홀더: 현재 진도와 프로젝트 경로처럼 바뀌는 값은 빈칸으로 남겨두는 게 좋다.

**Python 비교**:
```python
# 함수에 옵션을 넘기듯이
# AI에게도 설명 옵션을 한 번에 넘기는 개념이다.

tutor_mode = {
    'tone': 'spoken_korean',
    'analogy_first': True,
    'python_compare': True,
    'line_by_line': True,
}
```

**Java/Spring 연결**:
```text
즉, 프롬프트는 다른 AI에게 주는 '설정 파일'처럼 생각하면 된다.
```

**흐름 타임라인**:
- 0ms: 현재 수업에서 중요했던 규칙을 뽑는다.
- 10ms: 말투, 설명 스타일, 코드 해설 방식, 기록 방식을 묶는다.
- 20ms: 다른 AI가 바로 복붙해서 쓸 수 있는 시스템 프롬프트 형태로 정리한다.

**한 줄 메모**:
- 좋은 튜터 프롬프트는 단순히 친절하게 설명하라는 수준이 아니라, 설명 순서와 출력 형식까지 같이 고정해야 한다.
---
## [2026-07-08] Q: Day5 Tool Calling / MCP 수업 전체 흐름 분석

**질문**: 이게 오늘 수업 내용이야. 분석해봐.

**핵심 개념**:
- Tool Calling: 모델은 직접 실행하지 않고, 애플리케이션에게 도구 실행을 요청한다.
- `@Tool` + `.tools(...)`: 로컬 자바 메서드를 모델이 쓸 수 있는 도구로 붙이는 가장 기본 패턴이다.
- MCP: 파일시스템, 웹 fetch 같은 외부 도구를 표준 프로토콜로 연결하는 방식이다.
- Tool + Memory 결합: 도구 호출 결과와 이전 대화 맥락을 함께 기억해서 다음 턴에 재사용할 수 있다.

**Python 비교**:
```python
def get_current_time():
    return datetime.now().isoformat()

tools = [get_current_time]
# 모델은 어떤 tool이 필요한지 요청만 하고,
# 실제 함수 실행은 애플리케이션이 한다.
```

**Java/Spring 연결**:
```java
return chatClient.prompt()
        .user(question)
        .tools(dateTimeTools, customerTools, companyRuleTools)
        .call()
        .content();
```

**흐름 타임라인**:
- 0ms: 사용자가 질문을 보낸다.
- 10ms: Controller가 질문을 Service로 전달한다.
- 20ms: Service가 `ChatClient`에 질문과 도구 목록을 붙인다.
- 30ms: 모델이 필요한 도구를 고른다.
- 40ms: Spring AI가 실제 자바 메서드 또는 MCP 서버 도구를 실행한다.
- 50ms: 도구 결과가 다시 모델에게 전달된다.
- 60ms: 모델이 최종 자연어 답변을 만든다.

**한 줄 메모**:
- Day5의 핵심은 “AI가 답만 하는 단계”에서 “우리 앱의 기능을 빌려 일하는 단계”로 넘어가는 것이다.
---
## [2026-07-08] Q: `spring.ai.mcp.client.stdio.connections.filesystem` 설정 해석

**질문**:     mcp:
      client:
        request-timeout: 30s
        stdio:
          connections:
            filesystem:

설명해봐

**핵심 개념**:
- `client`: 우리 앱이 MCP 서버를 소비하는 쪽이라는 뜻이다.
- `request-timeout: 30s`: MCP 서버 응답을 30초까지만 기다린다는 뜻이다.
- `stdio`: 외부 MCP 서버 프로세스와 표준입출력으로 통신하는 방식이다.
- `connections.filesystem`: 파일시스템용 MCP 서버 연결 하나를 등록하는 시작점이다.

**Python 비교**:
```python
proc = subprocess.Popen(
    ["some-mcp-server"],
    stdin=subprocess.PIPE,
    stdout=subprocess.PIPE,
    text=True
)
```

**Java/Spring 연결**:
```yaml
spring:
  ai:
    mcp:
      client:
        request-timeout: 30s
        stdio:
          connections:
            filesystem:
```

**흐름 타임라인**:
- 0ms: 앱이 시작된다.
- 10ms: Spring Boot가 `application.yaml`의 MCP 설정을 읽는다.
- 20ms: `filesystem` 연결 정보를 보고 MCP 서버 실행 준비를 한다.
- 30ms: stdio 방식으로 서버 프로세스를 띄운다.
- 40ms: 서버 도구 목록을 받아온다.
- 50ms: 이후 사용자 질문이 오면 이 도구 목록을 `.tools(...)`처럼 사용한다.

**한 줄 메모**:
- 이 블록은 “파일 도구 서버를 우리 앱이 손님처럼 붙어서 쓸 준비”를 적는 설정이다.
---
## [2026-07-08] Q: filesystem MCP 서버를 자세히 이해하기

**질문**: filesystem에 관해 자세히 설명

**핵심 개념**:
- filesystem MCP 서버: 로컬 파일 읽기/쓰기/목록 조회 같은 기능을 도구 형태로 제공하는 서버다.
- 샌드박스 경로: 보통 `${user.dir}/mcp-sandbox`처럼 제한된 폴더만 열어둬서 안전하게 실습한다.
- 범용 도구: 고객 등급 조회처럼 우리 도메인 전용 기능은 `@Tool`, 파일 다루기처럼 공통 기능은 MCP가 적합하다.
- 모델은 직접 파일을 읽지 않는다: 어떤 파일을 읽을지 요청하고, 실제 읽기는 MCP 서버가 한다.

**Python 비교**:
```python
from pathlib import Path

root = Path("./mcp-sandbox")
files = list(root.iterdir())
text = (root / "memo.txt").read_text(encoding="utf-8")
```

**Java/Spring 연결**:
```yaml
filesystem:
  command: cmd.exe
  args:
    - "/c"
    - "npx"
    - "-y"
    - "@modelcontextprotocol/server-filesystem"
    - "${user.dir}/mcp-sandbox"
```

**흐름 타임라인**:
- 0ms: 앱이 filesystem MCP 서버를 실행한다.
- 10ms: 서버는 `mcp-sandbox` 경로를 기준으로 접근 가능한 파일 범위를 정한다.
- 20ms: 사용자가 “파일 읽어줘” 같은 질문을 보낸다.
- 30ms: 모델이 파일 읽기 도구가 필요하다고 판단한다.
- 40ms: Spring AI가 filesystem MCP 서버의 `read_text_file` 같은 도구를 호출한다.
- 50ms: 읽어온 결과가 모델에게 전달된다.
- 60ms: 모델이 내용을 요약하거나 설명해 최종 응답을 만든다.

**한 줄 메모**:
- filesystem은 “AI가 컴퓨터 전체를 보는 기능”이 아니라, 정해진 폴더 안에서만 파일 작업을 대신해주는 안전한 도구 서버다.
---
## [2026-07-08] Q: `McpToolCatalog.java` 작성 중 문법과 의존성 문제

**질문**: package com.study.day05toolmcp.mcp;

import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Component;
import org.springframework.


@Component
public class McpToolCatalog {
    
    private final ToolCallback[];
    
    public McpToolCatalog(List<McpSyncClient>) {
        this.filesystemTools = SyncMcpToolCallbackProvider.builder().mcpClients(mcpClients).build().getToolCallbacks();
    }
}
뭐가 문제지?

**핵심 개념**:
- 문법 오류: import 문이 끊겼고, 배열 필드 이름과 생성자 파라미터 이름이 빠져 있었다.
- 타입 의존성: `McpSyncClient`, `SyncMcpToolCallbackProvider`를 쓰려면 MCP client starter 의존성이 필요하다.
- 역할: `McpToolCatalog`는 MCP 서버에서 받아온 ToolCallback 배열을 묶어서 서비스에서 꺼내 쓰기 쉽게 만드는 컴포넌트다.
- Windows MCP 실행 주의: `application.yaml`에서 `cmd.exe /c npx` 형태가 더 안정적이었다.

**Python 비교**:
```python
class McpToolCatalog:
    def __init__(self, mcp_clients):
        self.filesystem_tools = build_callbacks(mcp_clients)
```

**Java/Spring 연결**:
```java
@Component
public class McpToolCatalog {

    private final ToolCallback[] filesystemTools;

    public McpToolCatalog(List<McpSyncClient> mcpClients) {
        this.filesystemTools = SyncMcpToolCallbackProvider.builder()
                .mcpClients(mcpClients)
                .build()
                .getToolCallbacks();
    }

    public ToolCallback[] filesystemTools() {
        return filesystemTools;
    }
}
```

**흐름 타임라인**:
- 0ms: 앱 부팅 중 Spring이 `McpToolCatalog` 빈 생성을 시도한다.
- 10ms: 생성자에서 `List<McpSyncClient>`가 주입된다.
- 20ms: `SyncMcpToolCallbackProvider`가 MCP 클라이언트 목록을 기반으로 ToolCallback 배열을 만든다.
- 30ms: `filesystemTools` 필드에 저장된다.
- 40ms: 이후 Service가 `.tools((Object[]) catalog.filesystemTools())`처럼 사용한다.

**한 줄 메모**:
- `McpToolCatalog`는 “MCP 서버 도구들을 Service에서 쉽게 꺼내 쓰기 위한 정리함”이라고 이해하면 된다.
---
## [2026-07-08] Q: Day1부터 Day5까지 전체 흐름을 Day4 포함해서 한 번에 정리

**질문**: 그리고 나 어제 수업 못 들어서 Day4 안 했어. 내가 Day4자료 줄테니까 Day1부터 Day5까지 오늘 모두 정리해보자.

**핵심 개념**:
- Day1: `ChatClient`로 가장 기본적인 AI 호출 흐름을 익힌다.
- Day2: Prompt와 Structured Output으로 “질문을 어떻게 잘 시키고, 답을 어떻게 구조로 받는지”를 배운다.
- Day3: Advisor와 Chat Memory로 여러 턴 대화와 공통 규칙을 붙인다.
- Day4: 멀티모달 입력으로 텍스트뿐 아니라 이미지·PDF·오디오를 함께 보내고 `.entity()`로 구조화해 받는다.
- Day5: Tool Calling과 MCP로 모델이 앱의 함수나 외부 도구를 “요청”하게 만든다.

**Python 비교**:
```python
# Day1
answer = llm.ask("안녕?")

# Day2
prompt = f"{text}를 3줄로 요약해줘"
result = to_dict(llm.ask(prompt))

# Day3
memory.append(user_message)
memory.append(ai_message)

# Day4
llm.ask(text="영수증 분석해줘", media=image_bytes)

# Day5
tool_result = get_customer_grade("C001")
llm.ask_with_tools("고객 등급 알려줘", tools=[get_customer_grade])
```

**Java/Spring 연결**:
```java
// Day1
chatClient.prompt().user(question).call().content();

// Day2
chatClient.prompt().user(u -> u.text(template).param("text", text)).call().entity(Result.class);

// Day3
builder.defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build());

// Day4
new Media(MimeTypeUtils.IMAGE_JPEG, new ByteArrayResource(file.getBytes()));

// Day5
chatClient.prompt().user(question).tools(dateTimeTools, customerTools).call().content();
```

**흐름 타임라인**:
- 0ms: Day1에서 사용자가 텍스트 질문을 보낸다.
- 10ms: Day2에서 Prompt가 더 구체적인 지시와 변수 치환을 담당한다.
- 20ms: Day3에서 Advisor와 Chat Memory가 대화 공통 규칙과 이전 맥락을 붙인다.
- 30ms: Day4에서 텍스트 외에 이미지·PDF·오디오 같은 Media가 함께 전달된다.
- 40ms: Day5에서 모델이 필요한 도구 호출을 요청하고, 앱이나 MCP 서버가 실제 실행한다.
- 50ms: 각 결과가 다시 모델에 전달되고 최종 응답이 완성된다.

**한 줄 메모**:
- Day1~Day5 전체를 한 문장으로 묶으면 “질문 보내기 → 더 잘 시키기 → 기억 붙이기 → 멀티모달로 넓히기 → 도구와 외부 시스템까지 연결하기”이다.
---
## [2026-07-08] Q: LMS 자료에서 Spring AI 관련 내용만 뽑아 교육자료 만들기

**질문**: https://lms.expandsource.com/materials 여기들어가면 이때까지 배운 내용들이 나오는데 Spring AI 관련 내용만 뽑아와서 교육 자료 만들어줘.

**핵심 개념**:
- LMS 기준 Spring AI 학습 흐름은 Day1 ChatClient → Day2 Prompt/Structured Output → Day3 Advisor/Chat Memory → Day4 Multimodal → Day5 Tool Calling/MCP 순서다.
- Day5 학습자 가이드에서도 Day1~4 기능이 이미 starter 위에 누적되어 있고, 그 위에 Tool/MCP를 쌓는 구조라고 설명한다.
- 교육자료로 만들 때는 “기능 확장 순서”와 “왜 이 개념이 다음 날에 필요한지”를 연결해서 보여주는 게 가장 중요하다.
- Day5 기준 제출물은 로컬 도구, tool+memory, MCP filesystem/fetch/mixed-chat까지 캡처와 README를 포함하는 형태다.

**Python 비교**:
```python
day1 = "llm.ask()"
day2 = "prompt + structured result"
day3 = "memory + advisor"
day4 = "text + image/pdf/audio"
day5 = "tools + external tool server"
```

**Java/Spring 연결**:
```java
// Day5 학습 가이드 핵심 흐름
chatClient.prompt()
        .user(question)
        .tools(dateTimeTools, customerTools, companyRuleTools)
        .call()
        .content();
```

**흐름 타임라인**:
- 0ms: LMS 수업 자료 목록에서 Day5와 Day4 가이드를 확인한다.
- 10ms: Day5 가이드에서 Day1~4가 starter에 이미 들어있다는 구조를 확인한다.
- 20ms: Day1~Day5를 기능 확장 순서로 재구성한다.
- 30ms: 각 Day별 핵심 개념과 대표 엔드포인트를 뽑는다.
- 40ms: 학습자용 교육자료 형태로 다시 묶는다.

**한 줄 메모**:
- LMS 자료 기준으로도 Spring AI 수업의 본질은 “하루마다 새 기술을 배우는 것”이 아니라 “같은 앱 위에 능력을 한 층씩 쌓는 것”이다.
---
## [2026-07-08] Q: Day1~Day5 Spring AI 교육자료를 더 자세하게 정리

**질문**: 교육자료를 자세히 줘야지?

**핵심 개념**:
- 상세 교육자료는 단순 요약이 아니라 `왜 배우는지`, `핵심 코드`, `실습 체크포인트`, `흔한 실수`, `다음 날 연결`까지 포함해야 한다.
- LMS에서 확인된 Spring AI 수업 축은 Day1 ChatClient, Day3 Advisor/Chat Memory, Day4 Multimodal, Day5 Tool Calling/MCP이며, Day2는 Prompt/Structured Output으로 그 사이를 연결한다.
- 전체 학습 흐름은 “질문 → 잘 시키기 → 기억 → 멀티모달 → 도구 실행”이다.

**Python 비교**:
```python
course_flow = [
    "ask llm",
    "prompt better + parse result",
    "add conversation memory",
    "send media with text",
    "call tools and external servers",
]
```

**Java/Spring 연결**:
```java
chatClient.prompt()
        .user(question)
        .call()
        .content();
```

**흐름 타임라인**:
- 0ms: LMS 자료 제목과 현재까지의 Day1~Day5 구조를 다시 확인한다.
- 10ms: 각 Day의 목적과 연결 관계를 정리한다.
- 20ms: 하루별 핵심 개념, 코드 포인트, 실습 체크포인트를 묶는다.
- 30ms: 학습자용 상세 교육자료 형태로 재구성한다.

**한 줄 메모**:
- 학습자는 기술 이름보다 “왜 오늘 이걸 배우고, 내일 무엇과 연결되는지”가 보여야 전체가 이해된다.
---
## [2026-07-08] Q: Day5 Tool Calling / MCP 과제 주제와 진행 방식 정하기

**질문**: 오늘 과제는 어떻게 할까?

**핵심 개념**:
- Day5 과제는 “멋진 주제”보다 “Tool 사다리 5단계 + MCP Client 4단계 + README/캡처 제출”을 얼마나 자연스럽게 담는지가 더 중요하다.
- 주제 선택 기준은 도구 3종(시간, 도메인 조회, 규칙/FAQ), tool+memory, filesystem/fetch, mixed-chat을 모두 설명하기 쉬운가이다.
- 예시 주제 중에서는 `AI 장기요양 헬퍼`가 이전 CareLink 맥락과도 맞고, Day5 요구사항을 과하지 않게 담기 좋다.

**Python 비교**:
```python
assignment_checklist = [
    "local tool 3개",
    "tool + memory",
    "filesystem MCP",
    "fetch MCP",
    "mixed chat",
    "README + screenshots",
]
```

**Java/Spring 연결**:
```java
chatClient.prompt()
        .user(question)
        .tools(localTools..., mcpTools...)
        .call()
        .content();
```

**흐름 타임라인**:
- 0ms: 과제 주제를 정한다.
- 10ms: Day5 제출 요구사항을 체크리스트로 바꾼다.
- 20ms: 주제가 로컬 Tool, Memory, MCP filesystem/fetch를 모두 담을 수 있는지 본다.
- 30ms: MVP 엔드포인트와 README 캡처 목록을 정한다.

**한 줄 메모**:
- Day5 과제는 “도구 호출 구조를 얼마나 잘 보여주느냐”가 핵심이라서, 주제는 복잡하기보다 설명이 잘 되는 쪽이 유리하다.
---
## [2026-07-08] Q: Day5 과제를 잘 개발하면 실제로 쓸 수 있는지

**질문**: 그래 가보자. 이거 잘 개발하면 쓸 수 있을려나?

**핵심 개념**:
- Day5 과제 수준의 앱도 범위를 잘 좁히면 실제 내부 업무 보조 도구로는 충분히 쓸 수 있다.
- 다만 장기요양처럼 민감한 도메인에서는 `자동 의사결정자`가 아니라 `사람을 돕는 보조자`로 두는 것이 안전하다.
- 실사용으로 가려면 인증, 권한, DB 연동, 로그, 근거 표시, 예외 처리, 비용 관리가 추가로 필요하다.
- 가장 먼저 실전성이 생기는 영역은 FAQ 답변, 보호자 상담 초안, 어르신 상태 요약, 규정/문서 조회 같은 보조 업무다.

**Python 비교**:
```python
# 바로 자동 승인 시스템으로 쓰기보다
# 사람이 검토하는 보조 도구로 먼저 쓰는 게 안전하다.

draft = ai_assistant.make_reply(question, patient_info, manual_docs)
human_review(draft)
```

**Java/Spring 연결**:
```java
chatClient.prompt()
        .user(question)
        .tools(localTools..., mcpTools...)
        .call()
        .content();
```

**흐름 타임라인**:
- 0ms: 사용자가 보호자 문의를 입력한다.
- 10ms: 앱이 어르신 상태, 돌봄 규칙, 문서 정보를 도구로 조회한다.
- 20ms: AI가 답변 초안을 만든다.
- 30ms: 사람이 초안을 검토하고 수정한다.
- 40ms: 검토된 답변을 실제 상담이나 기록에 활용한다.

**한 줄 메모**:
- 이 과제는 “현장 자동화 완성품”보다는 “실무 보조용 AI 어시스턴트의 매우 좋은 시작점”으로 보는 게 가장 정확하다.
---
## [2026-07-08] Q: AI 장기요양 헬퍼 Day5 과제 구현 시작

**질문**: 그래.

**핵심 개념**:
- 기존 Day5 Tool Calling 프로젝트를 `AI 장기요양 헬퍼` 주제로 바꿔 과제 제출 구조에 맞췄다.
- 로컬 Tool은 수급자 상태 조회, 돌봄 규칙 조회, 현재 시간 조회로 구성했다.
- MCP는 filesystem과 fetch를 모두 연결하고, local tool과 MCP tool을 함께 쓰는 mixed-chat까지 구성했다.
- 테스트에서는 MCP 서버 자동 연결을 꺼서 빌드가 외부 프로세스 상태에 흔들리지 않게 했다.

**Python 비교**:
```python
local_tools = [get_recipient_profile, get_care_rule, get_current_time]
mcp_tools = [read_local_file, fetch_url]
assistant.ask(question, tools=local_tools + mcp_tools)
```

**Java/Spring 연결**:
```java
return chatClient.prompt()
        .user(question)
        .tools(dateTimeTools, customerTools, companyRuleTools)
        .call()
        .content();
```

**흐름 타임라인**:
- 0ms: 사용자가 과제 주제를 `AI 장기요양 헬퍼`로 진행하자고 확정했다.
- 10ms: 기존 고객/사내 규칙 도구를 수급자/돌봄 규칙 도구 내용으로 바꿨다.
- 20ms: `/api/assistant`, `/api/tool-chat`, `/api/mcp/fetch`, `/api/mcp-chat`, `/api/mixed-chat`를 추가했다.
- 30ms: `mcp-sandbox`에 장기요양 문서 3개를 추가했다.
- 40ms: README와 제출 체크리스트를 추가했다.
- 50ms: `compileJava`, `build`, 8081 실제 부팅 검증을 통과했다.

**한 줄 메모**:
- 오늘 과제 MVP는 “장기요양 상담 도메인”을 빌려 Tool Calling과 MCP 구조를 선명하게 보여주는 방향으로 구현했다.
---
## [2026-07-08] Q: 백지에서 AI 장기요양 헬퍼를 어떤 순서로 만들지 이해하기

**질문**: 자 이건 너가 만든 거지. 내가 만든 거 아니잖아. 난 너가 한 방식이랑 똑같이 하고 싶어. 그런데 순서를 모르겠어. 내가 파악하기로는 넌 Controller, Tool, Service,application, MCP, service, Sandbox를 만들고 검증했는데 만약 우리가 작성한 코드가 하나도 없고 백지에서 시작한다고 하면 너는 지금의 프로그램을 어떻게 만들건지 나한테 코드 한 줄 별로 말해줄 수 있어?

**핵심 개념**:
- 백지에서 만들 때는 Controller부터 치는 게 아니라, 먼저 “AI가 어떤 능력을 가져야 하는지”를 Tool 단위로 나눈다.
- 구현 순서는 `프로젝트 설정 → 기본 ChatService → 로컬 Tool → ToolChatService → Memory Service → Controller → MCP 설정 → MCP Service → sandbox 문서 → 검증`이 자연스럽다.
- Controller는 입구이고, Service는 흐름이며, Tool은 AI가 요청할 수 있는 실제 능력이다.

**Python 비교**:
```python
tools = [get_current_time, get_recipient_profile, get_care_rule]
service = AiService(llm, tools)
route("/api/tool-chat", service.chat)
```

**Java/Spring 연결**:
```java
chatClient.prompt()
        .user(question)
        .tools(dateTimeTools, customerTools, companyRuleTools)
        .call()
        .content();
```

**흐름 타임라인**:
- 0ms: 어떤 AI 앱을 만들지 정한다.
- 10ms: AI가 쓸 로컬 도구를 정한다.
- 20ms: 각 도구를 `@Tool` 메서드로 만든다.
- 30ms: Service에서 `.tools(...)`로 도구를 붙인다.
- 40ms: Controller에서 URL을 열어준다.
- 50ms: MCP 설정과 문서 폴더를 붙인다.
- 60ms: Gradle build와 실제 부팅으로 검증한다.

**한 줄 메모**:
- 같은 프로그램을 직접 만들려면 “Controller부터”가 아니라 “AI에게 줄 능력부터” 생각하는 게 가장 안정적이다.
---
## [2026-07-08] Q: Day5 프로젝트 전체 코드를 파일별 주석 버전으로 보고 Spring Boot 구조 이해하기

**질문**: 너가 만든 우리의 프로젝트를 파일별로 전체 주석을 달아서 제공해줘. 그리고 나 아직 Springboot 구조가 이해가 안 가. 그 점 유의해서 전체코드 with 주석 by 파일 제공해줘.

**핵심 개념**:
- Spring Boot 구조: `Application`은 시작점, `Controller`는 요청 입구, `Service`는 실제 처리, `Tool`은 AI가 호출할 수 있는 기능, `Config`는 설정입니다.
- Tool Calling 구조: AI가 직접 함수를 실행하는 것이 아니라, AI가 도구 호출을 요청하고 Spring 애플리케이션이 실제 메서드를 실행합니다.
- MCP 구조: 외부 MCP 서버가 제공하는 도구를 `ToolCallback` 형태로 바꿔 `ChatClient`에 붙입니다.

**Python 비교**:
```python
@app.get("/api/assistant")
def assistant(question: str, conversation_id: str = "care-demo"):
    return helpdesk_service.chat(question, conversation_id)
```

**Java/Spring 연결**:
```java
@GetMapping("/api/assistant")
public String assistant(@RequestParam String question,
                        @RequestParam(defaultValue = "care-demo") String conversationId) {
    return helpdeskService.chat(question, conversationId);
}
```

**흐름 타임라인**:
- 0ms: 사용자가 `/api/assistant`로 질문을 보냅니다.
- 10ms: `AiController`가 요청을 받습니다.
- 20ms: `HelpdeskService.chat()`으로 질문을 넘깁니다.
- 30ms: `ChatMemoryAdvisor`가 같은 `conversationId`의 이전 대화를 붙입니다.
- 40ms: `dateTimeTools`, `customerTools`, `companyRuleTools`를 AI에게 사용 가능하도록 붙입니다.
- 100ms 이후: AI가 필요하면 Tool 호출을 요청하고, Spring 앱이 실제 Tool 메서드를 실행한 뒤 결과를 AI에게 돌려줍니다.

**한 줄 메모**:
- 원본 소스에 과한 주석을 직접 넣지 않고, 학습용 문서 `day05-tool-mcp/DAY5_ANNOTATED_CODE.md`에 파일별 주석 코드를 정리했습니다.
---
## [2026-07-08] Q: Day5 프로젝트에 팔란티어풍 프론트엔드 만들기

**질문**: 우리 프론트 앤드 만들자. 팔란티어 스타일로 만들어. 막 움직이고 뛰다니고 그런 거 있자나

**핵심 개념**:
- Spring Boot 정적 리소스: `src/main/resources/static/index.html`을 만들면 서버 실행 후 `http://localhost:8080/`에서 바로 열립니다.
- 프론트와 백엔드 연결: JavaScript `fetch()`로 `/api/assistant`, `/api/tool-chat`, `/api/mcp-chat`, `/api/mixed-chat`을 호출합니다.
- UI 구성: HTML은 구조, CSS는 팔란티어풍 작전 콘솔 디자인과 애니메이션, JS는 버튼 클릭과 API 요청을 담당합니다.

**Python 비교**:
```python
# Flask에서 static/index.html을 서빙하고,
# 브라우저 JS가 /api/assistant로 요청을 보내는 구조와 비슷합니다.
@app.get("/api/assistant")
def assistant(question: str):
    return service.chat(question)
```

**Java/Spring 연결**:
```java
// src/main/resources/static/index.html
// 이 위치에 있으면 Spring Boot가 "/" 주소로 자동 서빙합니다.
```

**흐름 타임라인**:
- 0ms: 사용자가 `http://localhost:8080/`로 접속합니다.
- 10ms: Spring Boot가 `index.html`, `styles.css`, `app.js`를 내려줍니다.
- 50ms: 사용자가 화면에서 질문을 입력하고 버튼을 누릅니다.
- 60ms: `app.js`가 `fetch("/api/assistant?...")`를 실행합니다.
- 100ms 이후: 기존 Controller, Service, ChatClient, Tool/MCP 흐름이 실행되고 응답이 화면에 표시됩니다.

**한 줄 메모**:
- 프론트 파일 3개(`index.html`, `styles.css`, `app.js`)와 README 프론트 설명을 추가했고, `gradlew build`와 `node --check`를 통과했습니다.
---
## [2026-07-08] Q: `@Service`, `private final`, 생성자 주입, `ChatClient.Builder` 구조의 이유

**질문**: `@Service public class ChatService`, `private final ChatClient chatClient`, `public ChatService(ChatClient.Builder builder) { this.chatClient = builder.build(); }` 난 이렇게 구조를 잡는 문법의 이유를 모르겠어

**핵심 개념**:
- `@Service`: Spring에게 이 클래스를 직접 new 하지 말고, 앱 시작 시 객체로 만들어 관리하라고 알려줍니다.
- `private final`: 이 서비스가 사용할 중요한 부품을 외부에서 함부로 바꾸지 못하게 고정합니다.
- 생성자 주입: 필요한 부품을 Spring이 생성자 안으로 자동으로 넣어주는 방식입니다.
- `Builder`: 복잡한 객체인 `ChatClient`를 설정을 포함해 안전하게 만들기 위한 조립 도구입니다.

**Python 비교**:
```python
class ChatService:
    def __init__(self, builder):
        self.chat_client = builder.build()
```

**Java/Spring 연결**:
```java
@Service
public class ChatService {
    private final ChatClient chatClient;

    public ChatService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }
}
```

**흐름 타임라인**:
- 0ms: Spring Boot가 실행됩니다.
- 100ms: Spring이 `@Service`가 붙은 `ChatService`를 발견합니다.
- 120ms: `ChatService`를 만들기 위해 생성자를 봅니다.
- 130ms: 생성자에 필요한 `ChatClient.Builder`를 Spring이 찾아 넣어줍니다.
- 140ms: `builder.build()`로 실제 `ChatClient`가 만들어집니다.
- 150ms: 만들어진 `ChatClient`가 `this.chatClient`에 저장됩니다.

**한 줄 메모**:
- 이 구조는 "내가 직접 객체를 조립하는 방식"이 아니라 "Spring이 앱 시작 때 필요한 객체들을 조립해두고, 우리는 가져다 쓰는 방식"입니다.
---
## [2026-07-08] Q: `private final`을 왜 사용하는지 이해하기

**질문**: private은 “이 클래스 안에서만 쓰게 하자”는 뜻이고, final은 “처음 넣은 뒤 바꾸지 말자”는 뜻 왜 이렇게 해?

**핵심 개념**:
- `private`: 클래스 내부 부품을 밖에서 마음대로 바꾸지 못하게 막아 객체의 책임을 보호합니다.
- `final`: 생성자에서 받은 필수 의존성을 실행 중에 다른 객체로 바꾸지 못하게 고정합니다.
- Spring Service는 보통 여러 요청이 함께 쓰는 싱글톤이므로, 내부 부품이 중간에 바뀌면 예측하기 어려운 버그가 생깁니다.

**Python 비교**:
```python
class ChatService:
    def __init__(self, chat_client):
        self._chat_client = chat_client  # 관례상 내부 전용
```

**Java/Spring 연결**:
```java
@Service
public class ChatService {
    private final ChatClient chatClient;
}
```

**흐름 타임라인**:
- 0ms: Spring이 `ChatService` 객체를 한 번 만듭니다.
- 10ms: 생성자에서 `ChatClient`를 넣습니다.
- 20ms: `final` 때문에 이후 다른 `ChatClient`로 교체할 수 없습니다.
- 100ms 이후: 여러 사용자의 요청이 들어와도 같은 안정된 `chatClient`를 사용합니다.

**한 줄 메모**:
- `private final`은 “이 서비스의 핵심 부품은 생성 시 정하고, 밖에서 못 건드리게 하자”는 안정성 문법입니다.
---
## [2026-07-08] Q: Day5 팔란티어풍 프론트엔드 사용법

**질문**: 너가 만든 프론트 사용법 좀 알려줘

**핵심 개념**:
- Spring Boot 정적 페이지: `http://localhost:8080/`로 접속하면 `src/main/resources/static/index.html`이 열립니다.
- API 모드 선택: 화면에서 `/api/assistant`, `/api/tool-chat`, `/api/mcp-chat`, `/api/mixed-chat` 중 어떤 백엔드 흐름을 쓸지 고릅니다.
- Chat Memory: `/api/assistant`는 같은 `conversationId`를 유지하면 이전 질문을 기억한 후속 질문이 가능합니다.

**Python 비교**:
```python
# 화면에서 fetch("/api/assistant?question=...")를 호출하는 구조입니다.
response = requests.get("/api/assistant", params={"question": question})
```

**Java/Spring 연결**:
```java
@GetMapping("/api/assistant")
public String assistant(@RequestParam String question,
                        @RequestParam(defaultValue = "care-demo") String conversationId) {
    return helpdeskService.chat(question, conversationId);
}
```

**흐름 타임라인**:
- 0ms: 브라우저에서 `http://localhost:8080/` 접속
- 10ms: `index.html`, `styles.css`, `app.js` 로드
- 50ms: 사용자가 API 모드와 질문을 선택
- 60ms: `Ctrl + Enter` 또는 상단 실행 버튼으로 `fetch()` 요청
- 100ms 이후: Spring Controller, Service, ChatClient, Tool/MCP가 실행되고 응답이 화면에 표시됨

**한 줄 메모**:
- 사용자 정의 질문은 질문창에서 `Ctrl + Enter`로 보내고, 상단 버튼은 미리 준비된 데모 질문을 바로 실행합니다.
---
## [2026-07-08] Q: 프론트 모드별 구현 흐름 이해하기

**질문**: 그런데 모드가 많잖아. 각각의 모드가 어떻게 구현된 건지 설명해줄래?

**핵심 개념**:
- 프론트 모드: 화면의 모드 카드는 실제 기능 자체가 아니라 호출할 `endpoint` 값을 바꾸는 UI입니다.
- Controller 라우팅: 선택된 endpoint는 `AiController`의 `@GetMapping` 메서드로 들어갑니다.
- Service 분리: `/api/tool-chat`은 `ToolChatService`, `/api/assistant`는 `HelpdeskService`, `/api/mcp-chat`과 `/api/mixed-chat`은 `McpChatService`가 처리합니다.

**Python 비교**:
```python
if endpoint == "/api/assistant":
    return helpdesk_service.chat(question, conversation_id)
elif endpoint == "/api/tool-chat":
    return tool_chat_service.chat(question)
```

**Java/Spring 연결**:
```java
@GetMapping("/api/assistant")
public String assistant(@RequestParam String question,
                        @RequestParam(defaultValue = "care-demo") String conversationId) {
    return helpdeskService.chat(question, conversationId);
}
```

**흐름 타임라인**:
- 0ms: 사용자가 프론트에서 모드 카드를 클릭합니다.
- 10ms: `app.js`가 `endpointSelect.value`를 해당 API 주소로 바꿉니다.
- 20ms: 사용자가 `Ctrl + Enter` 또는 실행 버튼을 누릅니다.
- 30ms: `fetch(endpoint + "?question=...")` 요청이 나갑니다.
- 40ms: `AiController`의 해당 `@GetMapping` 메서드가 실행됩니다.
- 50ms 이후: 각 모드에 맞는 Service가 ChatClient, Tool, Memory, MCP를 붙여 AI를 호출합니다.

**한 줄 메모**:
- 모드가 많아 보여도 핵심은 “endpoint가 다르고, 그 endpoint마다 붙는 도구 조합이 다르다”입니다.
---
## [2026-07-08] Q: Day5 프론트 시연 시나리오 구성

**질문**: 그러면 시연 시나리오를 짜줘.

**핵심 개념**:
- 시연 순서: 기본 화면 소개 → 로컬 Tool Calling → Chat Memory → MCP 문서 도구 → Mixed 최종 모드 순서가 가장 이해하기 쉽습니다.
- 발표 포인트: AI가 그냥 답하는 것이 아니라, 필요할 때 Java Tool과 MCP Tool을 호출해 답변을 강화한다는 점을 보여줍니다.
- 안전 메시지: 장기요양 상담 보조 도구이며 의료적·법적 최종 판단은 담당자 확인이 필요하다는 점을 함께 말합니다.

**Python 비교**:
```python
# 발표 흐름으로 치면 endpoint를 하나씩 바꿔가며 requests.get()을 실행하는 것과 같습니다.
requests.get("/api/tool-chat", params={"question": "R001 상태 알려줘"})
requests.get("/api/assistant", params={"conversationId": "demo-01", "question": "방금 수급자 기준으로 정리해줘"})
```

**Java/Spring 연결**:
```java
@GetMapping("/api/mixed-chat")
public String mixedChat(@RequestParam String question) {
    return mcpChatService.chatMixed(question);
}
```

**흐름 타임라인**:
- 0초: 프론트 화면과 프로젝트 목표 소개
- 30초: `/api/tool-chat`으로 로컬 Tool Calling 시연
- 90초: `/api/assistant`로 같은 `conversationId`에서 후속 질문 기억 시연
- 150초: `/api/mcp-chat`으로 `mcp-sandbox` 문서 조회 시연
- 210초: `/api/mixed-chat`으로 로컬 Tool과 MCP Tool 결합 시연

**한 줄 메모**:
- 시연의 핵심 문장은 “이 앱은 AI가 답만 생성하는 게 아니라, 필요한 도구를 선택하고 실행 결과를 바탕으로 상담 답변을 만듭니다.”입니다.
---
## [2026-07-08] Q: Day5 발표 내용을 한눈에 볼 수 있는 이미지 자료 만들기

**질문**: 이거 발표때 한눈에 볼 수 있게 이미지로 만들어줘. 양이 많으니까 이미지 여러개 만들어도 돼

**핵심 개념**:
- 발표용 시각화: 긴 설명을 한 장에 모두 넣기보다 표지, 아키텍처, 시연 순서, 모드 비교로 나누면 이해가 쉽습니다.
- SVG 인포그래픽: 글자가 깨지지 않고, PPT에 넣어도 선명하게 확대할 수 있습니다.
- PNG 변환: 발표 자료에 바로 넣기 편하도록 SVG를 PNG로도 변환했습니다.

**Python 비교**:
```python
# 개념적으로는 긴 설명 데이터를 여러 장의 차트로 나누는 작업입니다.
slides = ["cover", "architecture", "demo_flow", "mode_matrix"]
```

**Java/Spring 연결**:
```java
// 발표 이미지의 핵심 흐름
// Frontend -> AiController -> Service -> ChatClient -> Tool/MCP -> Gemini
```

**흐름 타임라인**:
- 0ms: 시연 내용을 4개 이미지 주제로 나눕니다.
- 10ms: 표지용 팔란티어풍 배경 이미지를 생성합니다.
- 30ms: 아키텍처 SVG를 만듭니다.
- 50ms: 3분 시연 시나리오 SVG를 만듭니다.
- 70ms: 모드별 구현 비교 SVG를 만듭니다.
- 100ms: SVG를 PNG로 변환해 PPT 삽입용 파일을 준비합니다.

**한 줄 메모**:
- 발표 이미지는 `day05-tool-mcp/presentation` 폴더에 `00-cover.png`, `01-architecture`, `02-demo-scenario`, `03-mode-matrix` 형태로 저장했습니다.
---
## [2026-07-08] Q: 외부 MCP 서버가 우리가 만든 MCP 파일을 의미하는지 이해하기

**질문**: 외부 MCP 서버는 우리가 만든 MCP 파일을 의미하는 거지?

**핵심 개념**:
- 외부 MCP 서버: 우리 Java 코드 안에 있는 파일이 아니라, `npx`나 `uvx`로 따로 실행되는 외부 도구 프로그램입니다.
- 우리 MCP Java 파일: 외부 MCP 서버를 직접 만드는 코드가 아니라, 외부 서버가 제공하는 도구를 Spring AI `ChatClient`에 연결하는 클라이언트 코드입니다.
- `mcp-sandbox`: MCP filesystem 서버가 읽을 수 있도록 허용한 문서 폴더이며, 서버 자체는 아닙니다.

**Python 비교**:
```python
# 우리 앱은 클라이언트 역할
mcp_tools = connect_to_external_mcp_server("filesystem")
ai.attach_tools(mcp_tools)
```

**Java/Spring 연결**:
```yaml
filesystem:
  command: npx.cmd
  args:
    - "-y"
    - "@modelcontextprotocol/server-filesystem"
    - "${user.dir}/mcp-sandbox"
```

**흐름 타임라인**:
- 0ms: Spring Boot 앱이 실행됩니다.
- 100ms: `application.yml` 설정을 보고 `npx.cmd`, `uvx.exe`로 외부 MCP 서버를 실행합니다.
- 200ms: Spring AI MCP Client가 외부 MCP 서버와 연결합니다.
- 300ms: `McpToolCatalog`가 외부 서버의 도구를 `ToolCallback`으로 모읍니다.
- 400ms: `McpChatService`가 그 도구를 `ChatClient.tools(...)`에 붙입니다.

**한 줄 메모**:
- `McpChatService.java`와 `McpToolCatalog.java`는 MCP 서버가 아니라, 외부 MCP 서버를 가져다 쓰는 연결 코드입니다.
---
## [2026-07-08] Q: Day5 작업물을 GitHub에 커밋하고 푸시하기

**질문**: git origin main push하자

**핵심 개념**:
- Git remote 확인: `origin`이 항상 원하는 저장소를 뜻하는 것은 아니므로 `git remote -v`로 먼저 확인해야 합니다.
- 이번 저장소 구조: `origin`은 `day01.git`, Spring AI 전체 레포는 `springai` remote로 연결되어 있어 잘못된 저장소로 push하지 않도록 주의했습니다.
- 커밋 대상 분리: Day5 프로젝트와 `STUDY_LOG.md`만 스테이징하고, `day04-multimodal/` 및 무관한 MCP 테스트 파일은 제외했습니다.

**Python 비교**:
```python
# push 전에 목적지 URL을 확인하는 것과 같습니다.
remote = "springai"
branch = "main"
```

**Java/Spring 연결**:
```text
day05-tool-mcp
├─ src/main/java
├─ src/main/resources/static
├─ mcp-sandbox
└─ presentation
```

**흐름 타임라인**:
- 0ms: `git status`, `git remote -v`로 저장소와 remote를 확인했습니다.
- 100ms: `day05-tool-mcp` 빌드를 다시 검증했습니다.
- 200ms: Day5 프로젝트와 `STUDY_LOG.md`를 스테이징했습니다.
- 300ms: 잘못된 `origin/day01.git` 대신 Spring AI 레포 remote인 `springai`로 push하기로 정리했습니다.

**한 줄 메모**:
- `origin`이라는 이름보다 실제 URL이 중요합니다. 이번에는 Spring AI 전체 레포인 `springai/main`이 안전한 push 대상입니다.
