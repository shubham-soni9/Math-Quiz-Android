package com.mathgame.activity;


import android.os.Bundle;
import android.widget.TextView;

import com.mathgame.R;
import com.mathgame.appdata.Keys;
import com.mathgame.structure.BaseActivity;
import com.mathgame.util.Transition;
import com.mathgame.util.Utils;

public class TutorialDataActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tvTutorial = findViewById(R.id.tvTutorial);
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            String webUrl = mBundle.getString(Keys.Prefs.KEY_WEB_URL);
            tvTutorial.setText(Utils.fromHtml(webUrl));
        }
    }

    @Override
    public String getToolbarTitle() {
        return getIntent().getStringExtra(Keys.Prefs.KEY_TITLE);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_tutorial_data;
    }

    @Override
    public void onBackPressed() {
        Transition.exit(this);
    }
}

