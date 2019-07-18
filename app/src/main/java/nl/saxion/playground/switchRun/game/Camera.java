package nl.saxion.playground.switchRun.game;

import nl.saxion.playground.switchRun.game.math.Vector2;

public class Camera {
    // The amount of seconds the camera has to catch up to its target.
    public static final float FOLLOW_DELAY = .1f;
    // The minimum amount of distance before the camera actually moves.
    private static final float EPSILON = 0.5f;

    public final Vector2 target = new Vector2();
    public final Vector2 position = new Vector2();
    // The center of the screen where the focus of the camera is.
    public final Vector2 center = new Vector2();

    public final Vector2 velocity = new Vector2();
    public Boolean started = false;

    private Game game;

    public Camera(Game game) {
        this.game = game;
        // Default the center to the center of the screen.
        center.x = game.getWidth() / 2;
        center.y = game.getHeight() / 2;
    }

    public void start() {
        started = true;
    }

    public void tick() {
        if (!started) return;

        // Interpolation is easier to implement using a delta time.
        float deltaTime = 1.f / game.ticksPerSecond();
        // Calculate the distance between where the camera is now and where it wants to be.


        float dy = target.y - position.y;

        position.x += velocity.x;
        if(Math.abs(dy) > EPSILON) {
            position.y += dy * (deltaTime / FOLLOW_DELAY);
        }
    }

    // Transforms a world coordinate to the correct screen coordinate.
    public int calculateScreenX(float x) {
        return Math.round(center.x + x - position.x);
    }
    public int calculateScreenY(float y) {
        return Math.round(center.y + y - position.y);
    }

}
