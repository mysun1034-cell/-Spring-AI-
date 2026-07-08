package com.study.day05toolmcp.mcp;

import io.modelcontextprotocol.client.McpSyncClient;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class McpToolCatalog {

    private final ToolCallback[] filesystemTools;
    private final ToolCallback[] fetchTools;
    private final ToolCallback[] allTools;

    public McpToolCatalog(List<McpSyncClient> mcpClients) {
        this.filesystemTools = toolsFrom(namedClients(mcpClients, "filesystem"));
        this.fetchTools = toolsFrom(namedClients(mcpClients, "fetch"));
        this.allTools = toolsFrom(mcpClients);
    }

    private ToolCallback[] toolsFrom(List<McpSyncClient> mcpClients) {
        if (mcpClients.isEmpty()) {
            return new ToolCallback[0];
        }
        return SyncMcpToolCallbackProvider
                .builder()
                .mcpClients(mcpClients)
                .build()
                .getToolCallbacks();
    }

    private List<McpSyncClient> namedClients(List<McpSyncClient> mcpClients, String name) {
        List<McpSyncClient> selectedClients = new ArrayList<>();
        for (McpSyncClient client : mcpClients) {
            if (client.getServerInfo() != null && name.equals(client.getServerInfo().name())) {
                selectedClients.add(client);
            }
        }
        return selectedClients;
    }

    public ToolCallback[] filesystemTools() {
        return filesystemTools;
    }

    public ToolCallback[] fetchTools() {
        return fetchTools;
    }

    public ToolCallback[] allTools() {
        return allTools;
    }
}
