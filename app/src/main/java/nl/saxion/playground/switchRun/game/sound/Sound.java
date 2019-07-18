package nl.saxion.playground.switchRun.game.sound;

import nl.saxion.playground.switchRun.R;

public enum Sound {
    Background(-1),
    Jump(R.raw.sound_jump),
    SwapDimension(-1),
    WallBump(-1),
    PickupPowerup(R.raw.sound_pickup),
    Death(-1);

    private int resource;
    Sound(int resource) {
        this.resource = resource;
    }

    public int getResource() {
        return resource;
    }
}
