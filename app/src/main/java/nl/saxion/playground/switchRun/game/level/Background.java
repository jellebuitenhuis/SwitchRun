package nl.saxion.playground.switchRun.game.level;

import android.graphics.Bitmap;

import nl.saxion.playground.switchRun.R;
import nl.saxion.playground.switchRun.game.DimensionType;
import nl.saxion.playground.switchRun.game.Game;
import nl.saxion.playground.switchRun.lib.Entity;
import nl.saxion.playground.switchRun.lib.GameView;

public class Background extends Entity {

    private static Bitmap bitmap;
    private static int bitmapId = 0;

    private Game game;


    public Background(Game game) {
        super();
        this.game = game;
        bitmapId = R.drawable.background_red;
    }

    @Override
    public int getLayer() {
        return -10;
    }

    @Override
    public void draw(GameView gv) {
        if (bitmap == null){
            bitmap = gv.getBitmapFromResource(bitmapId);
        }
        //laad aan de handt van de dimensie een andere background zien
        if (game.getCurrentPlayer().getCurrentDimension() == DimensionType.Blue) {
            if (bitmapId != R.drawable.background_blue) {
                bitmap = gv.getBitmapFromResource(R.drawable.background_blue);
            }
            bitmapId = R.drawable.background_blue;
        } else {
            if (bitmapId != R.drawable.background_red) {
                bitmap = gv.getBitmapFromResource(R.drawable.background_red);
            }
            bitmapId = R.drawable.background_red;
        }
        float bgWidth = (float) bitmap.getWidth() / (float) bitmap.getHeight() * game.getHeight();
        float offset = (game.getCamera().position.x) % bgWidth; // one-third speed relative to foreground

        for (int x = 0; x <= Math.ceil(game.getWidth() / bgWidth); x++) {
            gv.drawBitmap(bitmap, (float) x * bgWidth - offset, 0, bgWidth, game.getHeight());
        }
    }
}
