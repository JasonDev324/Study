package io.tanjundang.study.knowledge.service

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import io.tanjundang.study.R
import io.tanjundang.study.base.BaseActivity
import io.tanjundang.study.common.tools.Functions
import io.tanjundang.study.common.tools.LogTool

class BindActivity : BaseActivity() {


    private var hadUnbind = false
    private var binder: BindService.TestBinder? = null

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            binder = service as BindService.TestBinder
            binder!!.get().startDownload()
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Functions.toast("断开连接")
            LogTool.e("BindService", "serviceDisconnected")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        setContentView(R.layout.activity_bind)
    }

    override fun initData() {

    }

    fun BindService(v: View) {
        val intent = Intent(this, BindService::class.java)
        bindService(intent, connection, Service.BIND_AUTO_CREATE)
    }

    fun UnbindService(v: View) {
        hadUnbind = true;
        if (binder != null) {
            unbindService(connection)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (binder != null) {//防止未绑定服务，点击取消绑定导致的崩溃
            if (connection != null && hadUnbind == false)
                unbindService(connection)
        }
    }
}
