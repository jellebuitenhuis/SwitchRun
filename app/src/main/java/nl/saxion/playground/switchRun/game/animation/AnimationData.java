package nl.saxion.playground.switchRun.game.animation;

/**
 * Simple container class to keep the data of an animation nice and seperated from the logic.
 */
public class AnimationData {
    private String name;
    private int fps;
    private int row;
    private int[] frames;

    private int currentFrame;
    private float timePerFrame;
    private float currentFrameTime;

    public AnimationData(String name, int fps, int row, int... frames) {
        this.name = name;
        this.row = row;
        this.frames = frames;

        this.timePerFrame = 1.f / fps;
        this.currentFrameTime = 0;
    }

    public void tick(float deltaTime) {
        this.currentFrameTime += deltaTime;
        // Advance the frame if we reached the time to display each frame.
        if(this.currentFrameTime >= this.timePerFrame) {
            this.currentFrame ++;
            // Push the timer back.
            this.currentFrameTime -= this.timePerFrame;
        }
    }

    public int getCurrentFrame() {
        return this.currentFrame % frames.length;
    }

    public int getRow() {
        return this.row;
    }

}
