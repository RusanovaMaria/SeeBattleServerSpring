package com.seebattleserver.application.controller.invitationresponsecontroller.invitation;

import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.domain.game.ClassicGame;
import com.seebattleserver.domain.game.Game;
import com.seebattleserver.application.gameobjectposition.GameObjectPosition;
import com.seebattleserver.application.gameobjectposition.StandardGameObjectPosition;
import com.seebattleserver.domain.player.Player;
import com.seebattleserver.domain.playingfield.ClassicPlayingField;
import com.seebattleserver.domain.playingfield.PlayingField;
import com.seebattleserver.service.sender.UserSender;

public class AcceptInvitation implements Invitation {
    private User user;
    private GameRegistry gameRegistry;
    private UserSender userSender;
    private User userOpponent;

    public AcceptInvitation(User user, GameRegistry gameRegistry,
                            UserSender userSender) {
        this.user = user;
        this.gameRegistry = gameRegistry;
        this.userSender = userSender;
        userOpponent = user.getUserOpponent();
    }

    @Override
    public void handleAnswer() {
        if (userIsNotInGame(userOpponent)) {
            sendAnswer();
            makeUsersReadyForGame();
            notifyUsersAboutGameObjectTypeChoice();
        } else {
            notifyAboutUserOpponentInGame();
            setUserFree();
        }
    }

    private boolean userIsNotInGame(User user) {
        if ((user.getUserStatus() != UserStatus.IN_GAME) &&
                (user.getUserStatus() != UserStatus.IN_GAME_MOVE)) {
            return true;
        }
        return false;
    }

    private void sendAnswer() {
        userSender.sendMessage(userOpponent, new Message("Игрок " + user.getUsername() + " принял ваше предложение"));
    }

    private void makeUsersReadyForGame() {
        user.setUserStatus(UserStatus.READY_FOR_GAME);
        userOpponent.setUserStatus(UserStatus.READY_FOR_GAME);
    }

    private void notifyUsersAboutGameObjectTypeChoice() {
        userSender.sendMessage(user, new Message("Выберите тип расстановки игровых объектов\n"+
        "standard - стандартный тип расстановки\n"+
        "user - пользовательский тип расстановки"));
        userSender.sendMessage(userOpponent, new Message("Выберите тип расстановки игровых объектов\n"+
                "standard - стандартный тип расстановки\n"+
                "user - пользовательский тип расстановки"));
    }

    private void notifyAboutUserOpponentInGame() {
        userSender.sendMessage(user, new Message("Пользователь"
                +userOpponent.getUsername()+ "уже начал игру"));
    }

    private void setUserFree() {
        user.setUserStatus(UserStatus.FREE);
        user.setUserOpponent(null);
    }
}
