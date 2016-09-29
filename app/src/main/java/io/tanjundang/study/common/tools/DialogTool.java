package io.tanjundang.study.common.tools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import io.tanjundang.study.R;


/**
 * Developer: TanJunDang
 * Email: TanJunDang324@gmail.com
 * 对话框工具类
 * 需要翻转的地方，需要在onCreate中再次调用方法
 * 遇到的问题？
 * 如何封装一个DialogTool，在其屏幕翻转时，利用旧Fragment重构界面？
 */
public class DialogTool {

    private final String DIALOG_TAG = "DialogToolFragment";

    /**
     * 单例模式初始化工具
     *
     * @return
     */
    DialogToolFragment dialog;

    private static class Holder {
        private static DialogTool INSTANCE = new DialogTool();
    }

    DialogTool() {
    }

    public static DialogTool getInstance() {
        return Holder.INSTANCE;
    }


    public void showDialog(AppCompatActivity context, String title, String msg, final DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        /**
         * 方法一 旋转屏幕消失
         */
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setIcon(R.mipmap.ic_launcher);
//        builder.setCancelable(false);
//        builder.setTitle(title);
//        builder.setMessage(msg);
//        builder.setPositiveButton("确定", positiveListener);
//        builder.setNegativeButton("取消", negativeListener);
//        builder.create().show();
        /**
         * 方法二 旋转屏幕不消失，建议使用
         */

        dialog = new DialogToolFragment(title, msg, positiveListener, negativeListener);
        dialog.setCancelable(false);
        dialog.setRetainInstance(false);//Fragment忽略重建，true设置旋转屏幕后消失。
        dialog.show(context.getSupportFragmentManager(), DIALOG_TAG);
    }

    /**
     * 当需要重新构建屏幕的时候调用该方法
     *
     * @param context
     * @param title
     * @param msg
     * @param positiveListener
     * @param negativeListener
     */
    public void setRetainBundle(FragmentActivity context, String title, String msg, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        dialog = (DialogToolFragment) context.getSupportFragmentManager().findFragmentByTag(DIALOG_TAG);//找到重构前的fragment
        dialog.setTitle(title);
        dialog.setMsg(msg);
        dialog.setNegativeListener(negativeListener);
        dialog.setPositiveListener(positiveListener);
    }


    public static class DialogToolFragment extends DialogFragment {

        String title;
        String msg;
        DialogInterface.OnClickListener positiveListener;
        DialogInterface.OnClickListener negativeListener;


        public DialogToolFragment() {
        }

        public DialogToolFragment(String title, String msg, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
            this.title = title;
            this.msg = msg;
            this.positiveListener = positiveListener;
            this.negativeListener = negativeListener;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            builder.setIcon(R.mipmap.ic_launcher);
            builder.setTitle(title);
            builder.setMessage(msg);
            builder.setPositiveButton("确定", positiveListener);
            builder.setNegativeButton("取消", negativeListener);
            return builder.create();
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public DialogInterface.OnClickListener getPositiveListener() {
            return positiveListener;
        }

        public void setPositiveListener(DialogInterface.OnClickListener positiveListener) {
            this.positiveListener = positiveListener;
        }

        public DialogInterface.OnClickListener getNegativeListener() {
            return negativeListener;
        }

        public void setNegativeListener(DialogInterface.OnClickListener negativeListener) {
            this.negativeListener = negativeListener;
        }
    }

}
