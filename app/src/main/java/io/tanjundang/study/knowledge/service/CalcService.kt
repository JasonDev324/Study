package io.tanjundang.study.knowledge.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.support.v7.app.NotificationCompat

import io.tanjundang.study.MainActivity
import io.tanjundang.study.R
import io.tanjundang.study.common.tools.Functions
import io.tanjundang.study.common.tools.LogTool

/**
 * 类型：启动形式的Service
 * 作用：该Service用于打印0-2000的数字
 * 达到2000后自动停止服务
 */
class CalcService : Service() {
    private var total: Int = 0
    private var openService: Boolean = false

    override fun onBind(intent: Intent): IBinder? {
        // TODO: Return the communication channel to the service.
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        LogTool.e("CalcService", "onCreate")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val builder = NotificationCompat.Builder(applicationContext)
        builder.setContentIntent(PendingIntent.getActivity(this, 0, Intent(applicationContext, MainActivity::class.java), 0))
        builder.setContentTitle("前台服务")
        //不知道什么鬼，不设置Icon，其他信息都不显示
        builder.setSmallIcon(R.drawable.ic_menu_camera)
        builder.setContentText("二级子标题")
        builder.setSubText("三级子标题")
        val notification = builder.build()
        startForeground(startId, notification)
        LogTool.e("CalcService", "onStartCommand")
        Functions.toast("Service已启动，请查看LOG")
        total = intent.getIntExtra("msg", 100)
        //启动线程执行耗时操作。通常是下载之类的任务
        Thread(Runnable {
            while (!openService) {
                try {
                    Thread.sleep(100)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                LogTool.e("CalcService", total)
                total++
                builder.setSubText("total:$total")
                if (total == 2000) {
                    stopSelf()
                }
            }
        }).start()


        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        LogTool.e("CalcService", "onDestroy")
        Functions.toast("CalcService已停止，请查看LOG")
        openService = true
    }
}
