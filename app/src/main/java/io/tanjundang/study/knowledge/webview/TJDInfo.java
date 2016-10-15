package io.tanjundang.study.knowledge.webview;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;

import io.tanjundang.study.common.tools.DialogTool;
import io.tanjundang.study.common.tools.Functions;

/**
 * Author: TanJunDang
 * Email: TanJunDang324@gmail.com
 * Date: 2016/10/15
 */

public class TJDInfo {

    Context context;

    public TJDInfo(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void showTJDMsg() {
        DialogTool.getInstance().showDialog((AppCompatActivity) context, "JS与Android交互", "", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Functions.toast("JS调用Android方法成功");
            }
        }, null);
    }
}
