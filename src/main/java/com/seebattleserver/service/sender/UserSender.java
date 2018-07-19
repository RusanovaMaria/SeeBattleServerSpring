package com.seebattleserver.service.sender;

import com.seebattleserver.application.json.jsonmessage.JsonMessage;
import com.seebattleserver.application.user.User;
import org.springframework.stereotype.Component;

@Component
public interface UserSender {

    void sendMessage(User user, JsonMessage jsonMessage);

}
