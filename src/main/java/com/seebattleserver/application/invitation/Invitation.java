package com.seebattleserver.application.invitation;

import com.seebattleserver.application.message.Message;

import java.util.List;

public interface Invitation {

    List<Message> handleAnswer();

}
