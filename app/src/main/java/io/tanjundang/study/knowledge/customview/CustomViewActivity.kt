package io.tanjundang.study.knowledge.customview

import android.os.Bundle
import android.view.View

import io.tanjundang.study.R
import io.tanjundang.study.base.BaseActivity

class CustomViewActivity : BaseActivity() {
    internal var batteryView: BatteryView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        setContentView(R.layout.activity_custom_view)
        batteryView = findViewById<View>(R.id.batteryView) as BatteryView
        batteryView!!.setOnClickListener { batteryView!!.updateColor() }
    }

    override fun initData() {

    }
}
