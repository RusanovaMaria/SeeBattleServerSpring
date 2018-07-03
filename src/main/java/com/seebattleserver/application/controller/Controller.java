package com.seebattleserver.application.controller;

import com.seebattleserver.application.message.Message;
import org.springframework.web.socket.TextMessage;

import java.util.List;

public interface Controller {

    List<Message> handle(TextMessage text);
}
