package com.seebattleserver.domain.gameobjectaddition;

import com.seebattleserver.domain.gameobject.GameObject;
import com.seebattleserver.domain.playingfield.PlayingField;

import java.util.List;
import java.util.Map;

public interface GameObjectAddition {

    Map<Integer, List<GameObject>> add(PlayingField playingField);
}
