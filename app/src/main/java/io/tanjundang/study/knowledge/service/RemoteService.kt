package io.tanjundang.study.knowledge.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteException
import io.tanjundang.study.common.tools.LogTool

/**
 * @Author: TanJunDang
 * @Date: 2019/5/15
 * @Description:
 */

class RemoteService : Service() {

    var mBinder = object : RemoteAidlInterface.Stub() {
        @Throws(RemoteException::class)
        override fun calcMsg() {
            LogTool.e("RemoteService", "66666")
        }
    }


    override fun onBind(intent: Intent?): IBinder {
        return mBinder;
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }
}