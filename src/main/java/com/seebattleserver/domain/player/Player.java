package domain.player;

import domain.playingfield.ClassicPlayingField;

public class Player {

    private String name;
    private ClassicPlayingField playingField;

    public Player(String name) {

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
