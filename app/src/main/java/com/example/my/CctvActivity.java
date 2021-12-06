package com.example.my;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

public class CctvActivity extends AppCompatActivity {

    private WebView webView;
    private String url = "192.168.2.211:5000";
    private String htmlCode =
            "<html><head><style type='text/css'>body{margin:auto;text-align:center;}"+
                    "img{width:100%25;}div{overflow:hidden;}</style></head>" +
                    "<body><div><img src='192.168.2.211:5000'/></div></body></html>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctv);

        webView = (WebView) findViewById(R.id.cctvweb);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url); // LoadData
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClientClass());

        ImageButton btnback = findViewById(R.id.cctvhome);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}