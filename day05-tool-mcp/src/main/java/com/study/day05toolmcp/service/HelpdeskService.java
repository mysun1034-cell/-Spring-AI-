package com.study.day05toolmcp.service;

import com.study.day05toolmcp.config.ChatMemoryConfig;
import com.study.day05toolmcp.tool.CompanyRuleTools;
import com.study.day05toolmcp.tool.CustomerTools;
import com.study.day05toolmcp.tool.DateTimeTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class HelpdeskService {
    private final ChatClient chatClient;
    private final DateTimeTools dateTimeTools;
    private final CustomerTools customerTools;
    private final CompanyRuleTools companyRuleTools;

    public HelpdeskService(ChatClient.Builder builder,
                           @Qualifier("inMemoryChatMemory") ChatMemory chatMemory,
                           DateTimeTools dateTimeTools,
                           CustomerTools customerTools,
                           CompanyRuleTools companyRuleTools) {
        this.chatClient = builder
                .defaultSystem("""
                        당신은 장기요양 상담을 돕는 AI 헬퍼입니다.
                        보호자나 요양보호사의 질문에 답할 때 필요하면 도구로 수급자 상태, 돌봄 규칙, 현재 시각을 확인하세요.
                        확인된 도구 결과와 일반 안내를 구분하고, 의료적·법적 최종 판단은 담당자나 전문기관 확인이 필요하다고 안내하세요.
                        정중하고 간결한 한국어로 답변하세요.
                        """)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        new SimpleLoggerAdvisor()
                )
                .build();
        this.dateTimeTools = dateTimeTools;
        this.customerTools = customerTools;
        this.companyRuleTools = companyRuleTools;
    }

    public String chat(String question, String conversationId) {
        return chatClient.prompt()
                .user(question)
                .tools(dateTimeTools, customerTools, companyRuleTools)
                .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();
    }
}
