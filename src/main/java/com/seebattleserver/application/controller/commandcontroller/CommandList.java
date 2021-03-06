package com.seebattleserver.application.controller.commandcontroller;

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
    private String defaultCommandWord;

    public CommandList(UserRegistry userRegistry) {
        commands = new HashMap();
        this.userRegistry = userRegistry;
        defaultCommandWord = "help";
        iniCommands();
    }

    private void iniCommands() {
        put("help", new HelpCommand());
        put("list", new PlayerListCommand(userRegistry));
        put("invite", new PlayerInvitationCommand());
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

    public Command getDefaultCommand() {
        return commands.get(defaultCommandWord);
    }

    public void setDefaultCommand(String defaultCommandWord, Command defaultCommand) {
        this.defaultCommandWord = defaultCommandWord;
        if (!commands.containsKey(defaultCommand)) {
            commands.put(defaultCommandWord, defaultCommand);
        }
    }
}
