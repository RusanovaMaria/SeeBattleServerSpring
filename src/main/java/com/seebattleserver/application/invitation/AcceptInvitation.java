package com.seebattleserver.application.invitation;

import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.domain.game.ClassicGame;
import com.seebattleserver.domain.game.Game;
import com.seebattleserver.domain.gameobjectposition.GameObjectPosition;
import com.seebattleserver.domain.gameobjectposition.StandardGameObjectPosition;
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
            startGameProcessForUsers();
            startGame();
            notifyAboutGameStart();
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

    private void startGameProcessForUsers() {
        user.setUserStatus(UserStatus.IN_GAME);
        userOpponent.setUserStatus(UserStatus.IN_GAME_MOVE);
    }

    private void notifyAboutGameStart() {
        userSender.sendMessage(userOpponent, new Message("Введите координаты х и у"));
        userSender.sendMessage(user, new Message("Игра началась."));
    }


    private void startGame() {
        Player firstPlayer = new Player();
        Player secondPlayer = new Player();
        PlayingField firstPlayingField = new ClassicPlayingField();
        PlayingField secondPlayingField = new ClassicPlayingField();
        GameObjectPosition position;
        position= new StandardGameObjectPosition(firstPlayingField);
        position.establish();
        position = new StandardGameObjectPosition(secondPlayingField);
        position.establish();
        firstPlayer.setPlayingField(firstPlayingField);
        secondPlayer.setPlayingField(secondPlayingField);
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

    private void notifyAboutUserOpponentInGame() {
        userSender.sendMessage(user, new Message("Пользователь"
                +userOpponent.getUsername()+ "уже начал игру"));
    }

    private void setUserFree() {
        user.setUserStatus(UserStatus.FREE);
        user.setUserOpponent(null);
    }
}
