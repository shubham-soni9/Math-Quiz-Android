package com.mathgame.structure;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mathgame.appdata.Dependencies;

import java.util.Locale;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public Locale locale() {
        return Dependencies.getLocale(this);
    }
}
