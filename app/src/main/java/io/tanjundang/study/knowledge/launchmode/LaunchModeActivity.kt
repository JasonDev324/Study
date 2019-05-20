package io.tanjundang.study.knowledge.launchmode

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import io.tanjundang.study.R
import io.tanjundang.study.base.BaseActivity

class LaunchModeActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        setContentView(R.layout.activity_launch_mode)
    }

    override fun initData() {

    }

    fun Standard(v: View) {
        val intent = Intent(this, StandardActivity::class.java)
        startActivity(intent)
    }

    fun SingleTop(v: View) {
        val intent = Intent(this, SingleTopActivity::class.java)
        startActivity(intent)
    }

    fun SingleTask(v: View) {
        val intent = Intent(this, SingleTaskActivity::class.java)
        startActivity(intent)
    }

    fun SingleInstance(v: View) {
        val intent = Intent(this, SingleTaskActivity::class.java)
        startActivity(intent)
    }

    companion object {

        var STANDARD = "STANDARD"
        var SINGLETOP = "SINGLETOP"
        var SINGLETASK = "SINGLETASK"
        var SINGLEINSTANCE = "SINGLEINSTANCE"
    }
}
