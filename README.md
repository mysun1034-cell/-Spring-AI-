# day06-intergration

Spring Boot, Spring AI, React, PostgreSQL을 연결해서 "대화를 기억하는 헬프데스크 AI"를 만드는 예제 프로젝트다.

이 프로젝트는 단순히 AI에게 질문 한 번 던지고 답만 받는 수준이 아니라, 아래 흐름을 한 번에 담고 있다.

- React 화면에서 질문 입력
- Spring Boot 백엔드로 요청 전달
- Spring AI를 통해 Gemini 모델 호출
- 응답을 SSE로 실시간 스트리밍
- 고객 정보, 사내 규정, 현재 시간 같은 도구 호출
- conversationId 기준으로 대화 문맥 유지
- 대화 기록을 PostgreSQL에 저장
- 새로고침 후 이전 대화 복원

## 오늘 만든 것을 한 문장으로 말하면

"브라우저 채팅 화면에서 질문을 보내면, Spring Boot가 Spring AI와 Gemini를 사용해 답을 조금씩 흘려 보내고, 그 대화를 DB에 저장해 다음 접속 때 다시 보여주는 흐름"을 만든 것이다.

## 왜 이 프로젝트가 어려워 보이는가

이 프로젝트에는 여러 개념이 동시에 들어 있다.

- Spring Boot 웹 서버
- Spring AI 모델 호출
- Tool Calling
- Chat Memory
- PostgreSQL 저장
- React 프론트엔드
- SSE 스트리밍
- Docker로 DB 실행

처음에는 파일이 많아 보여도, 실제 중심축은 몇 개 안 된다.

## 핵심 파일 지도

### 백엔드 시작점

- [src/main/java/com/study/day06intergration/Day06IntergrationApplication.java](src/main/java/com/study/day06intergration/Day06IntergrationApplication.java)

스프링 부트 애플리케이션을 시작하는 파일이다. 스프링이 이 파일을 기준으로 컨트롤러, 서비스, 설정을 읽어 서버를 올린다.

### 전체 설정

- [src/main/resources/application.yml](src/main/resources/application.yml)

여기서 아래 내용을 정한다.

- 어떤 Gemini 모델을 쓸지
- API 키를 어디서 읽을지
- PostgreSQL에 어떻게 붙을지
- Spring AI JDBC 메모리 스키마를 자동으로 만들지
- JPA 테이블을 어떻게 관리할지

### HTTP 요청 진입점

- [src/main/java/com/study/day06intergration/controller/ApiController.java](src/main/java/com/study/day06intergration/controller/ApiController.java)

브라우저에서 오는 요청을 가장 먼저 받는 곳이다.

주요 엔드포인트는 아래와 같다.

- `GET /api/stream-console`
  - 콘솔에서 스트림 흐름을 확인하는 간단한 예제
- `GET /api/stream`
  - 가장 단순한 AI 스트리밍 예제
- `GET /api/chat/stream`
  - 실제 헬프데스크 대화 스트리밍 엔드포인트
- `GET /api/history`
  - conversationId 기준 과거 대화 조회

### 가장 단순한 AI 호출 서비스

- [src/main/java/com/study/day06intergration/service/ChatService.java](src/main/java/com/study/day06intergration/service/ChatService.java)

이 서비스는 Spring AI의 `ChatClient`를 가장 단순하게 사용하는 예제다.

- `ask(question)`는 응답을 한 번에 받는다.
- `askStream(question)`는 응답을 토큰처럼 조금씩 흘려 보낸다.

즉, "Spring AI 최소 사용법"에 해당하는 파일이다.

### 실제 헬프데스크 AI 서비스

- [src/main/java/com/study/day06intergration/service/HelpdeskService.java](src/main/java/com/study/day06intergration/service/HelpdeskService.java)

이 서비스가 오늘의 핵심이다.

여기서는 단순 질문/응답이 아니라 다음을 같이 처리한다.

- 시스템 프롬프트 설정
- Tool 등록
- Chat Memory 연결
- conversationId 기준 문맥 유지
- 스트리밍 응답 생성

즉, 단순 LLM 호출이 아니라 "상담원처럼 일하는 AI"에 가깝게 만든다.

### 대화 메모리 설정

- [src/main/java/com/study/day06intergration/config/ChatMemoryConfig.java](src/main/java/com/study/day06intergration/config/ChatMemoryConfig.java)

