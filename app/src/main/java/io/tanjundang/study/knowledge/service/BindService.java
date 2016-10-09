package io.tanjundang.study.knowledge.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;

import io.tanjundang.study.common.tools.Functions;
import io.tanjundang.study.common.tools.LogTool;

public class BindService extends Service {
    private BindService service;
    private TestBinder binder = new TestBinder();
    private int count = 0;

    public class TestBinder extends Binder {
        //通过Binder对象来获取服务对象，从而调用服务里的方法
        public BindService getService() {
            if (service == null) {
                service = new BindService();
            }
            return service;
        }
    }

    private boolean closeThread;

    public BindService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!closeThread) {
                    LogTool.e("BindService", "count:" + count);
                    count++;
                }
            }
        }).start();
    }

    @Override
    public TestBinder onBind(Intent intent) {
        LogTool.e("BindService", "onBind");
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.closeThread = true;
        LogTool.e("BindService", "onDestroy");
        Functions.toast("BindService已停止");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogTool.e("BindService", "onUnbind");
        return true;
    }


    public void startDownload() {
        Functions.toast("开始执行耗时任务，点击UnbindService按钮或返回按钮可取消任务");
    }

}
