package com.seebattleserver.service.message.handler;

import com.seebattleserver.service.message.Message;

public interface MessageHandler {

    void handle(Message message);

}