여기서는 두 가지 메모리 방식을 만든다.

- `inMemoryChatMemory`
  - 애플리케이션 메모리에만 저장하는 임시 메모리
- `jdbcMemoryChatMemory`
  - DB에 저장하는 메모리

현재 실제 헬프데스크 흐름에서는 `jdbcMemoryChatMemory`를 사용한다.

또한 `maxMessages(20)`으로 최근 20개 메시지만 기억하도록 설정했다.

### 대화 기록 저장 서비스

- [src/main/java/com/study/day06intergration/service/ChatHistoryService.java](src/main/java/com/study/day06intergration/service/ChatHistoryService.java)
- [src/main/java/com/study/day06intergration/entity/ChatHistoryEntity.java](src/main/java/com/study/day06intergration/entity/ChatHistoryEntity.java)
- [src/main/java/com/study/day06intergration/Repository/ChatHistoryRepository.java](src/main/java/com/study/day06intergration/Repository/ChatHistoryRepository.java)
- [src/main/java/com/study/day06intergration/dto/HistoryMessage.java](src/main/java/com/study/day06intergration/dto/HistoryMessage.java)

이 부분은 브라우저 화면 복원용 기록 저장 레이어다.

역할은 이렇게 나뉜다.

- `ChatHistoryEntity`
  - DB 테이블 한 줄의 모양
- `ChatHistoryRepository`
  - JPA로 DB를 읽고 쓰는 통로
- `ChatHistoryService`
  - 저장/조회 로직
- `HistoryMessage`
  - 프론트에 내려줄 응답 DTO

중요한 점은 이것이 `ChatMemory`와 완전히 같은 개념은 아니라는 것이다.

## 꼭 구분해야 하는 두 가지 기억

이 프로젝트에는 비슷해 보이지만 다른 "기억"이 두 개 있다.

### 1. Spring AI의 Chat Memory

목적:

- AI가 이전 대화를 이어서 이해하도록 만들기

예:

- 사용자가 "그 고객 다시 설명해줘"라고 했을 때
- AI가 앞 문맥을 참고하게 함

이건 AI의 작업 기억이다.

### 2. chat_history 테이블

목적:

- 프론트 화면에서 이전 메시지를 다시 그리기

예:

- 새로고침 후에도 이전 대화를 채팅창에 다시 보여주기

이건 UI 복원용 기록이다.

둘 다 "대화를 저장"하지만 쓰임새가 다르다.

## Tool Calling이란 무엇인가

이 프로젝트에서 AI는 필요한 경우 도구를 직접 호출할 수 있다.

### 현재 시간 도구

- [src/main/java/com/study/day06intergration/tool/DateTimeTools.java](src/main/java/com/study/day06intergration/tool/DateTimeTools.java)

AI가 현재 날짜/시간이 필요할 때 호출한다.

### 고객 정보 도구

- [src/main/java/com/study/day06intergration/tool/CustomerTools.java](src/main/java/com/study/day06intergration/tool/CustomerTools.java)

고객 ID를 넣으면 고객 이름, 등급, 응답 SLA 같은 정보를 가져온다.

예:

- `C001` -> VIP 고객

### 사내 규정 도구

- [src/main/java/com/study/day06intergration/tool/CompanyRuleTools.java](src/main/java/com/study/day06intergration/tool/CompanyRuleTools.java)

배포, 코드리뷰, 근무, 보안 같은 사내 규정을 주제별로 조회한다.

즉, AI가 그냥 상상으로 답하지 말고, 필요하면 정해진 자바 메서드를 호출해서 더 정확하게 답하게 하는 구조다.

## 실제 요청이 흐르는 순서

여기서부터가 오늘의 전체 스토리다.

### 1. 브라우저가 열리면 conversationId를 만든다

- [frontend/src/App.jsx](frontend/src/App.jsx)

프론트는 `localStorage`에 `conversationId`를 저장한다.

왜 필요한가:

- 같은 사용자의 같은 대화를 이어가기 위해서
- 새로고침해도 같은 conversationId를 계속 쓰기 위해서

예:

- 처음 접속: `web-1720490000000`
- 새로고침 후: 같은 ID 재사용

### 2. 브라우저는 먼저 과거 대화를 읽어온다

프론트는 시작하자마자 아래 요청을 보낸다.

- `GET /api/history?conversationId=...`

