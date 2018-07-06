package com.seebattleserver.application.responsesender;

import com.seebattleserver.application.message.Message;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ResponseSender {

    void sendResponse(List<Message> response);
}
