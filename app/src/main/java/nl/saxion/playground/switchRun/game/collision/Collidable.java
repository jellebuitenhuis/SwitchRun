package nl.saxion.playground.switchRun.game.collision;


import nl.saxion.playground.switchRun.game.DimensionType;
import nl.saxion.playground.switchRun.game.entities.GameEntity;

public interface Collidable {
    BoundingBox getCollisionRectangle();
    boolean isCollidableWith(GameEntity other);

    void onCollisionWith(GameEntity other, CollisionDirection direction);

    DimensionType getCurrentDimension();
}
