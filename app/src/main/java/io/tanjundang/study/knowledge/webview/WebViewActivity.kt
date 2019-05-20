package io.tanjundang.study.knowledge.webview

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView

import io.tanjundang.study.R
import io.tanjundang.study.base.BaseActivity
import io.tanjundang.study.common.tools.Functions
import io.tanjundang.study.common.tools.LogTool

class WebViewActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        setContentView(R.layout.activity_web_view)
    }

    fun NormalWebView(v: View) {
        Functions.startActivity(NormalWebViewActivity::class.java)
    }

    fun JSWebView(v: View) {
        Functions.startActivity(JSWebViewActivity::class.java)
    }

    override fun initData() {}


}
