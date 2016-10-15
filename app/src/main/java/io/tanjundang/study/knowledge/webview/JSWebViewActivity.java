package io.tanjundang.study.knowledge.webview;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;


import io.tanjundang.study.R;
import io.tanjundang.study.base.BaseActivity;

public class JSWebViewActivity extends BaseActivity {

    private final String LOCAL_URL = "file:///android_asset/jsweb.html";
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_jsweb_view);
        webView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        webView.addJavascriptInterface(new TJDInfo(this), "tjd");
        webView.loadUrl(LOCAL_URL);
    }

    @Override
    protected void initData() {

    }
}
