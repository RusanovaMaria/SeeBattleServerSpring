package com.seebattleserver.service.websocket.registry;

import com.seebattleserver.application.user.User;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

@Component
public class SessionRegistry {

    private Map<WebSocketSession, User> userMap;

    public SessionRegistry() {
        this.userMap = new HashMap<>();
    }

    public void put(WebSocketSession session, User user) {
        userMap.put(session, user);
    }

    public void remove(WebSocketSession session) {
        userMap.remove(session);
    }

    public User getUser(WebSocketSession session) {
        return userMap.get(session);
    }

    public WebSocketSession getSession(User user) {
        for (Map.Entry<WebSocketSession, User> entry : userMap.entrySet()) {
            if (user.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException("Не существует сессии для этого user");
    }

    public boolean containsSession(WebSocketSession session) {
        return userMap.containsValue(session);
    }

}
