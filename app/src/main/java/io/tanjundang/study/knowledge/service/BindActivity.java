package io.tanjundang.study.knowledge.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import io.tanjundang.study.R;
import io.tanjundang.study.base.BaseActivity;
import io.tanjundang.study.common.tools.Functions;
import io.tanjundang.study.common.tools.LogTool;

public class BindActivity extends BaseActivity {


    private BindService.TestBinder binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_bind);
    }

    @Override
    protected void initData() {

    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (BindService.TestBinder) service;
            binder.getService().startDownload();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Functions.toast("断开连接");
            LogTool.e("BindService", "serviceDisconnected");
        }
    };

    public void BindService(View v) {
        Intent intent = new Intent(this, BindService.class);
        bindService(intent, connection, Service.BIND_AUTO_CREATE);
    }

    public void UnbindService(View v) {
        if (binder != null) {
            unbindService(connection);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (binder != null) {//防止未绑定服务，点击取消绑定导致的崩溃
            unbindService(connection);
        }
    }
}
