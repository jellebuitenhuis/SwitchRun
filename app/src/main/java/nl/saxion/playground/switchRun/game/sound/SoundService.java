package nl.saxion.playground.switchRun.game.sound;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

public class SoundService {

    public static void play(Context context, Sound sound, boolean loop) {
        if(sound.getResource() == -1) {
            Log.e("SoundService", "Sound \"" + sound.toString() + "\" doesn't have a playable resource.");
            return;
        }

        MediaPlayer mp = MediaPlayer.create(context, sound.getResource());
        mp.setLooping(loop);
        mp.start();
    }

    public static void play(Context context, Sound sound) {
        play(context, sound, false);
    }

}
