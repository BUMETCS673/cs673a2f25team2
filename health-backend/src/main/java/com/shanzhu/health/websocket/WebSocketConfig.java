/**
 * @projectName springAi
 * @package com.example.springai.config
 * @className com.example.springai.websocket.WebSocketConfig
 * @copyright Copyright 2024 Thunisoft, Inc All rights reserved.
 */
package com.shanzhu.health.websocket;


import com.shanzhu.health.service.IBodyNotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final wsOpenAiChatModel openAiChatModel;
    private final IBodyNotesService bodyNotesService;


    @Autowired
    public WebSocketConfig(wsOpenAiChatModel openAiChatModel, IBodyNotesService bodyNotesService) {
        this.openAiChatModel = openAiChatModel; // 通过构造器注入
        this.bodyNotesService = bodyNotesService;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new ChatWebSocketHandler(openAiChatModel, bodyNotesService), "/ws/chat")
                .setAllowedOrigins("*") // 允许所有源
                .addInterceptors(new HttpSessionHandshakeInterceptor());
    }
}
