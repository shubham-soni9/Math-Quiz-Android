package com.mathgame.activity;


import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.mathgame.R;
import com.mathgame.appdata.Keys;
import com.mathgame.structure.BaseActivity;
import com.mathgame.util.Transition;

public class WebViewActivity extends BaseActivity {
    private final int iBack = R.id.llBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        WebView webViewInvoice = findViewById(R.id.webViewInvoice);
        TextView tvTitle = findViewById(R.id.tvTitle);
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            String title = mBundle.getString(mBundle.getString(Keys.Prefs.KEY_TITLE));
            String webUrl = mBundle.getString(mBundle.getString(Keys.Prefs.KEY_WEB_URL));
            webViewInvoice.loadDataWithBaseURL(null, webUrl, "text/html", "utf-8", null);
            tvTitle.setText(title);
        }
        findViewById(iBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Transition.exit(this);
    }
}

