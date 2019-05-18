package com.mathgame.activity;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
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
        setContentView(R.layout.activity_tutorial_data);
        Toolbar toolbar=findViewById(R.id.toolbar);
        TextView tvTutorial=findViewById(R.id.tvTutorial);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            String title = mBundle.getString(Keys.Prefs.KEY_TITLE);
            String webUrl = mBundle.getString(Keys.Prefs.KEY_WEB_URL);
            tvTutorial.setText(Utils.fromHtml(webUrl));
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void onBackPressed() {
        Transition.exit(this);
    }
}

