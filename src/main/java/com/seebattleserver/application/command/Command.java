package com.seebattleserver.application.command;

import com.seebattleserver.application.client.ClientSet;

public abstract class Command {

    protected ClientSet clientSet = new ClientSet();

    public abstract void execute();

}
