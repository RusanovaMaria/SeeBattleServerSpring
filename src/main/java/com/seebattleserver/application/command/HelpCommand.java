package com.seebattleserver.application.command;

import org.springframework.stereotype.Controller;

@Controller
public class HelpCommand implements Command {

    @Override
    public String execute() {
        return "Список команд\n"
                + "help - помощь\n"
                + "list - список игроков\n"
                + "request - отправить запрос на вступление в игру";
    }

}