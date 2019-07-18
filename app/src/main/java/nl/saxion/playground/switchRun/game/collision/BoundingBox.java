package nl.saxion.playground.switchRun.game.collision;

import nl.saxion.playground.switchRun.game.math.Vector2;

public class BoundingBox {
    public Vector2 position;
    public float width;
    public float height;

    public BoundingBox() {
        this(null, 0, 0);
    }

    public BoundingBox(Vector2 position, int width, int height) {
        this.position = (position != null) ? position : new Vector2();
        this.width = width;
        this.height = height;
    }

    public float left() {
        return position.x;
    }

    public float right() {
        return position.x + width;
    }

    public float top() {
        return position.y;
    }

    public float bottom() {
        return position.y + height;
    }

    public float centerX() {
        return position.x + width / 2;
    }

    public float centerY() {
        return position.y + height / 2;
    }

    public float getOverlapX(BoundingBox other) {
        // Calculate half sizes.
        float halfWidthA = this.width / 2.0f;
        float halfWidthB = other.width / 2.0f;

        // Calculate centers.
        float centerA = this.left() + halfWidthA;
        float centerB = other.left() + halfWidthB;

        // Calculate current and minimum-non-intersecting distances between centers.
        float distanceX = centerA - centerB;
        float minDistanceX = halfWidthA + halfWidthB;

        // If we are not intersecting at all, return (0, 0).
        if (Math.abs(distanceX) >= minDistanceX)
            return 0f;

        // Calculate and return intersection depths.
        return distanceX > 0 ? minDistanceX - distanceX : -minDistanceX - distanceX;
    }

    public float getOverlapY(BoundingBox other) {
        // Calculate half sizes.
        float halfHeightA = this.height / 2.0f;
        float halfHeightB = other.height / 2.0f;

        // Calculate centers.
        float centerA = this.top() + halfHeightA;
        float centerB = other.top() + halfHeightB;

        // Calculate current and minimum-non-intersecting distances between centers.
        float distanceY = centerA - centerB;
        float minDistanceY = halfHeightA + halfHeightB;

        // If we are not intersecting at all, return (0, 0).
        if (Math.abs(distanceY) >= minDistanceY)
            return 0f;

        // Calculate and return intersection depths.
        return distanceY > 0 ? minDistanceY - distanceY : -minDistanceY - distanceY;
    }
}