컨트롤러는 `ChatHistoryService.history()`를 호출해 DB에서 이전 메시지를 읽는다.

프론트는 이 메시지 목록을 채팅창에 렌더링한다.

즉, 새로고침해도 대화가 이어 보이게 된다.

### 3. 사용자가 질문을 입력한다

사용자가 채팅 입력창에 질문을 쓰고 전송 버튼을 누르면 `send()`가 실행된다.

프론트는 사용자 메시지를 먼저 화면에 추가한다.

그다음 아래 SSE 요청을 연다.

- `GET /api/chat/stream?question=...&conversationId=...`

### 4. 백엔드는 사용자 질문을 먼저 저장한다

`ApiController.helpdeskStream()` 안에서 아래 일이 먼저 일어난다.

- `chatHistoryService.save(conversationId, "user", question)`

즉, 사용자가 한 질문을 DB에 먼저 저장한다.

### 5. HelpdeskService가 AI 응답을 생성한다

`HelpdeskService.chatStream(question, conversationId)`가 실행된다.

여기서 내부적으로 하는 일:

- 시스템 프롬프트 적용
- tools 등록
- ChatMemory에 conversationId 전달
- Gemini 모델 호출
- 응답을 스트림으로 받기

### 6. 응답은 토막토막 프론트로 흘러간다

백엔드는 `Flux<String>`을 이용해 생성된 텍스트 조각을 SSE 형식으로 바꿔 보낸다.

프론트는 `EventSource`로 그 조각을 하나씩 받는다.

그래서 사용자는 답변이 한 번에 확 떨어지는 게 아니라, 실제로 써지는 것처럼 보게 된다.

### 7. 백엔드는 응답 전체를 모아서 다시 저장한다

컨트롤러는 스트리밍하면서 `StringBuilder`에 응답 조각을 계속 붙인다.

응답이 끝나면:

- `chatHistoryService.save(conversationId, "assistant", answer.toString())`

를 실행한다.

즉, AI 답변도 DB에 저장된다.

### 8. 프론트는 done 이벤트를 받으면 스트림을 닫는다

백엔드는 마지막에 `done` 이벤트를 보낸다.

프론트는 이 이벤트를 받으면:

- 스트리밍 중 텍스트를 최종 메시지로 확정
- `EventSource.close()`
- 상태를 `idle`로 복귀

한다.

이 과정을 해야 브라우저가 SSE를 계속 재연결하지 않는다.

## 프론트엔드 구조

### 메인 채팅 화면

- [frontend/src/App.jsx](frontend/src/App.jsx)

여기서 관리하는 상태:

- `messages`
  - 확정된 채팅 기록
- `input`
  - 입력창 값
- `status`
  - `idle`, `loading`, `streaming`
- `streaming`
  - 아직 완성되지 않은 스트리밍 답변
- `conversationId`
  - 현재 대화방 식별자

### Vite 프록시

- [frontend/vite.config.js](frontend/vite.config.js)

프론트는 5173 포트에서 실행되고, 백엔드는 8080 포트에서 실행된다.

프록시 설정으로 `/api` 요청을 자동으로 8080으로 넘긴다.

즉 프론트 코드는 굳이 `http://localhost:8080`을 직접 몰라도 된다.

## CORS 설정

- [src/main/java/com/study/day06intergration/config/CorsConfig.java](src/main/java/com/study/day06intergration/config/CorsConfig.java)

브라우저는 다른 포트로 가는 요청을 기본적으로 조심한다.

프론트가 `http://localhost:5173`,
백엔드가 `http://localhost:8080` 이므로,
백엔드에서 `/api/**` 요청에 대해 `5173`을 허용한다.

## Docker는 어디서 쓰는가

- [compose.yaml](compose.yaml)

이 프로젝트는 PostgreSQL을 Docker로 띄우는 방식이다.

왜 필요한가:

- 로컬 PC마다 DB 설치 상태가 달라도 같은 환경으로 실행 가능
- 스프링이 기대하는 DB 계정과 비밀번호를 고정 가능
- Spring AI JDBC 메모리와 대화 기록 저장소를 쉽게 준비 가능

## 실행 방법

### 1. PostgreSQL 실행

```bash
docker compose up -d
```

### 2. 백엔드 실행

Git Bash:

```bash
cd /c/Users/금정산2-PC02/p2-spring/spring-ai-study/day06-intergration
./gradlew bootRun
```

