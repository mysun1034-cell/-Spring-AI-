package com.study.day03advisormemory.advisor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;


public class AdvisorB implements CallAdvisor {

    Logger log = LoggerFactory.getLogger(AdvisorB.class);

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
        log.info("[전처리] advisorB 호출");
        ChatClientResponse response = callAdvisorChain.nextCall(chatClientRequest);
        log.info("[후처리] advisorB 호출");
        return response;
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int getOrder() {
        return 10;
    }
}
