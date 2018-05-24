package com.seebattleserver.application.controller;

import com.seebattleserver.application.client.Client;
import com.seebattleserver.application.command.Command;
import com.seebattleserver.application.command.HelpCommand;
import com.seebattleserver.application.command.PlayerInvitationCommand;
import com.seebattleserver.application.command.PlayerListCommand;

public class CommandController implements Controller {

    private final String[] commands = {"help", "list", "request"};
    private Client client;

    public CommandController(Client client) {
       this.client = client;
    }

    @Override
    public void handle(String command) {
        if (isRightCommand(command)) {
            handleCommand(command);
        } else {
            Command helpCommand = new HelpCommand(client);
            helpCommand.execute();
        }
    }

    private boolean isRightCommand(String command) {
        for (int i = 0; i < commands.length; i++) {
            if (commands[i].equals(command)) {
                return true;
            }
        }
        return false;
    }

    private void handleCommand(String command) {
        switch (command) {
            case "help":
                Command helpCommand = new HelpCommand(client);
                helpCommand.execute();
                break;
            case "list":
                Command listCommand = new PlayerListCommand(client);
                listCommand.execute();
                break;
            case "request":
                Command requestCommand = new PlayerInvitationCommand(client);
                requestCommand.execute();
                break;

            default:
                throw new IllegalArgumentException("Данного запроса не существует");
        }
    }
}