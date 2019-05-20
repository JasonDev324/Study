package io.tanjundang.study.knowledge.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import io.tanjundang.study.common.tools.Functions
import io.tanjundang.study.common.tools.LogTool

/**
 * @Author: TanJunDang
 * @Date: 2019/5/15
 * @Description:
 */

class BindService : Service() {
    var service: BindService? = null
    private val binder = TestBinder()
    private var count = 0

    private var closeThread: Boolean = false

    inner class TestBinder : Binder() {
        //通过Binder对象来获取服务对象，从而调用服务里的方法
        fun get(): BindService {
            if (service == null) {
                service = BindService()
            }
            return service as BindService
        }
    }

    override fun onCreate() {
        super.onCreate()
        Thread(Runnable {
            while (!closeThread) {
                LogTool.e("BindService", "count:$count")
                count++
            }
        }).start()
    }

    override fun onBind(intent: Intent): TestBinder? {
        LogTool.e("BindService", "onBind")
        return binder
    }

    override fun onDestroy() {
        super.onDestroy()
        this.closeThread = true
        LogTool.e("BindService", "onDestroy")
        Functions.toast("BindService已停止")
    }

    override fun onUnbind(intent: Intent): Boolean {
        LogTool.e("BindService", "onUnbind")
        return true
    }


    fun startDownload() {
        Functions.toast("开始执行耗时任务，点击UnbindService按钮或返回按钮可取消任务")
    }

}
