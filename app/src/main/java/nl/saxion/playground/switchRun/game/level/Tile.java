package nl.saxion.playground.switchRun.game.level;

import android.graphics.Bitmap;

import nl.saxion.playground.switchRun.R;
import nl.saxion.playground.switchRun.game.DimensionType;
import nl.saxion.playground.switchRun.game.Game;
import nl.saxion.playground.switchRun.game.collision.CollisionDirection;
import nl.saxion.playground.switchRun.game.entities.GameEntity;

/**
 * The basic tile
 */
@SuppressWarnings("WeakerAccess")
public class Tile extends GameEntity {
    public static final int TILE_SIZE = 10;

    private static Bitmap TILE_BLUE_SOLO_SOLO;
    private static Bitmap TILE_BLUE_BEGIN_SOLO;
    private static Bitmap TILE_BLUE_MIDDLE_SOLO;
    private static Bitmap TILE_BLUE_END_SOLO;
    private static Bitmap TILE_BLUE_SOLO_BOTTOM;
    private static Bitmap TILE_BLUE_BEGIN_BOTTOM;
    private static Bitmap TILE_BLUE_MIDDLE_BOTTOM;
    private static Bitmap TILE_BLUE_END_BOTTOM;
    private static Bitmap TILE_BLUE_SOLO_MIDDLE;
    private static Bitmap TILE_BLUE_BEGIN_MIDDLE;
    private static Bitmap TILE_BLUE_MIDDLE_MIDDLE;
    private static Bitmap TILE_BLUE_END_MIDDLE;
    private static Bitmap TILE_BLUE_SOLO_TOP;
    private static Bitmap TILE_BLUE_BEGIN_TOP;
    private static Bitmap TILE_BLUE_MIDDLE_TOP;
    private static Bitmap TILE_BLUE_END_TOP;
    private static Bitmap TILE_RED_SOLO_SOLO;
    private static Bitmap TILE_RED_BEGIN_SOLO;
    private static Bitmap TILE_RED_MIDDLE_SOLO;
    private static Bitmap TILE_RED_END_SOLO;
    private static Bitmap TILE_RED_SOLO_BOTTOM;
    private static Bitmap TILE_RED_BEGIN_BOTTOM;
    private static Bitmap TILE_RED_MIDDLE_BOTTOM;
    private static Bitmap TILE_RED_END_BOTTOM;
    private static Bitmap TILE_RED_SOLO_MIDDLE;
    private static Bitmap TILE_RED_BEGIN_MIDDLE;
    private static Bitmap TILE_RED_MIDDLE_MIDDLE;
    private static Bitmap TILE_RED_END_MIDDLE;
    private static Bitmap TILE_RED_SOLO_TOP;
    private static Bitmap TILE_RED_BEGIN_TOP;
    private static Bitmap TILE_RED_MIDDLE_TOP;
    private static Bitmap TILE_RED_END_TOP;


    private DimensionType type;
    private LevelSegment segment;

    private int tileX;
    private int tileY;

    /*
     * 0: begin
     * 1: middle
     * 2: end
     * 3: solo
     */
    private int tileSpriteX = 3;
    /*
     * 0: top
     * 1: middle
     * 2: end
     * 3: solo
     */
    private int tileSpriteY=3;

