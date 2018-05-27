package com.seebattleserver.service.sender;

import com.seebattleserver.application.user.User;
import com.seebattleserver.service.message.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public interface UserSender {

    void sendMessage(User user, Message message);

}
