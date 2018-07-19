package com.seebattleserver.application.json.jsonmessage.jsonmessagehandler;

import org.springframework.web.socket.TextMessage;

public interface JsonMessageHandler {

     String handle(TextMessage textMessage);
}
