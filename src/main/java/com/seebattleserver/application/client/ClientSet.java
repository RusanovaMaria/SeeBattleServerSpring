package com.seebattleserver.application.client;

import com.seebattleserver.domain.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;

public class ClientSet {

    private final static List<Client> clients = new ArrayList();
    public void add(Client client) {
        clients.add(client);
    }
    public Client findClientByWebSocketSession(WebSocketSession session) {
        for (Client client : clients) {
            if (client.getWebSocketSession().equals(session)) {
                return client;
            }
        }
        throw new IllegalArgumentException("Клиент с таким параметром не обнаружен");
    }

    public Client findClientHandlerByPlayer(Player player) {
        for (Client client : clients) {
            if (client.getPlayer().equals(player)) {
                return client;
            }
        }
        throw new IllegalArgumentException("Клиент с таким параметром не обнаружен");
    }
    public List<Client> getClients() {
        return clients;
    }
}