package io.tanjundang.study.knowledge.launchmode

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

import io.tanjundang.study.R
import io.tanjundang.study.base.BaseActivity
import io.tanjundang.study.knowledge.launchmode.TwoActivity.Companion.SkipToActivityTwo

class OneActivity : BaseActivity() {

    internal var type: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        setContentView(R.layout.activity_single_top_one)
        val tvTest = findViewById<View>(R.id.tvTest) as TextView
        tvTest.text = "当前Activity实例：$this"
    }

    override fun initData() {
        type = intent.getStringExtra("type")
    }

    fun SkipToTwo(v: View) {
        SkipToActivityTwo(this, type)
    }

    companion object {
        fun SkipToActivityOne(context: Context, type: String) {
            val intent = Intent(context, OneActivity::class.java)
            intent.putExtra("type", type)
            context.startActivity(intent)
        }
    }

}
