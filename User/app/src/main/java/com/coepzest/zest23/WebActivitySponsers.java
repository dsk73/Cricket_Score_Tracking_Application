package com.coepzest.zest23;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.coepzest.zest23.R;


public class WebActivitySponsers extends AppCompatActivity {
    WebView webView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_sponsers);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Zest 24");
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
        progressBar = findViewById(R.id.progressBar4);
        progressBar.setMax(100);
        webView = findViewById(R.id.web_view4);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://coepzest.org/accomodation/");
        progressBar.setProgress(0);
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}