package io.tanjundang.study.knowledge.launchmode

import android.os.Bundle
import android.view.View
import android.widget.TextView

import io.tanjundang.study.R
import io.tanjundang.study.base.BaseActivity

class FourActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        setContentView(R.layout.activity_four)
        val tvTest = findViewById<View>(R.id.tvTest) as TextView
        tvTest.text = "当前Activity实例：$this"
    }

    override fun initData() {

    }

    fun SkipToOne(v: View) {
        OneActivity.SkipToActivityOne(this, LaunchModeActivity.SINGLETASK)
    }
}
