package com.study.day03advisormemory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AiController {

    private final AiService aiService;
    private final MemoryChatService memoryChatService;
    private final PersistentChatService persistentChatService;

    public AiController(AiService aiService, MemoryChatService memoryChatService, PersistentChatService persistentChatService) {
        this.aiService = aiService;
        this.memoryChatService = memoryChatService;
        this.persistentChatService = persistentChatService;
    }

    @GetMapping("/api/ask")
    public String ask(@RequestParam String question) {
        return aiService.ask(question);
    }

    @GetMapping("/api/chat-memory")
    public String chatMemory(@RequestParam String question, @RequestParam String conversationId){
        return memoryChatService.chat(question, conversationId);
    }

    @GetMapping("/api/chat-persistent")
    public String chatPersistent(@RequestParam String question, @RequestParam String conversationId){
        return persistentChatService.chat(question, conversationId);
    }


}
