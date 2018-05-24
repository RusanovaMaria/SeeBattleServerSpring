package com.seebattleserver.application.gameimplementation;

import com.seebattleserver.application.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope("singleton")
public class GameImplementationSet {

    @Autowired
    public GameImplementationSet() {

    }

    private static final List<GameImplementation> gameImplementations = new ArrayList();

    public void add(GameImplementation gameImplementation) {
        gameImplementations.add(gameImplementation);
    }

    public void remove(GameImplementation gameImplementation) {
        gameImplementations.remove(gameImplementation);
    }
    public GameImplementation findGameImplementationByClient(Client client) {
        for (int i = 0; i < gameImplementations.size(); i++) {
            GameImplementation gameImplementation = gameImplementations.get(i);
            if (isClientInGame(gameImplementation, client)) {
                return gameImplementation;
            }
        }
        throw new IllegalArgumentException("Клиент в игровом прострастве не обнаружен");
    }

    private boolean isClientInGame(GameImplementation gameImplementation, Client client) {
        if (gameImplementation.isClientInGame(client)) {
            return true;
        }
        return false;
    }
}
