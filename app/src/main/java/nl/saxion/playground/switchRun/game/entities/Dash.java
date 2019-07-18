package nl.saxion.playground.switchRun.game.entities;


import android.util.Log;

import nl.saxion.playground.switchRun.game.DimensionType;
import nl.saxion.playground.switchRun.game.Game;
import nl.saxion.playground.switchRun.game.level.LevelSegment;

@SuppressWarnings("WeakerAccess")
public class Dash extends PowerUp
{

    public float oldSpeed;
    private static final float DECAY_SPEED = 0.9f;

    public Dash(Game game, LevelSegment segment, int tileX, int tileY) {
        super(game, segment, DimensionType.Dash, tileX, tileY);
        resource = game.getView().getResources().getIdentifier("dash", "drawable", game.getContext().getPackageName());
    }

    @Override
    public void usePowerUp(Player player)
    {
        super.usePowerUp(player);
        Log.v("PowerUp", "Dash");
        this.oldSpeed = player.velocity.x;
        player.position.x += 10;
        Player.MAX_RUN_VELOCITY = 10f;
        player.velocity.x += .5f;

    }


    @Override
    public void tick() {
        super.tick();
        if (using) {
            if (System.currentTimeMillis() > powerUpTime + POWER_UP_DURATION) {
                player.velocity.x = Math.max(player.velocity.x *= DECAY_SPEED, oldSpeed);
            }
            if (player.velocity.x <= oldSpeed) {
                Player.MAX_RUN_VELOCITY = Player.STANDARD_MAX_RUN_VELOCITY;
                game.removeEntity(this);
            }
        }
    }
}
