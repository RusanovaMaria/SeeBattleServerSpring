package com.seebattleserver.application.controller;

import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.invitation.AcceptInvitation;
import com.seebattleserver.application.invitation.Invitation;
import com.seebattleserver.application.invitation.NotAcceptInvitation;
import com.seebattleserver.application.user.User;
import com.seebattleserver.service.sender.UserSender;

public class InvitationController implements Controller {

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
        switch (answer) {
            case "yes":
                Invitation acceptInvitation = new AcceptInvitation(user, userSender, gameRegistry);
                acceptInvitation.handleAnswer();
                break;
                case "no":
                    Invitation notAcceptInvitation = new NotAcceptInvitation(user, userSender);
                    notAcceptInvitation.handleAnswer();
                    break;
                    default:
                        throw new IllegalArgumentException("Введен неверный ответ");
        }
    }
}