PowerShell:

```powershell
cd C:\Users\금정산2-PC02\p2-spring\spring-ai-study\day06-intergration
.\gradlew.bat bootRun
```

### 3. 프론트 실행

Git Bash:

```bash
cd /c/Users/금정산2-PC02/p2-spring/spring-ai-study/day06-intergration/frontend
npm run dev
```

PowerShell:

```powershell
cd C:\Users\금정산2-PC02\p2-spring\spring-ai-study\day06-intergration\frontend
npm.cmd run dev
```

PowerShell에서 `npm run dev` 대신 `npm.cmd run dev`를 쓰는 이유는 실행 정책 때문에 `npm.ps1`이 막힐 수 있기 때문이다.

## 확인 포인트

### 백엔드 컴파일 확인

```bash
./gradlew compileJava
```

### 프론트 빌드 확인

```bash
cd frontend
npm run build
```

PowerShell이면:

```powershell
npm.cmd run build
```

### 히스토리 API 확인

브라우저에서:

```text
http://localhost:8080/api/history?conversationId=test
```

정상이라면 `[]` 또는 이전 대화 JSON 배열이 나온다.

### 스트리밍 API 확인

브라우저에서:

```text
http://localhost:8080/api/chat/stream?question=안녕&conversationId=test
```

정상이라면 `data:{"text":"..."}` 형식으로 응답이 이어진다.

### 프론트 최종 확인

브라우저에서:

```text
http://127.0.0.1:5173
```

질문을 입력했을 때 답변이 스트리밍되면 연결 완료다.

## 오늘 실제로 막혔던 문제들

### 1. `/api/chat/stream` 404

원인:

- 프론트가 호출하는 경로와 백엔드 매핑 경로가 달랐기 때문

정리:

- 최종적으로 프론트는 `/api/chat/stream`
- 백엔드도 `/api/chat/stream`

로 맞춘다.

### 2. `/api/history` 404

원인:

- 프론트는 history를 호출하는데 백엔드에 그 API가 없거나, 수정 반영 전 서버가 떠 있었기 때문

정리:

- `ChatHistoryService`
- `HistoryMessage`
- `/api/history`

흐름을 맞춘다.

### 3. Gradle 컴파일 실패

실패 메시지:

- `cannot find symbol variable conversationID`

원인:

- `service/ChatHistoryRepository.java`라는 잘못된 중복 클래스가 있었고
- `conversationId` 대신 `conversationID` 오타까지 있었음

정리:

- 잘못된 중복 파일은 제거
- 실제 JPA 인터페이스는 `Repository/ChatHistoryRepository.java`를 사용
- `ChatHistoryService`를 실제 스프링 빈으로 등록

## 지금 이 프로젝트를 어떻게 이해하면 되는가

처음에는 모든 파일을 한 번에 이해하려 하지 말고 아래 순서로 보면 된다.

### 1단계: 전체 입구 보기

- `application.yml`
- `Day06IntergrationApplication.java`

### 2단계: HTTP 요청 보기

- `ApiController.java`

### 3단계: AI 호출 보기

- `ChatService.java`
- `HelpdeskService.java`

### 4단계: 기억과 저장 보기

- `ChatMemoryConfig.java`
- `ChatHistoryService.java`
- `ChatHistoryEntity.java`
- `ChatHistoryRepository.java`

### 5단계: 프론트 보기

- `frontend/src/App.jsx`
- `frontend/vite.config.js`

## 마지막 요약

이 프로젝트는 "Spring AI를 실제 서비스처럼 쓰는 최소 헬프데스크 앱"이다.

핵심만 다시 말하면:

- Spring Boot가 웹 서버 역할을 한다.
- Spring AI가 Gemini와 연결된다.
- Tool Calling으로 현재 시간, 고객 정보, 사내 규정을 조회할 수 있다.
- Chat Memory로 대화 문맥을 유지한다.
- Chat History 테이블로 화면 복원용 이력을 저장한다.
- React는 SSE로 응답을 실시간으로 받는다.
- Docker PostgreSQL은 메모리와 기록 저장소 역할을 한다.

즉 오늘 만든 것은
"AI 호출"
하나가 아니라
"웹, AI, 도구, 메모리, DB, 프론트 연결"이 다 묶인 하나의 작은 제품 흐름이다.
