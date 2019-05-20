package io.tanjundang.study.knowledge.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import io.tanjundang.study.R
import io.tanjundang.study.base.BaseActivity

class RemoteActivity : BaseActivity() {

    private var mBinder: RemoteAidlInterface? = null
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            mBinder = RemoteAidlInterface.Stub.asInterface(iBinder)
            try {
                mBinder!!.calcMsg()
            } catch (e: RemoteException) {
                e.printStackTrace()
            }

        }

        override fun onServiceDisconnected(componentName: ComponentName) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        setContentView(R.layout.activity_remote)
    }

    override fun initData() {

    }

    fun StartRemoteService(v: View) {
        val intent = Intent(this, RemoteService::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    fun UnbindRemoteService(v: View) {
        if (mBinder != null) {
            unbindService(connection)
            mBinder = null
        }
    }
}
