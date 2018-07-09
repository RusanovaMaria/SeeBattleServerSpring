package com.seebattleserver.application.controller.gamestartcontroller.gamestart;

import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.domain.game.ClassicGame;
import com.seebattleserver.domain.game.Game;
import com.seebattleserver.service.sender.UserSender;

public class ClassicGameStart implements GameStart {
    private User firstUser;
    private User secondUser;
    private GameRegistry gameRegistry;
    private UserSender userSender;

    public ClassicGameStart(User firstUser, User secondUser,
                            GameRegistry gameRegistry, UserSender userSender) {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.gameRegistry = gameRegistry;
        this.userSender = userSender;
    }

    public void start() {
        Game game = new ClassicGame(firstUser.getPlayer(), secondUser.getPlayer());
        gameRegistry.put(firstUser, game);
        gameRegistry.put(secondUser, game);
        notifyAboutGameStart();
    }

    private void notifyAboutGameStart() {
        if (firstUser.getUserStatus() == UserStatus.IN_GAME_MOVE) {
            userSender.sendMessage(firstUser, new Message("Игра началась. Введите х и у"));
            userSender.sendMessage(secondUser, new Message("Игра началась. Дождитесь хода соперника"));
        } else {
            userSender.sendMessage(secondUser, new Message("Игра началась. Введите х и у"));
            userSender.sendMessage(firstUser, new Message("Игра началась. Дождитесь хода соперника"));
        }
    }
}
