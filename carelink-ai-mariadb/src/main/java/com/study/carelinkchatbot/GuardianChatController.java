package com.study.carelinkchatbot;

import com.study.carelinkchatbot.dto.GuardianChatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GuardianChatController {

    private final GuardianChatService guardianChatService;

    public GuardianChatController(GuardianChatService guardianChatService) {
        this.guardianChatService = guardianChatService;
    }

    @GetMapping("/guardian-chat")
    public GuardianChatResponse chat(@RequestParam String question,
                                     @RequestParam String conversationId) {
        return guardianChatService.chat(question, conversationId);
    }
}

