package io.tanjundang.study.common.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import io.tanjundang.study.R;


/**
 * Developer: TanJunDang
 * Email: TanJunDang324@gmail.com
 * 对话框工具类
 */
public class DialogTool {

    /**
     * 单例模式初始化工具
     *
     * @return
     */

    public static void showDialog(Context context, String title, String msg, final DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton("确定", positiveListener);
        builder.setNegativeButton("取消", negativeListener);
        builder.create().show();
    }

}
