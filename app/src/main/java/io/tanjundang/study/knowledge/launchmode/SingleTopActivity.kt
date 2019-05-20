package io.tanjundang.study.knowledge.launchmode

import android.os.Bundle
import android.view.View

import io.tanjundang.study.R
import io.tanjundang.study.base.BaseActivity

class SingleTopActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        setContentView(R.layout.activity_single_top)
    }

    override fun initData() {

    }

    fun SkipToOne(v: View) {
        OneActivity.SkipToActivityOne(this, LaunchModeActivity.SINGLETOP)
    }

}
