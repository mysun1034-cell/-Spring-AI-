# Day5 발표용 이미지

발표 순서대로 이미지를 사용하면 됩니다.

## 0. 표지 배경

- `00-cover.png`
- 용도: 발표 첫 장 배경, 프로젝트 분위기 소개

## 1. 전체 아키텍처

- `01-architecture.png`
- `01-architecture.svg`
- 용도: 프론트에서 백엔드, ChatClient, Tool, MCP, Gemini까지 전체 흐름 설명

발표 멘트:

```text
사용자가 화면에서 질문을 보내면 AiController가 받고, 각 Service가 ChatClient에 Tool 또는 MCP를 붙여 Gemini에게 요청합니다.
```

## 2. 3분 시연 시나리오

- `02-demo-scenario.png`
- `02-demo-scenario.svg`
- 용도: 실제 시연 순서 안내

발표 순서:

```text
1. /api/tool-chat으로 로컬 Tool 확인
2. /api/assistant로 Chat Memory 확인
3. /api/mcp-chat으로 MCP 문서 조회
4. /api/mixed-chat으로 Local Tool + MCP 결합 확인
```

## 3. 모드별 구현 비교

- `03-mode-matrix.png`
- `03-mode-matrix.svg`
- 용도: 프론트 모드가 각각 어떤 백엔드 endpoint와 Service에 연결되는지 설명

발표 멘트:

```text
화면 모드는 단순한 디자인 버튼이 아니라, 호출할 endpoint를 바꾸는 스위치입니다.
endpoint마다 ChatClient에 붙는 Memory와 Tool 조합이 달라집니다.
```

## 사용 팁

- PPT에는 `.png`를 넣으면 가장 간단합니다.
- 글자를 확대해도 선명해야 하면 `.svg`를 넣으면 좋습니다.
- SVG는 브라우저에서 열면 일부 라인과 신호가 은은하게 움직입니다.
