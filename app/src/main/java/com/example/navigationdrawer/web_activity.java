package com.example.navigationdrawer;

import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class web_activity extends AppCompatActivity {
    private WebView myWebView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.webview);

        String url = getIntent().getStringExtra("url");
        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
        myWebView = (WebView) findViewById(R.id.newwebview);
        myWebView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
