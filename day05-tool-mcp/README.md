# AI 장기요양 헬퍼

Spring AI Day5 과제용 프로젝트입니다. 기본 ChatClient 호출 위에 로컬 Tool Calling, Chat Memory, MCP filesystem/fetch 도구를 붙여 장기요양 상담 보조 AI를 구현합니다.

## 핵심 개념

- 모델은 직접 실행하지 않고 도구 실행을 요청합니다.
- 실제 실행은 Spring 애플리케이션과 MCP 서버가 담당합니다.
- 로컬 `@Tool`과 MCP 서버 도구는 모두 `ToolCallback`으로 `ChatClient`에 붙습니다.
- 상담형 흐름은 `ChatMemory`와 `conversationId`로 이어갑니다.

## 실행 전 준비

```powershell
setx GOOGLE_API_KEY "본인_API_KEY"
cmd /c npx --version
uvx --version
```

PowerShell 실행 정책 때문에 `npx`가 막히면 앱 설정에서는 `npx.cmd`를 사용합니다.

## API

| API | 역할 |
|---|---|
| `/api/ask` | 도구 없는 기본 챗 |
| `/api/tool/datetime` | 현재 날짜/시간 도구 |
| `/api/tool/customer` | LMS 호환용 수급자 조회 도구 |
| `/api/tool/recipient` | 수급자 조회 도구 |
| `/api/tool/rule` | 돌봄 규칙 조회 도구 |
| `/api/tool-chat` | 시간, 수급자, 돌봄 규칙 중 모델이 선택 |
| `/api/assistant` | 도구 + Chat Memory 상담 |
| `/api/mcp/filesystem` | 로컬 문서 읽기 MCP |
| `/api/mcp/fetch` | 웹 페이지 가져오기 MCP |
| `/api/mcp-chat` | MCP 서버 도구 전체 사용 |
| `/api/mixed-chat` | 로컬 Tool + MCP 도구 함께 사용 |

## 테스트 질문

```text
/api/tool/recipient?question=R001 어르신 상태 알려줘
/api/tool/rule?question=야간돌봄 규칙 알려줘
/api/tool-chat?question=R001 상태와 야간돌봄 주의사항 같이 알려줘
/api/assistant?conversationId=care-001&question=R001 상태 먼저 확인해줘
/api/assistant?conversationId=care-001&question=그 어르신 야간 주의사항도 이어서 정리해줘
/api/mcp/filesystem?question=장기요양-주의사항.md 파일을 읽고 보호자에게 전달할 내용으로 요약해줘
/api/mixed-chat?question=R001 상태를 확인하고 보호자-상담-FAQ.md에서 낙상 위험 안내도 같이 찾아줘
```

## 제출 체크리스트

- datetime 도구 응답 캡처
- recipient/customer 도구 응답 캡처
- rule 도구 응답 캡처
- tool-chat 응답 캡처
- assistant 2턴 memory 응답 캡처
- mcp filesystem 응답 캡처
- mcp fetch 응답 캡처
- mixed-chat 응답 캡처

## 주의

- 실제 API Key는 코드와 GitHub에 올리지 않습니다.
- `data/`와 H2 DB 파일은 커밋하지 않습니다.
- 이 프로젝트는 상담 보조용 예시이며 의료적·법적 최종 판단을 자동화하지 않습니다.

## Frontend UI

Spring Boot 정적 리소스로 팔란티어풍 작전 콘솔 UI를 추가했습니다.

- 접속 주소: `http://localhost:8080/`
- 화면 파일: `src/main/resources/static/index.html`
- 스타일 파일: `src/main/resources/static/styles.css`
- 동작 파일: `src/main/resources/static/app.js`

화면에서 바로 호출할 수 있는 주요 API는 다음과 같습니다.

- `/api/assistant`: Tool Calling + Chat Memory
- `/api/tool-chat`: 로컬 Tool 3종(DateTime, Recipient, Rule)
- `/api/mcp-chat`: MCP 도구 전체
- `/api/mixed-chat`: 로컬 Tool + MCP Tool 결합

UI는 `conversationId`를 유지한 채 후속 질문을 보낼 수 있으므로, `/api/assistant`에서 Chat Memory가 어떻게 이어지는지 시연하기 좋습니다.
