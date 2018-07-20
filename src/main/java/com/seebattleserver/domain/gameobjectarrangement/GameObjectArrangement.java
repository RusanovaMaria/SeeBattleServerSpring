package com.seebattleserver.domain.gameobjectarrangement;

import com.seebattleserver.domain.playingfield.PlayingField;

import java.util.List;
import java.util.Map;

public interface GameObjectArrangement {

    void arrangeGameObjects(Map<Integer, List<List<String>>> coordinates, PlayingField playingField);
}
