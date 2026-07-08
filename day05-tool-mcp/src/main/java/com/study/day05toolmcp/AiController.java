package com.study.day05toolmcp;

import com.study.day05toolmcp.mcp.McpChatService;
import com.study.day05toolmcp.service.ChatService;
import com.study.day05toolmcp.service.HelpdeskService;
import com.study.day05toolmcp.service.ToolChatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AiController {
    private final ChatService chatService;
    private final ToolChatService toolChatService;
    private final HelpdeskService helpdeskService;
    private final McpChatService mcpChatService;

    public AiController(ChatService chatService, ToolChatService toolChatService, HelpdeskService helpdeskService, McpChatService mcpChatService) {
        this.chatService = chatService;
        this.toolChatService = toolChatService;
        this.helpdeskService = helpdeskService;
        this.mcpChatService = mcpChatService;
    }

    @GetMapping("/api/ask")
    public String ask(@RequestParam String question) {
        return chatService.ask(question);
    }

    @GetMapping("/api/tool/datetime")
    public String toolDatetime(@RequestParam String question) {
        return toolChatService.ask(question);
    }

    @GetMapping("/api/tool/customer")
    public String toolCustomer(@RequestParam String question) {
        return toolChatService.toolRecipient(question);
    }

    @GetMapping("/api/tool/recipient")
    public String toolRecipient(@RequestParam String question) {
        return toolChatService.toolRecipient(question);
    }

    @GetMapping("/api/tool/rule")
    public String toolRule(@RequestParam String question) {
        return toolChatService.toolRule(question);
    }

    @GetMapping("/api/tool/chat")
    public String toolChat(@RequestParam String question) {
        return toolChatService.chat(question);
    }

    @GetMapping("/api/tool-chat")
    public String toolChatAlias(@RequestParam String question) {
        return toolChatService.chat(question);
    }

    @GetMapping("/api/tool/help")
    public String helpChat(@RequestParam String question, @RequestParam(defaultValue = "care-demo") String conversationId) {
        return helpdeskService.chat(question, conversationId);
    }

    @GetMapping("/api/assistant")
    public String assistant(@RequestParam String question, @RequestParam(defaultValue = "care-demo") String conversationId) {
        return helpdeskService.chat(question, conversationId);
    }

    @GetMapping("/api/mcp/filesystem")
    public String mcpFilesystem(@RequestParam String question) {
        return mcpChatService.chatFilesystem(question);
    }

    @GetMapping("/api/mcp/fetch")
    public String mcpFetch(@RequestParam String question) {
        return mcpChatService.chatFetch(question);
    }

    @GetMapping("/api/mcp-chat")
    public String mcpChat(@RequestParam String question) {
        return mcpChatService.chatAllMcp(question);
    }

    @GetMapping("/api/mixed-chat")
    public String mixedChat(@RequestParam String question) {
        return mcpChatService.chatMixed(question);
    }
}
