package com.shanzhu.health.websocket;


import com.shanzhu.health.mapper.UserMapper;
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
    private final UserMapper userMapper;


    @Autowired
    public WebSocketConfig(wsOpenAiChatModel openAiChatModel, IBodyNotesService bodyNotesService, UserMapper userMapper) {
        this.openAiChatModel = openAiChatModel; // Inject through constructor
        this.bodyNotesService = bodyNotesService;
        this.userMapper = userMapper;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new ChatWebSocketHandler(openAiChatModel, bodyNotesService, userMapper), "/ws/chat")
                .setAllowedOrigins("*") // Allow all origins
                .addInterceptors(new HttpSessionHandshakeInterceptor());
    }
}
