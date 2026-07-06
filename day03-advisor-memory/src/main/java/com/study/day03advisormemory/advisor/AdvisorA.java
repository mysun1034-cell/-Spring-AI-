package com.study.day03advisormemory.advisor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;


public class AdvisorA implements CallAdvisor {

    Logger log = LoggerFactory.getLogger(AdvisorA.class);

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
        log.info("[전처리] advisorA 호출");
        ChatClientResponse response = callAdvisorChain.nextCall(chatClientRequest);
        log.info("[후처리] advisorA 호출");
        return response;
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    // Order숫자가 큰 것이 전처리에서 나중에 호출된다.
    @Override
    public int getOrder() {
        return 100;
    }
}
