package io.tanjundang.study.test.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import io.tanjundang.study.common.tools.LogTool;

/**
 * 该Service用于打印0-X的数字
 * X是Activity传来的数字
 */
public class CalcService extends Service {
    private int total;

    public CalcService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogTool.e("CalcService", "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogTool.e("CalcService", "onStartCommand");
        total = intent.getIntExtra("msg", 100);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < total; i++) {
                    LogTool.e("CalcService", i);
                    if (i == total) {
                        stopSelf();
                    }
                }
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogTool.e("CalcService", "onDestroy");
    }
}
