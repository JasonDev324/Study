package io.tanjundang.study.knowledge.launchmode

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

import io.tanjundang.study.R
import io.tanjundang.study.base.BaseActivity

class StandardActivity : BaseActivity() {

    internal var textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        setContentView(R.layout.activity_standard)
        textView = findViewById<View>(R.id.textView) as TextView
    }

    override fun initData() {
        textView!!.text = this.toString() + ""
    }

    fun Standard(v: View) {
        val intent = Intent(this, StandardActivity::class.java)
        startActivity(intent)
    }
}
