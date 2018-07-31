package com.seebattleserver.application.controller.invitationresponsecontroller;

import com.seebattleserver.application.controller.Controller;
import com.seebattleserver.application.controller.invitationresponsecontroller.invitationhandler.AcceptInvitationHandler;
import com.seebattleserver.application.controller.invitationresponsecontroller.invitationhandler.InvitationHandler;
import com.seebattleserver.application.controller.invitationresponsecontroller.invitationhandler.NotAcceptInvitationHandler;
import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.json.jsonmessage.JsonMessage;
import com.seebattleserver.application.json.jsonmessage.jsonmessagehandler.DefaultJsonMessageHandler;
import com.seebattleserver.application.json.jsonmessage.jsonmessagehandler.JsonMessageHandler;
import com.seebattleserver.application.user.User;
import com.seebattleserver.service.sender.UserSender;
import com.seebattleserver.service.websocket.SocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;

public class InvitationResponseController implements Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketHandler.class);
    private static final String YES = "yes";
    private static final String NO = "no";
    private User user;
    private GameRegistry gameRegistry;
    private UserSender userSender;

    public InvitationResponseController(User user, GameRegistry gameRegistry,
                                        UserSender userSender) {
        this.user = user;
        this.gameRegistry = gameRegistry;
        this.userSender = userSender;
    }

    @Override
    public void handle(TextMessage textMessage) {
        JsonMessageHandler defaultJsonMessageHandler = new DefaultJsonMessageHandler();
        String answer = defaultJsonMessageHandler.handle(textMessage);
        if (isCorrectAnswer(answer)) {
            InvitationHandler invitationHandler = createInvitation(answer);
            invitationHandler.handleAnswer();
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

    private InvitationHandler createInvitation(String answer) {
        switch (answer) {
            case YES:
                return new AcceptInvitationHandler(user, userSender);
            case NO:
                return new NotAcceptInvitationHandler(user, userSender);
            default:
                LOGGER.error("Введен неверный ответ");
                throw new IllegalArgumentException("Введен неверный ответ");
        }
    }

    private void makeUserMistakeResponse() {
        userSender.sendMessage(user, new JsonMessage("Введен неверный ответ. Попробуйте еще раз"));
    }
}
