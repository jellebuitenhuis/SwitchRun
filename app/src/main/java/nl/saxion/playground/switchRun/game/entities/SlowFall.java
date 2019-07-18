package nl.saxion.playground.switchRun.game.entities;


import android.util.Log;

import nl.saxion.playground.switchRun.game.DimensionType;
import nl.saxion.playground.switchRun.game.Game;
import nl.saxion.playground.switchRun.game.level.LevelSegment;

@SuppressWarnings("WeakerAccess")
public class SlowFall extends PowerUp
{
    public SlowFall(Game game, LevelSegment segment, int tileX, int tileY) {
        super(game, segment, DimensionType.SlowFall, tileX, tileY);
        resource = game.getView().getResources().getIdentifier("fall", "drawable", game.getContext().getPackageName());

    }

    @Override
    public void usePowerUp(Player player)
    {
        super.usePowerUp(player);
        Log.v("PowerUp", "Slow Fall");
        Player.MAX_FALL_SPEED = 0.2f;
        player.grounded = false;
    }


    @Override
    public void tick() {
        super.tick();
        if (System.currentTimeMillis() > powerUpTime + POWER_UP_DURATION && using) {
            Player.MAX_FALL_SPEED = Player.STANDARD_MAX_FALL_SPEED;
            game.removeEntity(this);
        }
    }
}
