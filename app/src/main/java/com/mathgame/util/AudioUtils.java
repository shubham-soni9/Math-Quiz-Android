package com.mathgame.util;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.RawRes;

import com.mathgame.R;

public class AudioUtils {
    private static volatile AudioUtils instance;
    private MediaPlayer mediaPlayer;

    public static AudioUtils getInstance() {
        if (instance == null) synchronized (AudioUtils.class) {
            if (instance == null) instance = new AudioUtils();
        }
        return instance;
    }

    public void onButtonClicked(Context context) {
        String sound = "android.resource://" + context.getPackageName() + "/" + R.raw.btn_click;
        final MediaPlayer mp = MediaPlayer.create(context.getApplicationContext(), Uri.parse(sound));
        mp.start();
    }
}