    public Tile(Game game, LevelSegment segment, DimensionType type, int tileX, int tileY) {
        super(game, 0, 0, TILE_SIZE, TILE_SIZE, -1, type);

        this.segment = segment;
        this.type = type;

        this.tileX = tileX;
        this.tileY = tileY;
        // Set the tile default at it's local position.
        this.position.set(getLocalX(), getLocalY());

        // TODO: Litrte hacky, need to make this more clean
        if (TILE_RED_SOLO_SOLO == null || TILE_BLUE_SOLO_SOLO == null) {
            TILE_BLUE_SOLO_SOLO = game.getView().getBitmapFromResource(R.drawable.tile_blue_solo_solo);
            TILE_BLUE_BEGIN_SOLO = game.getView().getBitmapFromResource(R.drawable.tile_blue_begin_solo);
            TILE_BLUE_MIDDLE_SOLO = game.getView().getBitmapFromResource(R.drawable.tile_blue_middle_solo);
            TILE_BLUE_END_SOLO = game.getView().getBitmapFromResource(R.drawable.tile_blue_end_solo);
            TILE_BLUE_SOLO_BOTTOM = game.getView().getBitmapFromResource(R.drawable.tile_blue_solo_bottom);
            TILE_BLUE_BEGIN_BOTTOM = game.getView().getBitmapFromResource(R.drawable.tile_blue_begin_bottom);
            TILE_BLUE_MIDDLE_BOTTOM = game.getView().getBitmapFromResource(R.drawable.tile_blue_middle_bottom);
            TILE_BLUE_END_BOTTOM = game.getView().getBitmapFromResource(R.drawable.tile_blue_end_bottom);
            TILE_BLUE_SOLO_MIDDLE = game.getView().getBitmapFromResource(R.drawable.tile_blue_solo_middle);
            TILE_BLUE_BEGIN_MIDDLE = game.getView().getBitmapFromResource(R.drawable.tile_blue_begin_middle);
            TILE_BLUE_MIDDLE_MIDDLE = game.getView().getBitmapFromResource(R.drawable.tile_blue_middle_middle);
            TILE_BLUE_END_MIDDLE = game.getView().getBitmapFromResource(R.drawable.tile_blue_end_middle);
            TILE_BLUE_SOLO_TOP = game.getView().getBitmapFromResource(R.drawable.tile_blue_solo_top);
            TILE_BLUE_BEGIN_TOP = game.getView().getBitmapFromResource(R.drawable.tile_blue_begin_top);
            TILE_BLUE_MIDDLE_TOP = game.getView().getBitmapFromResource(R.drawable.tile_blue_middle_top);
            TILE_BLUE_END_TOP = game.getView().getBitmapFromResource(R.drawable.tile_blue_end_top);
            TILE_RED_SOLO_SOLO = game.getView().getBitmapFromResource(R.drawable.tile_red_solo_solo);
            TILE_RED_BEGIN_SOLO = game.getView().getBitmapFromResource(R.drawable.tile_red_begin_solo);
            TILE_RED_MIDDLE_SOLO = game.getView().getBitmapFromResource(R.drawable.tile_red_middle_solo);
            TILE_RED_END_SOLO = game.getView().getBitmapFromResource(R.drawable.tile_red_end_solo);
            TILE_RED_SOLO_BOTTOM = game.getView().getBitmapFromResource(R.drawable.tile_red_solo_bottom);
            TILE_RED_BEGIN_BOTTOM = game.getView().getBitmapFromResource(R.drawable.tile_red_begin_bottom);
            TILE_RED_MIDDLE_BOTTOM = game.getView().getBitmapFromResource(R.drawable.tile_red_middle_bottom);
            TILE_RED_END_BOTTOM = game.getView().getBitmapFromResource(R.drawable.tile_red_end_bottom);
            TILE_RED_SOLO_MIDDLE = game.getView().getBitmapFromResource(R.drawable.tile_red_solo_middle);
            TILE_RED_BEGIN_MIDDLE = game.getView().getBitmapFromResource(R.drawable.tile_red_begin_middle);
            TILE_RED_MIDDLE_MIDDLE = game.getView().getBitmapFromResource(R.drawable.tile_red_middle_middle);
            TILE_RED_END_MIDDLE = game.getView().getBitmapFromResource(R.drawable.tile_red_end_middle);
            TILE_RED_SOLO_TOP = game.getView().getBitmapFromResource(R.drawable.tile_red_solo_top);
            TILE_RED_BEGIN_TOP = game.getView().getBitmapFromResource(R.drawable.tile_red_begin_top);
            TILE_RED_MIDDLE_TOP = game.getView().getBitmapFromResource(R.drawable.tile_red_middle_top);
            TILE_RED_END_TOP = game.getView().getBitmapFromResource(R.drawable.tile_red_end_top);

        }
    }

    @Override
    public void onCollisionWith(GameEntity other, CollisionDirection direction) {

    }

