package io.tanjundang.study.knowledge.service

import android.content.Intent
import android.os.Bundle
import android.view.View

import io.tanjundang.study.R
import io.tanjundang.study.base.BaseActivity

/**
 * 两种启动Service的实例
 */
class ServiceActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        setContentView(R.layout.activity_service)
    }

    override fun initData() {

    }

    fun StartService(v: View) {
        //只需要指定跳转那个Service并调用startService方法即可，不需要添加Action
        val intent = Intent(this@ServiceActivity, CalcService::class.java)
        intent.putExtra("msg", 1000)
        startService(intent)
    }

    fun BindService(v: View) {
        val intent = Intent(this@ServiceActivity, BindActivity::class.java)
        startActivity(intent)
    }

    fun RemoteService(v: View) {
        val intent = Intent(this@ServiceActivity, RemoteActivity::class.java)
        startActivity(intent)
    }
}
