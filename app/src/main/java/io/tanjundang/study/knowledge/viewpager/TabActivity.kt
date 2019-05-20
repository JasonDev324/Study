package io.tanjundang.study.knowledge.viewpager

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import io.tanjundang.study.MainActivity
import io.tanjundang.study.R

class TabActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)
    }

    fun SimpleTab(v: View) {
        val intent = Intent(this, SimpleTabActivity::class.java)
        startActivity(intent)
    }

    fun CustomTab(v: View) {
        val intent = Intent(this, CustomTabActivity::class.java)
        startActivity(intent)
    }
}
