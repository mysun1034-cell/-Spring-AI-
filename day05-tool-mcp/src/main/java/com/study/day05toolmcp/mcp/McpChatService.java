package com.study.day05toolmcp.mcp;

import com.study.day05toolmcp.tool.CompanyRuleTools;
import com.study.day05toolmcp.tool.CustomerTools;
import com.study.day05toolmcp.tool.DateTimeTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Stream;

@Service
public class McpChatService {

    private final ChatClient chatClient;
    private final McpToolCatalog catalog;
    private final DateTimeTools dateTimeTools;
    private final CustomerTools customerTools;
    private final CompanyRuleTools companyRuleTools;

    public McpChatService(ChatClient.Builder builder, McpToolCatalog catalog, DateTimeTools dateTimeTools,
                          CustomerTools customerTools, CompanyRuleTools companyRuleTools) {
        this.chatClient = builder
                .defaultSystem("""
                        당신은 장기요양 상담을 돕는 AI 헬퍼입니다.
                        MCP 문서 도구를 사용할 때는 문서에서 확인한 내용과 일반 안내를 구분하세요.
                        """)
                .build();
        this.catalog = catalog;
        this.dateTimeTools = dateTimeTools;
        this.customerTools = customerTools;
        this.companyRuleTools = companyRuleTools;
    }

    public String chatFilesystem(String question) {
        return chatClient.prompt().user(question)
                .tools((Object[]) catalog.filesystemTools())
                .call().content();
    }

    public String chatFetch(String question) {
        return chatClient.prompt().user(question)
                .tools((Object[]) catalog.fetchTools())
                .call().content();
    }

    public String chatAllMcp(String question) {
        return chatClient.prompt().user(question)
                .tools((Object[]) catalog.allTools())
                .call().content();
    }

    public String chatMixed(String question) {
        Object[] tools = Stream.concat(
                Stream.of(dateTimeTools, customerTools, companyRuleTools),
                Arrays.stream(catalog.allTools())
        ).toArray();

        return chatClient.prompt().user(question)
                .tools(tools)
                .call().content();
    }
}
