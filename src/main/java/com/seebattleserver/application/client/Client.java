package com.seebattleserver.application.client;

import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.domain.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketSession;
import com.seebattleserver.service.websocket.SocketHandler;

import java.io.IOException;

public class Client {

    private final static String DEFAULT_NAME = "";
    private Client opponent;
    private WebSocketSession session;
    private Player player;
    private UserStatus status;
    @Autowired
    private SocketHandler socketHandler = new SocketHandler();

    public Client(WebSocketSession session) {
        this.session = session;
        opponent = null;
        setStatus(UserStatus.FREE);
        createPlayer();
    }

    private void createPlayer() {
        player = new Player(DEFAULT_NAME);
    }

    public WebSocketSession getWebSocketSession() {
        return session;
    }

    public Player getPlayer() {
        return player;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getName() {

        return player.getName();
    }

    public void setName(String name) {
        player.setName(name);
    }

    public void setOpponent(Client opponent) {
        this.opponent = opponent;
    }

    public Client getOpponent() {
        return opponent;
    }

    public void removeOpponent() {
        opponent = null;
    }

    public boolean hasOpponent() {
        if (opponent != null) {
            return true;
        }
        return false;
    }

    public void sendMessage(String message) throws IOException {
        socketHandler.sendMessageInSession(session, message);
    }
}
