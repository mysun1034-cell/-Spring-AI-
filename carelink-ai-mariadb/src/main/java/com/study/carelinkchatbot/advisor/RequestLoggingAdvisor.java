package com.study.carelinkchatbot.advisor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.stereotype.Component;

@Component
public class RequestLoggingAdvisor implements CallAdvisor {

    private static final Logger log = LoggerFactory.getLogger(RequestLoggingAdvisor.class);

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
        log.info("[guardian-chat] advisor start");
        ChatClientResponse response = callAdvisorChain.nextCall(chatClientRequest);
        log.info("[guardian-chat] advisor end");
        return response;
    }

    @Override
    public String getName() {
        return "requestLoggingAdvisor";
    }

    @Override
    public int getOrder() {
        return 0;
    }
}

