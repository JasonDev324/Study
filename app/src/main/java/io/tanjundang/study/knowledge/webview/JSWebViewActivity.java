package io.tanjundang.study.knowledge.webview;

import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
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

    /**
     * android传递数据给JS，JS获取数据后，调用Android中的方法并显示android传来的数据
     *
     * @param v
     */
    public void callJS(View v) {
        String msg = "Android传递数据给JS";
        //调用js中的函数：showInfoFromJava(msg)
        webView.loadUrl("javascript:showInfoFromJava('" + msg + "')");
//        webView.loadUrl(LOCAL_URL);
    }

    @Override
    protected void initData() {

    }
}
