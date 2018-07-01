package com.seebattleserver.domain.player;

import com.seebattleserver.domain.playingfield.PlayingField;

public class Player {

    private PlayingField playingField;

    public PlayingField getPlayingField() {
        return playingField;
    }

    public void setPlayingField(PlayingField playingField) {
        this.playingField = playingField;
    }
}
