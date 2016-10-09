package io.tanjundang.study.common.tools;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;

import io.tanjundang.study.R;

/**
 * Developer: TanJunDang
 * Email: TanJunDang324@gmail.com
 * Date: 2016/9/28
 * 使用：
 * 1.在activity中调用needRequestPermission，在已获取权限时，做相应的处理
 * 2.在activity中重写onRequestPermissionsResult方法。
 *
 * @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
 * if (requestCode == PermissionTool.requestCode) {
 * if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
 * dothing();
 * } else {
 * if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
 * //当点击拒绝且不再询问的时候执行该处代码
 * Functions.toast("请重新打开设置->应用进行权限分配");
 * }
 * }
 * return;
 * } else {
 * super.onRequestPermissionsResult(requestCode, permissions, grantResults);
 * }
 * }
 */

public class PermissionTool {
    private static Context appContext;

    //静态内部类内部类里面实例化
    private static class Holder {
        public static PermissionTool INSTANCE = new PermissionTool();
    }

    public static PermissionTool getInstance(Context context) {
        appContext = context;
        return Holder.INSTANCE;
    }


    private boolean hasPermission(String permission) {
        return ContextCompat.checkSelfPermission(appContext, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * @param msg        要求该权限的描述
     * @param permission
     * @return
     */
    @TargetApi(Build.VERSION_CODES.M)
    public void needRequestPermission(String msg, String permission, int requestCode) {
        if (TextUtils.isEmpty(msg)) {
            msg = "需要分配相关的权限才能正常使用该功能";
        }
        if (!hasPermission(permission)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) appContext, permission)) {
                showExplainDialog(msg, permission, requestCode);
            } else {
                ActivityCompat.requestPermissions((Activity) appContext, new String[]{permission}, requestCode);
            }
        } else {
            listener.onClick(new View(appContext));
        }
    }

    View.OnClickListener listener;

    /**
     * 不需要权限时，直接调用方法
     *
     * @param listener
     * @return
     */
    public PermissionTool NoNeedPermission(View.OnClickListener listener) {
        this.listener = listener;
        return Holder.INSTANCE;
    }

    public boolean PermissionGrant(int requestCode, String[] permissions, int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public void showExplainDialog(String message, final String permission, final int requestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(appContext);
        builder.setMessage(message);
        builder.setIcon(R.drawable.ic_menu_gallery);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions((Activity) appContext, new String[]{permission}, requestCode);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

}
