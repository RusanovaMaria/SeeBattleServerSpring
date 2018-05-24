package com.seebattleserver.application.controller;

import com.seebattleserver.application.client.Client;
import com.seebattleserver.application.client.ClientSet;
import com.seebattleserver.application.gameimplementation.GameImplementation;
import com.seebattleserver.application.gameimplementation.GameImplementationSet;
import com.seebattleserver.domain.game.Game;
import com.seebattleserver.domain.game.Result;

import java.io.IOException;

public class GameController implements Controller {
    private Client client;
    private ClientSet clientSet = new ClientSet();
    private GameImplementationSet gameImplementationSet = new GameImplementationSet();

    public GameController(Client client) {
        this.client = client;
    }

    @Override
    public void handle(String message) {
        GameImplementation gameImplementation = gameImplementationSet.findGameImplementationByClient(client);
        if (gameImplementation.isClientMove(client)) {
            String coordinates = message.trim();
            makeMove(client, gameImplementation, coordinates);
        } else {
              notifyAboutMistake();
        }
    }

    private int getX(String coordinates) {
        int x = Integer.parseInt(coordinates.substring(0, 1));
        return x;
    }

    private char getY(String coordinates) {
        char y = coordinates.charAt(1);
        return y;
    }

    private void makeMove(Client client, GameImplementation gameImplementation, String coordinates) {
        int x = getX(coordinates);
        char y = getY(coordinates);

        try {
            Game game = gameImplementation.getGame();
            Result result = game.fire(client.getPlayer(), x, y);
            getAnswerByResult(result);
            gameImplementation.passMove(client);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void getAnswerByResult (Result result) throws IOException {
        switch (result) {
            case MISSED:
                client.sendMessage("Мимо");
                break;
            case REPEATED:
                client.sendMessage("Вы уже стреляли в эту клетку");
                break;
            case GOT:
                client.sendMessage("Попадание");
                break;
            case KILLED:
                client.sendMessage("Убит");
        }
    }

    private void notifyAboutMistake() {
        try {
            client.sendMessage("Сейчас не ваш ход");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
