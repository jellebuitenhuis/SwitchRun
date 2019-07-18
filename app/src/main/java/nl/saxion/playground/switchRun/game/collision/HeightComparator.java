package nl.saxion.playground.switchRun.game.collision;

import java.util.Comparator;

import nl.saxion.playground.switchRun.game.entities.GameEntity;

public class HeightComparator implements Comparator<GameEntity> {
    public int compare(GameEntity first, GameEntity second) {
        return (int)Math.signum(first.getCollisionRectangle().top() - second.getCollisionRectangle().top());
    }
}
