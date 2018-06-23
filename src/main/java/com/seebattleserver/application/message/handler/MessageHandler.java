package com.seebattleserver.application.message.handler;

import com.seebattleserver.application.message.Message;

public interface MessageHandler {

    void handle(Message message);

}
