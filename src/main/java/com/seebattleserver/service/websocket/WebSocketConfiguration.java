package com.seebattleserver.service.websocket;

import com.google.gson.Gson;
import com.seebattleserver.application.user.User;
import com.seebattleserver.service.sender.UserSender;
import com.seebattleserver.service.sender.WebSocketUserSender;
import com.seebattleserver.service.websocket.registry.SessionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    private Gson gson;
    private SessionRegistry sessionRegistry;

    @Autowired
    public WebSocketConfiguration(SessionRegistry sessionRegistry, Gson gson) {
        this.gson = gson;
        this.sessionRegistry = sessionRegistry;
    }

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new SocketHandler(sessionRegistry, gson), "/game");
    }

    @Bean
    public UserSender userSender() {
        return new WebSocketUserSender(sessionRegistry, gson);
    }

}
