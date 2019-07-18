package nl.saxion.playground.switchRun.game.entities;

import android.graphics.Bitmap;

import nl.saxion.playground.switchRun.game.Camera;
import nl.saxion.playground.switchRun.game.DimensionType;
import nl.saxion.playground.switchRun.game.Game;
import nl.saxion.playground.switchRun.game.collision.Collidable;
import nl.saxion.playground.switchRun.game.collision.BoundingBox;
import nl.saxion.playground.switchRun.game.math.Vector2;
import nl.saxion.playground.switchRun.lib.Entity;
import nl.saxion.playground.switchRun.lib.GameView;

/**
 * The base class for any Entity that requires a position or size
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class GameEntity extends Entity implements EntityProperties, Collidable {
    protected Vector2 position;
    protected Vector2 velocity;
    protected Game game;

    protected float angle = 0;
    protected int width;
    protected int height;

    protected int resource;
    protected Bitmap bitmap;

    protected BoundingBox collision;
    protected DimensionType dimension;
    /**
     * The initializer
     *
     * @param game     the Game that the GameEntity will be part of
     * @param x        the x position the GameEntity should start at
     * @param y        the y position the GameEntity should start at
     * @param width    the width of the GameEntity
     * @param height   the height of the GameEntity
     * @param resource the id of the drawable the GameEntity should use
     */
    public GameEntity(Game game, float x, float y, int width, int height, int resource, DimensionType dimension) {
        this.game = game;
        this.position = new Vector2(x, y);
        this.velocity = new Vector2();

        this.collision = new BoundingBox(this.position, width, height);

        this.width = width;
        this.height = height;

        this.dimension = dimension;

        this.resource = resource;
    }

    /**
     * Adds the velocity to the current position, effectively moving the GameEntity
     */
    @Override
    public void tick() {
    }

    /**
     * Draws the GameEntity if a resource is given
     *
     * @param gv The `GameView` to draw to.
     */
    @Override
    public void draw(GameView gv) {
        final Camera camera = game.getCamera();

        if (bitmap == null && resource != -1) {
            bitmap = gv.getBitmapFromResource(resource);
        }

        if (bitmap != null) {
            gv.drawBitmap(bitmap, camera.calculateScreenX(position.x), camera.calculateScreenY(position.y), width, height, angle);
        }
    }

    /**
     * @return the position of the entity.
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Sets the position of the GameEntity
     *
     * @param x the new x position
     * @param y the new y position
     */
    protected void setPosition(float x, float y) {
        position.set(x, y);
    }

    /**
     * @return the velocity of the entity
     */
    public Vector2 getVelocity() {
        return velocity;
    }

    /**
     * Returns the width of the GameEntity
     *
     * @return the width of the GameEntity
     */
    public float getWidth() {
        return width;
    }

    /**
     * Returns the height of the GameEntity
     *
     * @return the height of the GameEntity
     */
    public float getHeight() {
        return height;
    }

    /**
     * Returns the angle of the GameEntity
     *
     * @return the angle of the GameEntity
     */
    public float getAngle() {
        return angle;
    }

    /**
     * Sets the angle of the GameEntity
     *
     * @param angle the new angle
     */
    protected void setAngle(float angle) {
        this.angle = angle;
    }

    /**
     * Sets the resource to be used when drawing the GameEntity
     *
     * @param resource the resource to be used when drawing the GameEntity
     */
    protected void setDrawable(int resource) {
        this.resource = resource;
        bitmap = null;
    }

    public BoundingBox getCollisionRectangle() {
        return collision;
    }

    @Override
    public boolean isCollidableWith(GameEntity other) {
        final DimensionType otherDim = other.getCurrentDimension();

        return (this.dimension == DimensionType.Both ||
                otherDim == DimensionType.Both ||
                this.dimension == other.getCurrentDimension());
    }

    public DimensionType getCurrentDimension() {
        return this.dimension;
    }

    /**
     * Returns the x coordinate for the left border of the GameEntity
     *
     * @return the x coordinate for the left border of the GameEntity
     */
    float getLeft() {
        return position.x;
    }

    /**
     * Returns the x coordinate for the right border of the GameEntity
     *
     * @return the x coordinate for the right border of the GameEntity
     */
    float getRight() {
        return position.x + width;
    }

    /**
     * Returns the y coordinate for the top border of the GameEntity
     *
     * @return the y coordinate for the top border of the GameEntity
     */
    float getTop() {
        return position.y;
    }

    /**
     * Returns the y coordinate fot the bottom border of the gameObject
     *
     * @return the y coordinate fot the bottom border of the gameObject
     */
    float getBot() {
        return position.y + height;
    }
}
