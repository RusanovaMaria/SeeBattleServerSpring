package com.seebattleserver.configuration;

import com.google.gson.Gson;
import com.seebattleserver.service.sender.UserSender;
import com.seebattleserver.service.sender.WebSocketUserSender;
import com.seebattleserver.service.websocket.registry.SessionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SenderConfiguration {

    private SessionRegistry sessionRegistry;
    private Gson gson;

    @Autowired
    public SenderConfiguration(SessionRegistry sessionRegistry, Gson gson) {
        this.sessionRegistry = sessionRegistry;
        this.gson = gson;
    }

    @Bean
    public UserSender userSender() {
        return new WebSocketUserSender(sessionRegistry, gson);
    }
}
