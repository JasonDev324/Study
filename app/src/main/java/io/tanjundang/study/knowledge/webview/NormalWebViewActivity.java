//package io.tanjundang.study.knowledge.webview;
//
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.view.View;
//import android.webkit.WebChromeClient;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.TextView;
//
//import io.tanjundang.study.R;
//import io.tanjundang.study.base.BaseActivity;
//import io.tanjundang.study.common.tools.Functions;
//import io.tanjundang.study.common.tools.LogTool;
//
//public class NormalWebViewActivity extends BaseActivity {
//    private WebView webView;
//    private TextView tvProgress;
//    private long exitTime = 0;
//    private final String LOAD_URL = "https://zhidao.baidu.com/";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    protected void initView() {
//        setContentView(R.layout.activity_normal_web_view);
//        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
//        webView = (WebView) findViewById(R.id.webView);
//        tvProgress = (TextView) findViewById(R.id.tvProgress);
//    }
//
//    public void reload(View v) {
//        webView.reload();
//    }
//
//    public void clearCache(View v) {
//        webView.clearCache(true);
//    }
//
//    @Override
//    protected void initData() {
//        webView.loadUrl(LOAD_URL);
//        webView.setWebChromeClient(new WebChromeClient() {
//
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                super.onProgressChanged(view, newProgress);
//                tvProgress.setText(String.format("加载进度：%d", newProgress));
//                LogTool.e("TAG", newProgress + "");
//            }
//
//            @Override
//            public void onReceivedTitle(WebView view, String title) {
//                getSupportActionBar().setTitle(title);
//            }
//        });
////若设置了webViewClient，则从webView启动新的页面，没设置则启动浏览器来加载url
//        webView.setWebViewClient(new WebViewClient() {
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                tvProgress.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                tvProgress.setVisibility(View.GONE);
//            }
//        });
//
//        WebSettings settings = webView.getSettings();
//        settings.setUseWideViewPort(true);//设定支持viewport
//        settings.setLoadWithOverviewMode(true);   //两句结合使用使页面自适应屏幕
//
//        settings.setBuiltInZoomControls(true);//设定支持缩放
//        settings.setDisplayZoomControls(true);//是否显示缩放按钮 true为显示
////        settings.setSupportZoom(true);//这方法没什么用，但是如果设置为false 则上述setBuiltInZoomControls无效
//
//
//        // 开启DOM storage API 功能
//        settings.setDomStorageEnabled(true);
//        // 开启database storage API功能
//        settings.setDatabaseEnabled(true);
//
//        settings.setAppCacheEnabled(true);
//        settings.setAppCachePath(getCacheDir().getAbsolutePath() + "/webCache");
//        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (webView.canGoBack()) {
//            webView.goBack();
//        } else {
//            if ((System.currentTimeMillis() - exitTime) > 2000) {
//                Functions.toast("再按一次退出程序");
//                exitTime = System.currentTimeMillis();
//            } else {
//                super.onBackPressed();
//            }
//
//        }
//    }
//}
