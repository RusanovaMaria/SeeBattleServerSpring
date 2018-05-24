package com.seebattleserver.application.controller;

import com.seebattleserver.application.client.Client;
import com.seebattleserver.application.invitation.AcceptInvitation;
import com.seebattleserver.application.invitation.Invitation;
import com.seebattleserver.application.invitation.NotAcceptInvitation;

public class InvitationController implements Controller {

    private Client client;

    public InvitationController(Client client) {
        this.client = client;
    }

    @Override
    public void handle(String answer) {
        switch (answer) {
            case "yes":
                Invitation acceptInvitation = new AcceptInvitation(client);
                acceptInvitation.handleAnswer();
                break;
                case "no":
                    Invitation notAcceptInvitation = new NotAcceptInvitation(client);
                    notAcceptInvitation.handleAnswer();
                    break;
                    default:
                        throw new IllegalArgumentException("Введен неверный ответ");
        }
    }
}