    @Override
    public boolean isCollidableWith(GameEntity other) {
        // Tiles don't need to check collision with anything.
        return false;
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

    public void makeVisibleFor(DimensionType dimension) {

        Bitmap b = null;
        if (type == dimension || type == DimensionType.Both) {
            //this.bitmap = (dimension == DimensionType.Red) ? TILE_RED :
            //       (dimension == DimensionType.Blue) ? TILE_BLUE : null;

            Tile previous = segment.getTile(tileX - 1, tileY);
            Tile next = segment.getTile(tileX + 1, tileY);
            Tile up = segment.getTile(tileX, tileY - 1);
            Tile down = segment.getTile(tileX, tileY + 1);

            if (previous == null && next == null) {
                tileSpriteX = 3;
            }
            else if (previous == null) {
                if (next.type == DimensionType.Both || next.type == dimension) {
                    tileSpriteX = 0;
                }
            } else if (next == null) {
                if (previous.type == DimensionType.Both || previous.type == dimension) {
                    tileSpriteX = 2;
                }
            } else {
                if ((previous.type == DimensionType.Both || previous.type == dimension) && (next.type == DimensionType.Both || next.type == dimension)) {
                    tileSpriteX = 1;
                }
                else if (next.type == DimensionType.Both || next.type == dimension) {
                    tileSpriteX = 0;
                }
                else if (previous.type == DimensionType.Both || previous.type == dimension) {
                    tileSpriteX = 2;
                }
            }

            if (down == null && up == null) {
                tileSpriteY = 3;
            }
            else if (up == null) {
                if (down.type == DimensionType.Both || down.type == dimension) {
                    tileSpriteY = 0;
                }
            } else if (down == null) {
                if (up.type == DimensionType.Both || up.type == dimension) {
                    tileSpriteY = 2;
                }
            } else {
                if ((up.type == DimensionType.Both || up.type == dimension) && (down.type == DimensionType.Both || down.type == dimension)) {
                    tileSpriteY = 1;
                }
                else if (down.type == DimensionType.Both || down.type == dimension) {
                    tileSpriteY = 0;
                }
                else if (up.type == DimensionType.Both || up.type == dimension) {
                    tileSpriteY = 2;
                }
            }


            if (dimension == DimensionType.Red) {
                if (tileSpriteY == 0) {
                    if (tileSpriteX == 0) {
                        b = TILE_RED_BEGIN_TOP;
                    } else if (tileSpriteX == 1) {
                        b = TILE_RED_MIDDLE_TOP;
                    } else if (tileSpriteX == 2) {
                        b = TILE_RED_END_TOP;
                    } else if (tileSpriteX == 3) {
                        b = TILE_RED_SOLO_TOP;
                    }
                } else if (tileSpriteY == 1) {
                    if (tileSpriteX == 0) {
                        b = TILE_RED_BEGIN_MIDDLE;
                    } else if (tileSpriteX == 1) {
                        b = TILE_RED_MIDDLE_MIDDLE;
                    } else if (tileSpriteX == 2) {
                        b = TILE_RED_END_MIDDLE;
                    } else if (tileSpriteX == 3) {
                        b = TILE_RED_SOLO_MIDDLE;
                    }
                } else if (tileSpriteY == 2) {
                    if (tileSpriteX == 0) {
                        b = TILE_RED_BEGIN_BOTTOM;
                    } else if (tileSpriteX == 1) {
                        b = TILE_RED_MIDDLE_BOTTOM;
                    } else if (tileSpriteX == 2) {
                        b = TILE_RED_END_BOTTOM;
                    } else if (tileSpriteX == 3) {
                        b = TILE_RED_SOLO_BOTTOM;
                    }
                } else if (tileSpriteY == 3) {
                    if (tileSpriteX == 0) {
                        b = TILE_RED_BEGIN_SOLO;
                    } else if (tileSpriteX == 1) {
                        b = TILE_RED_MIDDLE_SOLO;
                    } else if (tileSpriteX == 2) {
                        b = TILE_RED_END_SOLO;
                    } else if (tileSpriteX == 3) {
                        b = TILE_RED_SOLO_SOLO;
                    }
                }
            } else if (dimension == DimensionType.Blue) {
                if (tileSpriteY == 0) {
                    if (tileSpriteX == 0) {
                        b = TILE_BLUE_BEGIN_TOP;
                    } else if (tileSpriteX == 1) {
                        b = TILE_BLUE_MIDDLE_TOP;
                    } else if (tileSpriteX == 2) {
                        b = TILE_BLUE_END_TOP;
                    } else if (tileSpriteX == 3) {
                        b = TILE_BLUE_SOLO_TOP;
                    }
                } else if (tileSpriteY == 1) {
                    if (tileSpriteX == 0) {
                        b = TILE_BLUE_BEGIN_MIDDLE;
                    } else if (tileSpriteX == 1) {
                        b = TILE_BLUE_MIDDLE_MIDDLE;
                    } else if (tileSpriteX == 2) {
                        b = TILE_BLUE_END_MIDDLE;
                    } else if (tileSpriteX == 3) {
                        b = TILE_BLUE_SOLO_MIDDLE;
                    }
                } else if (tileSpriteY == 2) {
                    if (tileSpriteX == 0) {
                        b = TILE_BLUE_BEGIN_BOTTOM;
                    } else if (tileSpriteX == 1) {
                        b = TILE_BLUE_MIDDLE_BOTTOM;
                    } else if (tileSpriteX == 2) {
                        b = TILE_BLUE_END_BOTTOM;
                    } else if (tileSpriteX == 3) {
                        b = TILE_BLUE_SOLO_BOTTOM;
                    }
                } else if (tileSpriteY == 3) {
                    if (tileSpriteX == 0) {
                        b = TILE_BLUE_BEGIN_SOLO;
                    } else if (tileSpriteX == 1) {
                        b = TILE_BLUE_MIDDLE_SOLO;
                    } else if (tileSpriteX == 2) {
                        b = TILE_BLUE_END_SOLO;
                    } else if (tileSpriteX == 3) {
                        b = TILE_BLUE_SOLO_SOLO;
                    }
                }
            }
        }
        this.bitmap = b;
    }

    public float getLocalX() {
        return tileX * TILE_SIZE;
    }

    public float getLocalY() {
        return tileY * TILE_SIZE;
    }
}
