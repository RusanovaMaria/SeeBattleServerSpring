package com.seebattleserver.application.controller;

import com.google.gson.Gson;
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
import org.springframework.web.socket.TextMessage;

import java.util.ArrayList;
import java.util.List;

public class InvitationResponseController implements Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketHandler.class);
    private static final String YES = "yes";
    private static final String NO = "no";

    private User user;
    private GameRegistry gameRegistry;
    private Gson gson;
    private UserSender userSender;

    public InvitationResponseController(User user, GameRegistry gameRegistry,
                                        Gson gson, UserSender userSender) {
        this.user = user;
        this.gameRegistry = gameRegistry;
        this.gson = gson;
        this.userSender = userSender;
    }

    @Override
    public void handle(String answer) {
        if (isCorrectAnswer(answer)) {
            Invitation invitation = createInvitation(answer);
            invitation.handleAnswer();
        } else {
            makeUserMistakeResponse();
        }
    }

    private boolean isCorrectAnswer(String answer) {
        if (answer.equals(YES) || answer.equals(NO)) {
            return true;
        }
        return false;
    }

    private Invitation createInvitation(String answer) {
        switch (answer) {
            case YES:
                return new AcceptInvitation(user, gameRegistry, userSender);
            case NO:
                return new NotAcceptInvitation(user, userSender);
            default:
                LOGGER.error("Введен неверный ответ");
                throw new IllegalArgumentException("Введен неверный ответ");
        }
    }

    private void makeUserMistakeResponse() {
        userSender.sendMessage(user, new Message("Введен неверный ответ. Попробуйте еще раз"));
    }
}
