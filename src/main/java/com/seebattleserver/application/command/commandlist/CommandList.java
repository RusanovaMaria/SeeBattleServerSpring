package com.seebattleserver.application.command.commandlist;

import com.seebattleserver.application.command.Command;
import com.seebattleserver.application.command.HelpCommand;
import com.seebattleserver.application.command.PlayerInvitationCommand;
import com.seebattleserver.application.command.PlayerListCommand;
import com.seebattleserver.application.user.UserRegistry;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandList {
    private UserRegistry userRegistry;
    private Map<String, Command> commands;

    public CommandList(UserRegistry userRegistry) {
        commands = new HashMap();
        this.userRegistry = userRegistry;
        iniCommands();
    }

    private void iniCommands() {
        put("help", new HelpCommand());
        put("list", new PlayerListCommand(userRegistry));
        put("request", new PlayerInvitationCommand());
    }

    public void put(String commandWord, Command command) {
        commands.put(commandWord, command);
    }

    public void remove(String commandWord) {
        commands.remove(commandWord);
    }

    public Command getCommand(String commandWord) {
        return commands.get(commandWord);
    }
}
