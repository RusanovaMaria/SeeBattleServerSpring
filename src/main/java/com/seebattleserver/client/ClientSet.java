package com.seebattleserver.application.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope("singleton")
public class ClientSet {

    @Autowired
    private ArrayList<Client> clients;

    public void add(Client client) {

        clients.add(client);
    }

    public Client findClientByWebSockeSession(WebSocketSession session) {

        for (Client client : clients) {

            if (client.getWebSocketSession().equals(session)) {
                return client;
            }
        }

        throw new IllegalArgumentException("Клиент с таким параметром не обнаружен");
    }

    public Client findClientHandlerByPlayer(domain.player.Player player) {

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
