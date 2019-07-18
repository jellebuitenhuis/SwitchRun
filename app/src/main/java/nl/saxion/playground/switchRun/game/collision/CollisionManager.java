package nl.saxion.playground.switchRun.game.collision;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nl.saxion.playground.switchRun.game.entities.GameEntity;

public class CollisionManager {
    @SuppressLint("NewApi")
    private List<GameEntity> objects = new ArrayList<>();
    private Comparator<GameEntity>  objectComparator = new HeightComparator();


    public void add(GameEntity entity) {
        objects.add(entity);
    }

    public void remove(GameEntity entity) {
        objects.remove(entity);
    }

    public void resolve() {
        Collections.sort(objects, objectComparator);
        //Log.d("CollisionManager", "Highest object: " + objects.get(0).getClass().getSimpleName());

        for(int i = objects.size() - 1; i >= 0; i--) {
            final GameEntity first = objects.get(i);

            for(int j = objects.size() - 1; j >= 0; j--) {
                final GameEntity second = objects.get(j);
                // Never check collision between the player and the player itself.
                if(first == second)
                    continue;
                // Never check collision we don't need to.
                if(first.isCollidableWith(second) == false)
                    continue;

                //Log.d("CollisionManager", "Checking collision between " + first.getClass().getSimpleName() + " and " + second.getClass().getSimpleName());

                resolve(first, second);
            }
        }
    }

    private void resolve(GameEntity e1, GameEntity e2) {
        final BoundingBox first = e1.getCollisionRectangle();
        final BoundingBox second = e2.getCollisionRectangle();

        float dx = first.getOverlapX(second),
                dy = first.getOverlapY(second);
        if (dx == 0 || dy == 0)
            return;

        CollisionDirection direction = CollisionDirection.None;
        // Only move the smallest amount.
        if(Math.abs(dx) < Math.abs(dy)) {
            // Offset the first position to be outside of the second bounding box.
            e1.getPosition().x += dx;
            // Determine the direction the collision came from.
            if(dx > 0) {
                direction = CollisionDirection.Left;
            } else if(dx < 0) {
                direction = CollisionDirection.Right;
            }
        } else {
            // Offset the first position to be outside of the second bounding box.
            e1.getPosition().y += dy;
            // Determine the direction the collision came from.
            if(dy > 0) {
                direction = CollisionDirection.Up;
            } else if(dy < 0) {
                direction = CollisionDirection.Down;
            }
        }

        e1.onCollisionWith(e2, direction);
    }



}
