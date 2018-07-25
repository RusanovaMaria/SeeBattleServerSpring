package com.seebattleserver.domain.gameobjectpositioncontroller;

import com.seebattleserver.domain.rule.Rule;

public interface GameObjectPositionController {

    void control();

    Rule getRule();
}
