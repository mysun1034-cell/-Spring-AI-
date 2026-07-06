package com.study.day03advisormemory;

import com.study.day03advisormemory.advisor.*;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiService {

    private final ChatClient chatClient;
    private ChatMemory chatMemory;

    public AiService(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultSystem("""
                        당신은 사내 개발팀을 돕는 AI assistant이다.
                        정중하고, 간결한 한국어로 답변하시오.
                        """)
                .defaultAdvisors(new MaxCharLengthAdvisor(), new CallCounterAdvisor(),
                new SafeGuardAdvisor(List.of("계좌번호", "폭탄", "천안문"),
                        "해당 질문은 민감한 컨텐츠 요청입니다. 응답할 수 없습니다.",
                        Ordered.HIGHEST_PRECEDENCE), // 내장 Advisor
                new SimpleLoggerAdvisor()   // 내장 로깅
                )
                .build();
    }

    public String ask(String question) {
        return chatClient.prompt()
                .advisors(new AdvisorA(), new AdvisorB(), new AdvisorC()
                        ) // advisor 삽입 적용
                .user(question)
                .call()
                .content();
    }
}
