package com.mathgame.activity;


import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.mathgame.R;
import com.mathgame.appdata.Keys;
import com.mathgame.structure.BaseActivity;
import com.mathgame.util.Transition;

public class WebViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webViewInvoice = findViewById(R.id.webViewInvoice);
        TextView tvTitle = findViewById(R.id.tvTitle);
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            String webUrl = mBundle.getString(Keys.Prefs.KEY_WEB_URL);
            webViewInvoice.loadDataWithBaseURL(null, webUrl, "text/html", "utf-8", null);
        }
    }

    @Override
    public String getToolbarTitle() {
        return getIntent().getStringExtra(Keys.Prefs.KEY_TITLE);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_webview;
    }

    @Override
    public void onBackPressed() {
        Transition.exit(this);
    }
}

