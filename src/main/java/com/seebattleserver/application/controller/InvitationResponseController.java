package com.seebattleserver.application.controller;

import com.google.gson.Gson;
import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.invitation.AcceptInvitation;
import com.seebattleserver.application.invitation.Invitation;
import com.seebattleserver.application.invitation.NotAcceptInvitation;
import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.user.User;
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
    private List<Message> response;

    public InvitationResponseController(User user, GameRegistry gameRegistry, Gson gson) {
        this.user = user;
        this.gameRegistry = gameRegistry;
        this.gson = gson;
        response = new ArrayList<>();
    }

    @Override
    public List<Message> handle(TextMessage text) {
        String answer = getMessage(text);
        if (isCorrectAnswer(answer)) {
            Invitation invitation = createInvitation(answer);
            response = invitation.handleAnswer();
        } else {
           makeUserMistakeResponse();
        }
        return response;
    }

    private String getMessage(TextMessage text) {
        Message message = gson.fromJson(text.getPayload(), Message.class);
        String answer = message.getContent().trim();
        return answer;
    }

    private Invitation createInvitation(String answer) {
        switch (answer) {
            case YES:
                return new AcceptInvitation(user, gameRegistry);
            case NO:
                return new NotAcceptInvitation(user);
            default:
                LOGGER.error("Введен неверный ответ");
                throw new IllegalArgumentException("Введен неверный ответ");
        }
    }

    private boolean isCorrectAnswer(String answer) {
        if (answer.equals(YES) || answer.equals(NO)) {
            return true;
        }
        return false;
    }

    private void makeUserMistakeResponse() {
        response.add(new Message("Введен неверный ответ. Попробуйте еще раз", user));
    }
}
