package com.seebattleserver.application.invitation;

import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.domain.game.ClassicGame;
import com.seebattleserver.domain.game.Game;
import com.seebattleserver.domain.player.Player;

import java.util.ArrayList;
import java.util.List;

public class AcceptInvitation implements Invitation {
    private User user;
    private User userOpponent;
    private GameRegistry gameRegistry;
    private List<Message> response;

    public AcceptInvitation(User user, GameRegistry gameRegistry) {
        this.user = user;
        this.gameRegistry = gameRegistry;
        response = new ArrayList<>();
        userOpponent = user.getOpponent();
    }

    @Override
    public List<Message> handleAnswer() {
        acceptInvitation();
        return response;
    }

    private void acceptInvitation() {
        changeUserStatuses();
        makeResponse();
        startGame();
    }

    private void changeUserStatuses() {
        user.setUserStatus(UserStatus.IN_GAME);
        userOpponent.setUserStatus(UserStatus.IN_GAME_MOVE);
    }

    private void makeResponse() {
        response.add(new Message("Игрок " + user.getUsername() + " принял ваше предложение", userOpponent));
        response.add(new Message("Введите координаты х и у", userOpponent));
        response.add(new Message("Игра началась.", user));
    }

    private void startGame() {
        Player firstPlayer = new Player();
        Player secondPlayer = new Player();
        Game game = new ClassicGame(firstPlayer, secondPlayer);

        associateUserWithPlayer(userOpponent, firstPlayer);
        associateUserWithPlayer(user, firstPlayer);

        putGameToGameRegistry(game);
    }

    private void associateUserWithPlayer(User user, Player player) {
        user.setPlayer(player);
    }

    private void putGameToGameRegistry(Game game) {
        gameRegistry.put(userOpponent, game);
        gameRegistry.put(user, game);
    }
}
