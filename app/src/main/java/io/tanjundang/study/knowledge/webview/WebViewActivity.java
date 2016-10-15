package io.tanjundang.study.knowledge.webview;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import io.tanjundang.study.R;
import io.tanjundang.study.base.BaseActivity;
import io.tanjundang.study.common.tools.Functions;
import io.tanjundang.study.common.tools.LogTool;

public class WebViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_web_view);
    }

    public void NormalWebView(View v) {
        Functions.startActivity(NormalWebViewActivity.class);
    }

    public void JSWebView(View v) {
        Functions.startActivity(JSWebViewActivity.class);
    }

    @Override
    protected void initData() {
    }


}
