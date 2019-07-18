package nl.saxion.playground.switchRun.game.UI;

import java.util.ArrayList;
import java.util.List;

import nl.saxion.playground.switchRun.game.DimensionType;
import nl.saxion.playground.switchRun.game.Game;
import nl.saxion.playground.switchRun.game.collision.CollisionDirection;
import nl.saxion.playground.switchRun.game.entities.GameEntity;
import nl.saxion.playground.switchRun.lib.GameView;

public class Image extends GameEntity implements MenuItem {

    private final Game game;

    private List<TouchListener> touchListeners = new ArrayList<>();


    /**
     * The initializer
     *
     * @param game      the Game that the GameEntity will be part of
     * @param x         the x position the GameEntity should start at
     * @param y         the y position the GameEntity should start at
     * @param width     the width of the GameEntity
     * @param height    the height of the GameEntity
     * @param resource  the id of the drawable the GameEntity should use
     */
    public Image(Game game, float x, float y, int width, int height, int resource) {
        super(game, x, y, width, height, resource, DimensionType.Both);
        this.game = game;
    }

    @Override
    public void draw(GameView gv) {
        if (bitmap == null)
            bitmap = gv.getBitmapFromResource(resource);
        gv.drawBitmap(bitmap, position.x, position.y, width, height);
    }

    @Override
    public void onCollisionWith(GameEntity other, CollisionDirection direction) { }

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
        return 1;
    }
}
