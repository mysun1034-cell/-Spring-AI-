package com.study.carelinkchatbot;

import com.study.carelinkchatbot.advisor.RequestLoggingAdvisor;
import com.study.carelinkchatbot.dto.GuardianChatResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class GuardianChatService {

    private static final String SYSTEM_PROMPT = """
            당신은 CareLink 보호자 상담 도우미입니다.
            - 한국어로 답변하세요.
            - 보호자가 바로 실천할 수 있는 내용을 먼저 설명하세요.
            - 의료 진단을 단정하지 말고, 위험 신호가 있으면 병원 또는 119 상담을 권하세요.
            - 이전 대화 맥락을 이어서 답변하세요.
            """;

    private final ChatClient chatClient;

    public GuardianChatService(ChatClient.Builder builder,
                               @Qualifier("jdbcChatMemory") ChatMemory chatMemory,
                               RequestLoggingAdvisor requestLoggingAdvisor) {
        this.chatClient = builder
                .defaultAdvisors(
                        requestLoggingAdvisor,
                        MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    public GuardianChatResponse chat(String question, String conversationId) {
        String answer = chatClient.prompt()
                .system(SYSTEM_PROMPT)
                .user(question)
                .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();

        return new GuardianChatResponse(conversationId, answer);
    }
}

