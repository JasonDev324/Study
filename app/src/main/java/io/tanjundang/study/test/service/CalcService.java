package io.tanjundang.study.test.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;

import io.tanjundang.study.MainActivity;
import io.tanjundang.study.R;
import io.tanjundang.study.common.tools.Functions;
import io.tanjundang.study.common.tools.LogTool;

/**
 * 类型：启动形式的Service
 * 作用：该Service用于打印0-2000的数字
 * 达到2000后自动停止服务
 */
public class CalcService extends Service {
    private int total;
    private boolean openService;

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
    public int onStartCommand(final Intent intent, int flags, final int startId) {
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setContentIntent(PendingIntent.getActivity(this, 0, new Intent(getApplicationContext(), MainActivity.class), 0));
        builder.setContentTitle("前台服务");
        //不知道什么鬼，不设置Icon，其他信息都不显示
        builder.setSmallIcon(R.drawable.ic_menu_camera);
        builder.setContentText("二级子标题");
        builder.setSubText("三级子标题");
        Notification notification = builder.build();
        startForeground(startId, notification);
        LogTool.e("CalcService", "onStartCommand");
        Functions.toast("Service已启动，请查看LOG");
        total = intent.getIntExtra("msg", 100);
        //启动线程执行耗时操作。通常是下载之类的任务
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!openService) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    LogTool.e("CalcService", total);
                    total++;
                    builder.setSubText("total:" + total);
                    if (total == 2000) {
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
        Functions.toast("CalcService已停止，请查看LOG");
        openService = true;
    }
}
