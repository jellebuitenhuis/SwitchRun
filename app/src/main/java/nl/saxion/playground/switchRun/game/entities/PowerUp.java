package nl.saxion.playground.switchRun.game.entities;


import nl.saxion.playground.switchRun.game.DimensionType;
import nl.saxion.playground.switchRun.game.Game;
import nl.saxion.playground.switchRun.game.collision.CollisionDirection;
import nl.saxion.playground.switchRun.game.level.LevelSegment;
import nl.saxion.playground.switchRun.game.level.Tile;

@SuppressWarnings("WeakerAccess")
public abstract class PowerUp extends Tile {

    protected Player player;
    protected long powerUpTime = 0;
    protected static final long POWER_UP_DURATION = 1000;
    protected Boolean using = false;


    public PowerUp(Game game, LevelSegment segment, DimensionType type, int tileX, int tileY) {
        super(game, segment, type, tileX, tileY);
    }

    public void usePowerUp(Player player)
    {
        this.player = player;
        this.using = true;
        powerUpTime = System.currentTimeMillis();
    }

    @Override
    public void onCollisionWith(GameEntity other, CollisionDirection direction) {

    }

    @Override
    public boolean isCollidableWith(GameEntity other) {
        return false;
    }

    @Override
    public boolean isMovable() {
        return false;
    }

    @Override
    public boolean isAffectedByGravity() {
        return false;
    }

    @Override
    public boolean isPickupable() {
        return true;
    }
}
