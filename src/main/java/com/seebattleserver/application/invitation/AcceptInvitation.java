package com.seebattleserver.application.invitation;

import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.domain.game.ClassicGame;
import com.seebattleserver.domain.game.Game;
import com.seebattleserver.domain.player.Player;
import com.seebattleserver.service.sender.UserSender;

public class AcceptInvitation implements Invitation {

    private User user;
    private UserSender userSender;
    private GameRegistry gameRegistry;

    public AcceptInvitation(User user, UserSender userSender, GameRegistry gameRegistry) {
        this.user = user;
        this.userSender = userSender;
        this.gameRegistry = gameRegistry;
    }

    @Override
    public void handleAnswer() {
        acceptInvitation();
    }

    private void acceptInvitation() {
        User userOpponent = user.getOpponent();
        notifyOpponent(userOpponent);
        changeUserStatuses(user, userOpponent);
        startGame(user, userOpponent);
    }

    private void notifyOpponent(User userOpponent) {
        userSender.sendMessage(userOpponent, new Message("Игрок " + user.getUsername() + " принял ваше предложение"));
        userSender.sendMessage(userOpponent, new Message("Введите координаты х и у"));
    }

    private void changeUserStatuses(User user, User userOpponent) {
        user.setUserStatus(UserStatus.IN_GAME);
        userOpponent.setUserStatus(UserStatus.IN_GAME_MOVE);
    }

    private void startGame(User user, User userOpponent) {
        Player firstPlayer = new Player();
        Player secondPlayer = new Player();
        Game game = new ClassicGame(firstPlayer, secondPlayer);

        associateUserWithPlayer(userOpponent, firstPlayer);
        associateUserWithPlayer(user, firstPlayer);

        putGameToGameRegistry(user, userOpponent, game);
    }

    private void associateUserWithPlayer(User user, Player player) {
        user.setPlayer(player);
    }

    private void putGameToGameRegistry(User user, User userOpponent, Game game) {
        gameRegistry.put(userOpponent, game);
        gameRegistry.put(user, game);
    }
}
