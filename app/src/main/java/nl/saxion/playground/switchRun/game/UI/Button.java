package nl.saxion.playground.switchRun.game.UI;

import java.util.ArrayList;
import java.util.List;

import nl.saxion.playground.switchRun.game.DimensionType;
import nl.saxion.playground.switchRun.game.Game;
import nl.saxion.playground.switchRun.game.collision.CollisionDirection;
import nl.saxion.playground.switchRun.game.entities.GameEntity;
import nl.saxion.playground.switchRun.lib.GameModel;
import nl.saxion.playground.switchRun.lib.GameView;

public class Button extends GameEntity implements MenuItem {

    private List<TouchListener> touchListeners = new ArrayList<>();

    public Button(Game game, float x, float y, int width, int height, int resource) {
        super(game, x, y, width, height, resource, DimensionType.Both);
    }

    @Override
    public void tick() {
        for (GameModel.Touch touch : game.touches) {
            if (touch.x > position.x && touch.x < position.x + width &&
                    touch.y > position.y && touch.y < position.y + height) {

                for (TouchListener listener : touchListeners) {
                    listener.onTouch();
                }
            }
        }
    }

    @Override
    public void draw(GameView gv) {
        if (bitmap == null)
            bitmap = gv.getBitmapFromResource(resource);

        gv.drawBitmap(bitmap, position.x, position.y, width, height);
    }

    public void addOnTouchListener(TouchListener listener) {
        touchListeners.add(listener);
    }

    public void removeonTouchListener(TouchListener listener) {
        touchListeners.remove(listener);
    }

    @Override
    public void onCollisionWith(GameEntity other, CollisionDirection direction) {
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
        return false;
    }

    @Override
    public GameEntity getInstance() {
        return this;
    }

    @Override
    public int getLayer() {
        return 10;
    }
}
