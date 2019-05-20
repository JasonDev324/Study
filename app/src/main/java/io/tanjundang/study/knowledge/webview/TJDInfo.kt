package io.tanjundang.study.knowledge.webview

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.webkit.JavascriptInterface

import io.tanjundang.study.common.tools.DialogTool
import io.tanjundang.study.common.tools.Functions

/**
 * Author: TanJunDang
 * Email: TanJunDang324@gmail.com
 * Date: 2016/10/15
 */

class TJDInfo(internal var context: Context) {

    @JavascriptInterface
    fun showTJDMsg() {
        DialogTool.getInstance().showDialog(context as AppCompatActivity, "JS与Android交互", "", DialogInterface.OnClickListener { dialog, which -> Functions.toast("JS调用Android方法成功") }, null)
    }

    @JavascriptInterface
    fun getMessageFromJS(msg: String) {
        Functions.toast(msg)
    }


}
