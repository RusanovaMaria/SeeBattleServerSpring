package com.seebattleserver.domain.gameobjectpositioncontroller;

import com.seebattleserver.domain.coordinatecouple.CoordinatesCouple;
import com.seebattleserver.domain.playingfield.PlayingField;
import com.seebattleserver.domain.rule.Rule;

import java.util.List;

public interface GameObjectInstallationController {

    boolean gameObjectCanBeInstalled(List<CoordinatesCouple> shipCoordinates, PlayingField playingField);

    Rule getRule();
}
