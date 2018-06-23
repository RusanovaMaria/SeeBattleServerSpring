package com.seebattleserver.application.controller;

import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.invitation.AcceptInvitation;
import com.seebattleserver.application.invitation.Invitation;
import com.seebattleserver.application.invitation.NotAcceptInvitation;
import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.user.User;
import com.seebattleserver.service.sender.UserSender;
import com.seebattleserver.service.websocket.SocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvitationController implements Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(SocketHandler.class);
    private static final String YES = "yes";
    private static final String NO = "no";

    private User user;
    private UserSender userSender;
    private GameRegistry gameRegistry;

    public InvitationController(User user, UserSender userSender, GameRegistry gameRegistry) {
        this.user = user;
        this.userSender = userSender;
        this.gameRegistry = gameRegistry;
    }

    @Override
    public void handle(String answer) {
        if (isCorrectAnswer(answer)) {
            Invitation invitation = createInvitation(answer);
            invitation.handleAnswer();
        } else {
            notifyAboutMistake();
            LOGGER.warn("Ошибка ввода пользователя "+ user);
        }
    }

    private Invitation createInvitation(String answer) {
        switch (answer) {
            case YES:
                return new AcceptInvitation(user, userSender, gameRegistry);
            case NO:
                return new NotAcceptInvitation(user, userSender);
            default:
                throw new IllegalArgumentException("Введен неверный ответ");
        }
    }

    private boolean isCorrectAnswer(String answer) {
        if (answer.equals(YES) || answer.equals(NO)) {
            return true;
        }
        return false;
    }

    private void notifyAboutMistake() {
        userSender.sendMessage(user, new Message("Введен неверный ответ. Попробуйте еще раз"));
    }
}
