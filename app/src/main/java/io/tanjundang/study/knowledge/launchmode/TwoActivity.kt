package io.tanjundang.study.knowledge.launchmode

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

import io.tanjundang.study.R
import io.tanjundang.study.base.BaseActivity
import io.tanjundang.study.knowledge.launchmode.ThreeActivity.Companion.SkipToActivityThree

class TwoActivity : BaseActivity() {

    private var type = ""
    internal var tvTest: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        setContentView(R.layout.activity_single_top_two)
        tvTest = findViewById<View>(R.id.tvTest) as TextView
    }

    override fun initData() {
        tvTest!!.text = "当前Activity实例：$this"
        type = intent.getStringExtra("type")
    }

    fun SkipToThree(v: View) {
        SkipToActivityThree(this, type)
    }

    companion object {
        fun SkipToActivityTwo(context: Context, type: String) {
            val intent = Intent(context, TwoActivity::class.java)
            intent.putExtra("type", type)
            context.startActivity(intent)
        }
    }
}
