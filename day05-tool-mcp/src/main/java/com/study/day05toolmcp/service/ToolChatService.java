package com.study.day05toolmcp.service;

import com.study.day05toolmcp.tool.CompanyRuleTools;
import com.study.day05toolmcp.tool.CustomerTools;
import com.study.day05toolmcp.tool.DateTimeTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.stereotype.Service;

@Service
public class ToolChatService {

    private final ChatClient chatClient;
    private final DateTimeTools dateTimeTools;
    private final CustomerTools customerTools;
    private final CompanyRuleTools companyRuleTools;

    public ToolChatService(ChatClient.Builder builder, DateTimeTools dateTimeTools, CustomerTools customerTools, CompanyRuleTools companyRuleTools) {
        this.chatClient = builder
                .defaultSystem("""
                        당신은 장기요양 상담을 돕는 AI 헬퍼입니다.
                        답변할 때는 확인된 도구 결과와 일반 안내를 구분하고,
                        의료적·법적 최종 판단은 사람이 확인해야 한다고 안내하세요.
                        """)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
        this.dateTimeTools = dateTimeTools;
        this.customerTools = customerTools;
        this.companyRuleTools = companyRuleTools;
    }

    public String ask(String question) {
        return chatClient.prompt()
                .tools(dateTimeTools)
                .user(question).call().content();
    }

    public String toolRecipient(String question) {
        return chatClient.prompt()
                .tools(customerTools)
                .user(question).call().content();
    }

    public String toolRule(String question) {
        return chatClient.prompt()
                .tools(companyRuleTools)
                .user(question).call().content();
    }

    // 세 가지 도구 모두 골라서 호출
    public String chat(String question) {
        return chatClient.prompt()
                .user(question)
                .tools(dateTimeTools, customerTools, companyRuleTools)
                .call().content();
    }

}
