package io.tanjundang.study;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.tanjundang.study.base.BaseActivity;
import io.tanjundang.study.common.tools.CommonDialog;
import io.tanjundang.study.common.tools.Functions;
import io.tanjundang.study.common.tools.LogTool;
import io.tanjundang.study.common.tools.PermissionTool;
import io.tanjundang.study.common.tools.ThreadManageTool;
import io.tanjundang.study.knowledge.audio.AudioRecordActivity;
import rx.internal.util.RxThreadFactory;

public class TestActivity extends BaseActivity {

    TextView tvMsg;
    ImageView ivVoice;
    ArrayList permissionList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_test);
        tvMsg = (TextView) findViewById(R.id.tvMsg);
        ivVoice = (ImageView) findViewById(R.id.ivVoice);
    }

    int number_of_cores = Runtime.getRuntime().availableProcessors();
    int keep_alive_time = 1;
    TimeUnit keep_alive_time_unit = TimeUnit.SECONDS;
    BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();

    ExecutorService executorService = new ThreadPoolExecutor(
            number_of_cores,
            number_of_cores * 2,
            keep_alive_time,
            keep_alive_time_unit,
            taskQueue,
            Executors.defaultThreadFactory(), new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            LogTool.v(TAG, "task was rejected");
        }
    });

    @Override
    protected void initData() {
        permissionList.add(Manifest.permission.WRITE_CONTACTS);
        permissionList.add(Manifest.permission.RECORD_AUDIO);
        permissionList.add(Manifest.permission.INTERNET);
        permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    public void start() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    long id = Thread.currentThread().getId();
                    LogTool.v(TAG, "\nThreadName:" + Thread.currentThread().getName() + "\nThreadId:" + id + "\nCount:" + Thread.activeCount() + "\nnum:" + i);
                }
            }
        });
    }

    public void startThread() {
        ThreadManageTool.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    long id = Thread.currentThread().getId();
                    LogTool.v(TAG, "\nThreadName:" + Thread.currentThread().getName() + "\nThreadId:" + id + "\nCount:" + Thread.activeCount() + "\nnum:" + i);
                }
            }
        });
    }

    public void checkPermission(final View view) {
        PermissionTool.getInstance(this).requestPermissions(permissionList, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dothing();
                start(view);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    public void start(View v) {

        showDiaglog();
    }


    public void TEST1(View v) {
        start();
    }

    public void showDiaglog() {
        CommonDialog.Builder dialog = new CommonDialog.Builder(this);
        dialog.setTitle("666")
                .setContent("abc")
                .setPositiveListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Functions.toast("FUCK");
                    }
                }).build().show();


    }


    public void TEST2(View v) {
        startThread();
    }


    public void TEST3(View v) {
    }

    public void TEST4(View v) {
    }

    public void voice(View v) {
        Intent intent = new Intent(this, AudioRecordActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

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
        PermissionTool.getInstance(this).onRequestPermissionsResult(permissionList, requestCode, permissions, grantResults);
    }
}

