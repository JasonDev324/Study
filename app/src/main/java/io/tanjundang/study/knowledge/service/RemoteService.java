package io.tanjundang.study.knowledge.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.RemoteException;

import io.tanjundang.study.common.tools.LogTool;

public class RemoteService extends Service {
    public RemoteService() {
    }


    RemoteAidlInterface.Stub mBinder = new RemoteAidlInterface.Stub() {
        @Override
        public void calcMsg() throws RemoteException {
            LogTool.e("RemoteService", "66666");
        }
    };

    @Override
    public Binder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }


}
