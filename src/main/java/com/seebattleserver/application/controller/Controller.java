package com.seebattleserver.application.controller;

import org.springframework.web.socket.TextMessage;

public interface Controller {

    void handle(String message);
}
