package com.seebattleserver.service.websocket;

import com.google.gson.Gson;
import com.seebattleserver.application.user.UserRegistry;
import com.seebattleserver.service.sender.UserSender;
import com.seebattleserver.service.sender.WebSocketUserSender;
import com.seebattleserver.service.websocket.registry.SessionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    private Gson gson;
    private SessionRegistry sessionRegistry;
    private SocketHandler socketHandler;

    @Autowired
    public WebSocketConfiguration(SessionRegistry sessionRegistry, Gson gson, SocketHandler socketHandler) {
        this.sessionRegistry = sessionRegistry;
        this.gson = gson;
        this.socketHandler = socketHandler;
    }

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(socketHandler, "/game");
    }
}
