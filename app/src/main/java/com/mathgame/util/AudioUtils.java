package com.mathgame.util;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.RawRes;

import com.mathgame.R;

public class AudioUtils {
    public static void playAudio(Context context, @RawRes int tone) {
        try {
            String sound = "android.resource://" + context.getPackageName() + "/" + tone;
            final MediaPlayer mp = MediaPlayer.create(context.getApplicationContext(), Uri.parse(sound));
            mp.start();
            Log.v("Play", "Play");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onButtonClicked(Context context){
        String sound = "android.resource://" + context.getPackageName() + "/" + R.raw.btn_click;
        final MediaPlayer mp = MediaPlayer.create(context.getApplicationContext(), Uri.parse(sound));
        mp.start();
    }
}
