package io.tanjundang.study.knowledge.webview

import android.annotation.SuppressLint
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

class NormalWebViewActivity : BaseActivity() {
    private var webView: WebView? = null
    private var tvProgress: TextView? = null
    private var exitTime: Long = 0
    private val LOAD_URL = "https://zhidao.baidu.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("RestrictedApi")
    override fun initView() {
        setContentView(R.layout.activity_normal_web_view)
        webView = findViewById<View>(R.id.webView) as WebView
        tvProgress = findViewById<View>(R.id.tvProgress) as TextView
    }

    fun reload(v: View) {
        webView!!.reload()
    }

    fun clearCache(v: View) {
        webView!!.clearCache(true)
    }

    override fun initData() {
        webView!!.loadUrl(LOAD_URL)
        webView!!.webChromeClient = object : WebChromeClient() {

            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                tvProgress!!.text = String.format("加载进度：%d", newProgress)
                LogTool.e("TAG", newProgress.toString() + "")
            }

            override fun onReceivedTitle(view: WebView, title: String) {
                supportActionBar!!.title = title
            }
        }

        //若设置了webViewClient，则从webView启动新的页面，没设置则启动浏览器来加载url
        webView!!.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                tvProgress!!.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                tvProgress!!.visibility = View.GONE
            }
        }

        val settings = webView!!.settings
        settings.useWideViewPort = true//设定支持viewport
        settings.loadWithOverviewMode = true   //两句结合使用使页面自适应屏幕

        settings.builtInZoomControls = true//设定支持缩放
        settings.displayZoomControls = true//是否显示缩放按钮 true为显示
        //        settings.setSupportZoom(true);//这方法没什么用，但是如果设置为false 则上述setBuiltInZoomControls无效


        // 开启DOM storage API 功能
        settings.domStorageEnabled = true
        // 开启database storage API功能
        settings.databaseEnabled = true

        settings.setAppCacheEnabled(true)
        settings.setAppCachePath(cacheDir.absolutePath + "/webCache")
        settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
    }

    override fun onBackPressed() {
        if (webView!!.canGoBack()) {
            webView!!.goBack()
        } else {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Functions.toast("再按一次退出程序")
                exitTime = System.currentTimeMillis()
            } else {
                super.onBackPressed()
            }

        }
    }
}
