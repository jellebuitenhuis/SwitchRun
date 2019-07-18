package nl.saxion.playground.switchRun.game.entities;

import nl.saxion.playground.switchRun.game.DimensionType;
import nl.saxion.playground.switchRun.game.Game;

public abstract class MovingEntity extends GameEntity {
    private static final float GRAVITY = 0.00981f;
    protected static final float STANDARD_MAX_FALL_SPEED = 1.2f;
    protected static float MAX_FALL_SPEED = STANDARD_MAX_FALL_SPEED;

    public MovingEntity(Game game, float x, float y, int width, int height, int resource, DimensionType dimension) {
        super(game, x, y, width, height, resource, dimension);
    }

    @Override
    public void tick() {
        if(isAffectedByGravity()) {
            //Applies gravity
            velocity.y += GRAVITY;
            if (velocity.y > MAX_FALL_SPEED) {
                velocity.y = MAX_FALL_SPEED;
            }
        } else {
            velocity.y = 0;
        }

        position.add(velocity);
    }


    @Override
    public boolean isMovable() {
        return true;
    }
}
