package com.seebattleserver.application.controller.gameprocesscontroller.gameendhandler;

import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.domain.game.Game;
import com.seebattleserver.domain.player.Player;
import com.seebattleserver.service.sender.UserSender;

public class GameEndHandler {
    private User firstUser;
    private User secondUser;
    private Game game;
    private GameRegistry gameRegistry;
    private UserSender userSender;

    public GameEndHandler(User firstUser, User secondUser, Game game, GameRegistry gameRegistry, UserSender userSender) {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.game = game;
        this.gameRegistry = gameRegistry;
        this.userSender = userSender;
    }

    public void endGame() {
        User winner = determineWinner();
        User looser = winner.getUserOpponent();
        notifyAboutGameEnd(winner, looser);
        endGameProcessForUsers();
        removeGame();
    }

    private void notifyAboutGameEnd(User winner, User looser) {
        userSender.sendMessage(winner, new Message("Игра окончена. Вы выиграли!"));
        userSender.sendMessage(looser, new Message("Игра окончена. Вы проиграли"));
    }

    private User determineWinner() {
        Player winner = game.determineWinner();
        if (firstUser.getPlayer().equals(winner)) {
            return firstUser;
        } else {
            return secondUser;
        }
    }

    private void endGameProcessForUsers() {
        firstUser.setUserStatus(UserStatus.FREE);
        firstUser.setUserOpponent(null);
        secondUser.setUserStatus(UserStatus.FREE);
        secondUser.setUserOpponent(null);
    }

    private void removeGame() {
        gameRegistry.remove(firstUser, game);
        gameRegistry.remove(secondUser, game);
    }
}
