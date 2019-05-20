package io.tanjundang.study.knowledge.webview

import android.os.Bundle
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView


import io.tanjundang.study.R
import io.tanjundang.study.base.BaseActivity

class JSWebViewActivity : BaseActivity() {

    private val LOCAL_URL = "file:///android_asset/jsweb.html"
    private var webView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        setContentView(R.layout.activity_jsweb_view)
        webView = findViewById<View>(R.id.webView) as WebView
        val webSettings = webView!!.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.defaultTextEncodingName = "UTF-8"
        webView!!.addJavascriptInterface(TJDInfo(this), "tjd")
        webView!!.loadUrl(LOCAL_URL)
    }

    /**
     * android传递数据给JS，JS获取数据后，调用Android中的方法并显示android传来的数据
     *
     * @param v
     */
    fun callJS(v: View) {
        val msg = "Android传递数据给JS"
        //调用js中的函数：showInfoFromJava(msg)
        webView!!.loadUrl("javascript:showInfoFromJava('$msg')")
        //        webView.loadUrl(LOCAL_URL);
    }

    override fun initData() {

    }
}
