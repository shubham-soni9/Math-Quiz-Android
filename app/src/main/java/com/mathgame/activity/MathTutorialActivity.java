package com.mathgame.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.mathgame.R;
import com.mathgame.adapter.TutorialPagerAdapter;
import com.mathgame.appdata.GameSettings;
import com.mathgame.appdata.Keys;
import com.mathgame.structure.BaseActivity;

public class MathTutorialActivity extends BaseActivity {
    private ViewPager tutorialPager;
    private TabLayout tabTutorials;
    private Toolbar   toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_tutorial);
        init();
        setData();
    }

    private void setData() {
        TutorialPagerAdapter tutorialPagerAdapter = new TutorialPagerAdapter(getSupportFragmentManager(), GameSettings.getTutorialList(this));
        tutorialPager.setAdapter(tutorialPagerAdapter);
        tabTutorials.setupWithViewPager(tutorialPager);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.tutorials);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.getInt(Keys.Extras.OPERATION_TYPE, -1) != -1) {
            tutorialPager.setCurrentItem(bundle.getInt(Keys.Extras.OPERATION_TYPE));
        }
    }

    private void init() {
        tutorialPager = findViewById(R.id.tutorialPager);
        tabTutorials = findViewById(R.id.tabTutorials);
        toolbar = findViewById(R.id.toolbar);
    }
}
