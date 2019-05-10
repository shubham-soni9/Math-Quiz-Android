package com.mathgame.structure;

import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.mathgame.database.ObjectBox;

public class MyApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        ObjectBox.init(this);
    }
}
