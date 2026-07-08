const endpointSelect = document.querySelector("#endpoint");
const conversationInput = document.querySelector("#conversationId");
const questionInput = document.querySelector("#question");
const responseBox = document.querySelector("#response");
const liveBadge = document.querySelector("#liveBadge");
const activeEndpoint = document.querySelector("#activeEndpoint");
const timer = document.querySelector("#timer");
const memoryState = document.querySelector("#memoryState");
const modeCards = document.querySelectorAll(".mode-card");
const quickPrompts = document.querySelectorAll("[data-prompt]");

const guardianPrompt = "R001 수급자의 야간 돌봄 주의사항과 보호자에게 전달할 안내문을 작성해주세요.";
const followUpPrompt = "방금 말한 수급자를 기준으로, 보호자에게 전화할 때 30초 안에 말할 수 있는 문장으로 정리해주세요.";

let activeController = null;
let loadingInterval = null;

document.addEventListener("pointermove", (event) => {
    document.documentElement.style.setProperty("--mouse-x", `${event.clientX}px`);
    document.documentElement.style.setProperty("--mouse-y", `${event.clientY}px`);
});

modeCards.forEach((card) => {
    card.addEventListener("click", () => {
        const endpoint = card.dataset.endpoint;
        endpointSelect.value = endpoint;
        setActiveMode(endpoint);
        updateTelemetry();
    });
});

endpointSelect.addEventListener("change", () => {
    setActiveMode(endpointSelect.value);
    updateTelemetry();
});

quickPrompts.forEach((button) => {
    button.addEventListener("click", () => {
        questionInput.value = button.dataset.prompt;
        questionInput.focus();
    });
});

document.querySelector("#runGuardian").addEventListener("click", () => {
    endpointSelect.value = "/api/assistant";
    questionInput.value = guardianPrompt;
    setActiveMode("/api/assistant");
    sendQuestion();
});

document.querySelector("#runFollowUp").addEventListener("click", () => {
    endpointSelect.value = "/api/assistant";
    questionInput.value = followUpPrompt;
    setActiveMode("/api/assistant");
    sendQuestion();
});

questionInput.addEventListener("keydown", (event) => {
    if ((event.ctrlKey || event.metaKey) && event.key === "Enter") {
        sendQuestion();
    }
});

function setActiveMode(endpoint) {
    modeCards.forEach((card) => {
        card.classList.toggle("active", card.dataset.endpoint === endpoint);
    });
}

function updateTelemetry() {
    const endpoint = endpointSelect.value;
    activeEndpoint.textContent = endpoint;
    memoryState.textContent = endpoint.includes("assistant") || endpoint.includes("help")
        ? "memory: on"
        : "memory: off";
}

async function sendQuestion() {
    const endpoint = endpointSelect.value;
    const question = questionInput.value.trim();
    const conversationId = conversationInput.value.trim() || "carelink-night-01";

    if (!question) {
        setError("질문을 먼저 입력해주세요.");
        return;
    }

    if (activeController) {
        activeController.abort();
    }

    activeController = new AbortController();
    const startedAt = performance.now();

    setLoading(endpoint);

    try {
        const url = buildUrl(endpoint, question, conversationId);
        const response = await fetch(url, { signal: activeController.signal });
        const body = await response.text();

        if (!response.ok) {
            throw new Error(`${response.status} ${response.statusText}\n\n${body}`);
        }

        setSuccess(body, performance.now() - startedAt);
    } catch (error) {
        if (error.name === "AbortError") {
            return;
        }

        setError(error.message || "요청 중 오류가 발생했습니다.");
    } finally {
        activeController = null;
        stopTimer();
    }
}

function buildUrl(endpoint, question, conversationId) {
    const params = new URLSearchParams();
    params.set("question", question);

    if (endpoint.includes("assistant") || endpoint.includes("help")) {
        params.set("conversationId", conversationId);
    }

    return `${endpoint}?${params.toString()}`;
}

function setLoading(endpoint) {
    updateTelemetry();
    liveBadge.className = "live-badge loading";
    liveBadge.textContent = "Inferencing";
    responseBox.textContent = "AI 작전 콘솔 연결 중...\n\n";
    responseBox.textContent += `endpoint: ${endpoint}\n`;
    responseBox.textContent += "status: Spring AI ChatClient 호출 준비\n";
    responseBox.textContent += "trace: Tool / Memory / MCP 경로 확인 중";

    const startedAt = performance.now();
    loadingInterval = window.setInterval(() => {
        const elapsed = Math.round(performance.now() - startedAt);
        timer.textContent = `${elapsed}ms`;
        responseBox.textContent = responseBox.textContent.replace(/trace: .*/, `trace: ${makeTrace(elapsed)}`);
    }, 180);
}

function setSuccess(body, elapsed) {
    liveBadge.className = "live-badge";
    liveBadge.textContent = "Complete";
    timer.textContent = `${Math.round(elapsed)}ms`;
    responseBox.textContent = body;
}

function setError(message) {
    liveBadge.className = "live-badge error";
    liveBadge.textContent = "Error";
    responseBox.textContent = `요청 실패\n\n${message}\n\n확인할 것:\n- Spring Boot 서버가 실행 중인지\n- GOOGLE_API_KEY가 설정되어 있는지\n- MCP 요청이면 npx/uvx가 설치되어 있는지`;
}

function stopTimer() {
    if (loadingInterval) {
        window.clearInterval(loadingInterval);
        loadingInterval = null;
    }
}

function makeTrace(elapsed) {
    const traces = [
        "Controller 진입 확인",
        "Service 라우팅 중",
        "ChatClient prompt 구성",
        "Tool 후보 분석",
        "Memory conversationId 확인",
        "Gemini 응답 대기",
        "응답 본문 정리"
    ];

    const index = Math.floor(elapsed / 540) % traces.length;
    return traces[index];
}

updateTelemetry();
