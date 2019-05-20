package io.tanjundang.study.knowledge.mvc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

import io.tanjundang.study.R

/**
 * @Author: TanJunDang
 * @Date: 2018/10/12
 * @Description: 视图层负责数据的显示，以及用户的操作响应
 */

class MVCActivity : AppCompatActivity() {
    internal var tvContent: TextView? = null
    internal var btnLoadData: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvc)
        tvContent = findViewById<View>(R.id.tvContent) as TextView
        btnLoadData = findViewById<View>(R.id.btnLoadData) as Button
        val controller = MVCController(this)
        btnLoadData!!.setOnClickListener { controller.loadData() }
    }

    fun updateUI(text: String) {
        tvContent!!.text = text
    }

}
