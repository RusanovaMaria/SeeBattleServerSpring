package com.seebattleserver.application.client;

import domain.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketSession;
import service.websocket.ClientHandler;

import java.io.IOException;

public class Client {

    private final static String DEFAULT_NAME = "";
    private Client opponent;
    private WebSocketSession session;
    private Player player;
    private ClientStatus status;
    @Autowired
    private ClientHandler clientHandler;

    public Client(WebSocketSession session) {
        this.session = session;
        opponent = null;
        setStatus(ClientStatus.FREE);
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

    public ClientStatus getStatus() {
        return status;
    }

    public void setStatus(ClientStatus status) {
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
        clientHandler.sendMessageInSession(session, message);
    }

   // public String readMessage(TextMessage )
}
