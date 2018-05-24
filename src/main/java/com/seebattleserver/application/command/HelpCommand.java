package com.seebattleserver.application.command;

import com.seebattleserver.application.client.Client;

import java.io.IOException;

public class HelpCommand extends Command {

    private Client client;

    public HelpCommand(Client client) {
        this.client = client;
    }

    @Override
    public void execute() {
        try {
            writeCommandList();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void writeCommandList() throws IOException {
        client.sendMessage("Список команд");
        client.sendMessage("help - помощь");
        client.sendMessage("list - список игроков");
        client.sendMessage("request - отправить запрос на вступление в игру");
    }
}