package com.seebattleserver.service.sender;

import com.seebattleserver.application.user.User;
import com.seebattleserver.application.message.Message;
import org.springframework.stereotype.Component;

@Component
public interface UserSender {

    void sendMessage(User user, Message message);

}
