package io.tanjundang.study.knowledge;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import io.tanjundang.study.R;
import io.tanjundang.study.common.tools.DialogTool;
import io.tanjundang.study.common.tools.Functions;
import io.tanjundang.study.common.tools.LogTool;
import io.tanjundang.study.common.tools.PermissionTool;

public class TestActivity extends AppCompatActivity {

    TextView tvMsg;
    private String title;
    private String msg;
    private DialogInterface.OnClickListener positilistener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        tvMsg = (TextView) findViewById(R.id.tvMsg);
//        if (savedInstanceState != null) {
//            title = savedInstanceState.getString("title");
//            msg = savedInstanceState.getString("msg");
//            DialogTool.getInstance().setRetainBundle(this, title, msg, positilistener, null);
//        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("title", title);
        outState.putString("msg", msg);
    }

    public void checkPermission(View view) {
//        getPermissiton();
//        if (!PermissionTool.getInstance(this).needRequestPermission("", Manifest.permission.WRITE_CONTACTS)) {
//            dothing();
//        }
//        PermissionTool.getInstance(this).NoNeedPermission(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dothing();
//            }
//        }).needRequestPermission("", Manifest.permission.WRITE_CONTACTS, 600);

//        title = "顶你肺";
//        msg = "6666";
//        positilistener = new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Functions.toast("可以哇");
//            }
//        };
//        DialogTool.getInstance().showDialog(this, title, msg, positilistener, null);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    //
    public void getPermissiton() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                dothing();
            } else {
                LogTool.e("permission", shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS));
//                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) getApplicationContext(), Manifest.permission.WRITE_CONTACTS)) {
                LogTool.e("permission", "" + ActivityCompat.shouldShowRequestPermissionRationale(TestActivity.this, Manifest.permission.WRITE_CONTACTS));
                LogTool.e("permission", "" + shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS));
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    DialogTool.getInstance().showDialog(this, null, "获取必要的权限", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS}, 666);
                        }
                    }, null);
                } else {
                    requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS}, 666);
                    //一开始执行这里，请求权限
                }
            }
        }
    }

    private void dothing() {
        ContentResolver resolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        Cursor cursor = resolver.query(uri, null, null, null, null);
        int i = 0;
        StringBuffer sb = new StringBuffer("");
        while (cursor.moveToNext()) {
            i++;
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            sb.append("名字：" + name + "手机：" + phone + "\n");
            if (i == 10) {
                break;
            }
        }
        tvMsg.setText(sb.toString());
        Functions.toast("Success");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (PermissionTool.getInstance(this).PermissionGrant(requestCode, permissions, grantResults)) {
            dothing();
        } else {
            Functions.toast("请重新打开设置->应用进行权限分配");
        }
//        if (requestCode == 600) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                dothing();
//            } else {
//                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
//                    //当点击拒绝且不再询问的时候执行该处代码
//                    Functions.toast("请重新打开设置->应用进行权限分配");
//                }
//            }
//            return;
//        } else {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
    }
}

