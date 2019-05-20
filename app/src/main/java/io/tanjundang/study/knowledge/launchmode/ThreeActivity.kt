package io.tanjundang.study.knowledge.launchmode

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import io.tanjundang.study.R
import io.tanjundang.study.base.BaseActivity
import io.tanjundang.study.common.tools.Functions

/**
 * @Author: TanJunDang
 * @Date: 2019/5/15
 * @Description:
 */
class ThreeActivity : BaseActivity() {

    var type: String = ""
    var tvTest: TextView? = null
    var rootview: LinearLayout? = null

    override fun initView() {
        setContentView(R.layout.activity_single_top_three)
        tvTest = findViewById<View>(R.id.tvTest) as TextView
        rootview = findViewById<View>(R.id.activity_single_top_three) as LinearLayout
    }

    override fun initData() {
        type = intent.getStringExtra("type")
        tvTest!!.text = "当前Activity实例 $this"
        if (type.equals(LaunchModeActivity.Companion.SINGLETASK)) {
            for (i in 0 until rootview!!.getChildCount()) {
                rootview!!.getChildAt(i).visibility = View.GONE
            }
            rootview!!.getChildAt(1).visibility = View.VISIBLE
            rootview!!.getChildAt(2).visibility = View.VISIBLE
        }
    }

    companion object {
        fun SkipToActivityThree(context: Context, type: String) {
            val intent = Intent(context, ThreeActivity::class.java)
            intent.putExtra("type", type)
            context.startActivity(intent)
        }
    }

    fun SkipToThree(v: View) {
        val intent = Intent(this, ThreeActivity::class.java)
        startActivity(intent)
    }

    fun SkipToFour(v: View) {
        val intent = Intent(this, FourActivity::class.java)
        startActivity(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Functions.toast("复用旧实例，不进行跳转，但是执行onNewIntent方法")
    }
}