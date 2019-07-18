package nl.saxion.playground.switchRun.game.math;

/**
 * Holds an x and y value
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class Vector2 {
    public float x;
    public float y;

    /**
     * Initializes a vector with 0 as x and y value
     */
    public Vector2() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Initializes a vector with given x and y value
     *
     * @param x the x value of the vector
     * @param y the y value of the vector
     */
    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Adds another vector
     *
     * @param other the vector to be added
     * @return the result
     */
    public Vector2 add(Vector2 other) {
        x += other.x;
        y += other.y;

        return this;
    }

    public Vector2 add(float x, float y) {
        return set(this.x + x, this.y + y);
    }


    /**
     * Subtracts another vector
     *
     * @param other the vector to be subtracted
     * @return the result
     */
    public Vector2 sub(Vector2 other) {
        x -= other.x;
        y -= other.y;

        return this;
    }

    public Vector2 sub(float x, float y) {
        return this.set(this.x - x, this.y - y);
    }


    /**
     * Sets the point to value
     * @param x the x coordinate
     * @param y the y coordinate
     * @return itself, useful for chaining
     */
    public Vector2 set(float x, float y) {
        this.x = x;
        this.y = y;

        return this;
    }

    /**
     * Sets this Vector2 to zero
     * @return itself, useful for chaining
     */
    public Vector2 zero() {
        return set(0, 0);
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }

    public Vector2 set(Vector2 position) {
        return set(position.x, position.y);
    }
}
