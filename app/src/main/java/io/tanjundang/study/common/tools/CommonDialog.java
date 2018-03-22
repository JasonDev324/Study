package io.tanjundang.study.common.tools;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * @Author: TanJunDang
 * @Date: 2018/3/21
 * @Description: 使用Builder模式实现的通用Dialog工具
 * 使用方式
 * 1.创建Builder.
 * 2.调用build
 * 3.调用show
 */

public class CommonDialog extends DialogFragment {
    public CommonDialog() {
    }

    AppCompatActivity activity;
    Fragment fragment;
    private String title;
    private String content;
    private String confirmText;
    private String cancelText;
    //    cancelable属性不能通过builder来设置，需要创建dialog后才能设置
    private boolean cancelable = false;
    private DialogInterface.OnClickListener positiveListener;
    private DialogInterface.OnClickListener negativeListener;

    public CommonDialog(AppCompatActivity activity, String title, String content,
                        String confirmText, String cancelText,
                        boolean cancelable,
                        DialogInterface.OnClickListener positiveListener,
                        DialogInterface.OnClickListener negativeListener) {
        this.activity = activity;
        this.title = title;
        this.content = content;
        this.confirmText = confirmText;
        this.cancelText = cancelText;
        this.cancelable = cancelable;
        this.positiveListener = positiveListener;
        this.negativeListener = negativeListener;
    }

    public CommonDialog(Fragment fragment, String title, String content,
                        String confirmText, String cancelText,
                        boolean cancelable,
                        DialogInterface.OnClickListener positiveListener,
                        DialogInterface.OnClickListener negativeListener) {
        this.fragment = fragment;
        this.title = title;
        this.content = content;
        this.confirmText = confirmText;
        this.cancelText = cancelText;
        this.cancelable = cancelable;
        this.positiveListener = positiveListener;
        this.negativeListener = negativeListener;
    }

    @Override
    public boolean isCancelable() {
        return cancelable;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title)
                .setMessage(content)
                .setPositiveButton(confirmText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (positiveListener != null) positiveListener.onClick(dialog, which);
                    }
                })
                .setNegativeButton(cancelText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (negativeListener != null) negativeListener.onClick(dialog, which);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(cancelable);
        return dialog;
    }

    public void show() {
        if (activity != null) {
            show(activity.getSupportFragmentManager(), "CommonDialog");
        } else {
            show(fragment.getFragmentManager(), "CommonDialog");
        }
    }

    public static class Builder {
        private String title;
        private String content;
        private String confirmText = "Confirm";
        private String cancelText = "Cancel";
        private boolean isCancelable = false;
        private DialogInterface.OnClickListener positiveListener;
        private DialogInterface.OnClickListener negativeListener;
        AppCompatActivity activity;
        Fragment fragment;

        public Builder(AppCompatActivity activity) {
            this.activity = activity;
        }

        public Builder(Fragment fragment) {
            this.fragment = fragment;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.isCancelable = cancelable;
            return this;
        }

        public Builder setConfirmText(String confirmText) {
            this.confirmText = confirmText;
            return this;
        }

        public Builder setCancelText(String cancelText) {
            this.cancelText = cancelText;
            return this;
        }

        public Builder setPositiveListener(DialogInterface.OnClickListener positiveListener) {
            this.positiveListener = positiveListener;
            return this;
        }

        public Builder setNegativeListener(DialogInterface.OnClickListener negativeListener) {
            this.negativeListener = negativeListener;
            return this;
        }

        public CommonDialog build() {
            if (activity != null) {
                return new CommonDialog(activity, title, content, confirmText, cancelText, isCancelable, positiveListener, negativeListener);
            } else {
                return new CommonDialog(fragment, title, content, confirmText, cancelText, isCancelable, positiveListener, negativeListener);
            }
        }
    }
}