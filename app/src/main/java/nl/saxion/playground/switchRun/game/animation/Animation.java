package nl.saxion.playground.switchRun.game.animation;

import android.graphics.Bitmap;
import android.util.Log;
import nl.saxion.playground.switchRun.lib.GameView;

import java.util.HashMap;
import java.util.Map;

public class Animation {
    private static final int DEFAULT_FPS = 24;

    private Bitmap spritesheet;
    // Gets initialized when parseFrames is called.
    private Bitmap[][] sprites = null;
    // The amount of pixels per frame in the source image.
    private int frameWidth;
    private int frameHeight;
    // The amount of pixels the frame will be on the screen.
    private int renderWidth;
    private int renderHeight;

    private Map<String, AnimationData> animations = new HashMap();
    // The animation currently running. Nothing is shown if it is set to null.
    private String currentAnimation = null;

    public Animation(Bitmap spritesheet, int framesX, int framesY, int renderWidth, int renderHeight) {
        this.spritesheet = spritesheet;

        this.renderWidth = renderWidth;
        this.renderHeight = renderHeight;
        // Calculate how much frames there are in the X and Y axis of the sprite sheet.

        final int frameWidth = (int) Math.floor(spritesheet.getWidth() / framesX);
        final int frameHeight = (int) Math.floor(spritesheet.getHeight() / framesY);

        Log.v("Animation", "The spritesheet is " + spritesheet.getWidth() + "x" + spritesheet.getHeight());
        Log.v("Animation", "The frames are " + frameWidth + "x" + frameHeight);

        // Prepare memory to store the sprites in their respective array.
        this.sprites = new Bitmap[framesY][framesX];
        // Subdivide the bitmap into its sprites and store them for later use.
        for(int y = 0; y < sprites.length; y++) {
            for(int x = 0; x < sprites[y].length; x++) {
                this.sprites[y][x] = Bitmap.createBitmap(spritesheet, x * frameWidth, y * frameHeight, frameWidth, frameHeight);
            }
        }
    }

    public void draw(GameView gv, float x, float y, float deltaTime) {
        if(currentAnimation == null)
            return;

        final AnimationData current = animations.get(currentAnimation);
        current.tick(deltaTime);

        Bitmap frame = sprites[current.getRow()][current.getCurrentFrame()];
        if(frame != null)
            gv.drawBitmap(frame, x, y, renderWidth, renderHeight);

        //gv.drawBitmap(spritesheet, x, y, renderWidth, renderHeight);
    }

    public void setAnimation(String name) {
        this.currentAnimation = name;
    }

    public void addAnimation(String name, int row, int[] frames) {
        addAnimation(name, DEFAULT_FPS, row, frames);
    }

    public void addAnimation(String name, int fps, int row, int[] frames) {
        this.animations.put(name, new AnimationData(name, fps, row, frames));
    }
}
