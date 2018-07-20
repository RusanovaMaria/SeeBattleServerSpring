package com.seebattleserver.application.controller.invitationresponsecontroller.invitationhandler;

import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.json.jsonmessage.JsonMessage;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.domain.playingfield.ClassicPlayingField;
import com.seebattleserver.service.sender.UserSender;

public class AcceptInvitationHandler implements InvitationHandler {
    private User user;
    private GameRegistry gameRegistry;
    private UserSender userSender;
    private User userOpponent;

    public AcceptInvitationHandler(User user, GameRegistry gameRegistry,
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
        userSender.sendMessage(userOpponent, new JsonMessage("Игрок " + user.getUsername() + " принял ваше предложение"));
    }

    private void makeUsersReadyForGame() {
        user.setUserStatus(UserStatus.READY_FOR_GAME);
        userOpponent.setUserStatus(UserStatus.READY_FOR_GAME);
        assignGameFields();
    }

    private void assignGameFields() {
        user.getPlayer().setPlayingField(new ClassicPlayingField());
        userOpponent.getPlayer().setPlayingField(new ClassicPlayingField());
    }

    private void notifyUsersAboutGameObjectTypeChoice() {
        userSender.sendMessage(user, new JsonMessage("Выберите тип расстановки игровых объектов\n" +
                "default - стандартный тип расстановки\n" +
                "user - пользовательский тип расстановки"));
        userSender.sendMessage(userOpponent, new JsonMessage("Выберите тип расстановки игровых объектов\n" +
                "default - стандартный тип расстановки\n" +
                "user - пользовательский тип расстановки"));
    }

    private void notifyAboutUserOpponentInGame() {
        userSender.sendMessage(user, new JsonMessage("Пользователь"
                + userOpponent.getUsername() + "уже начал игру"));
    }

    private void setUserFree() {
        user.setUserStatus(UserStatus.FREE);
        user.setUserOpponent(null);
    }
}
