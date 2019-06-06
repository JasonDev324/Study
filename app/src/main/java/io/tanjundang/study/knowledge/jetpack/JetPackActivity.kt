package io.tanjundang.study.knowledge.jetpack

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import io.tanjundang.study.R
import io.tanjundang.study.knowledge.room.RoomActivity

/**
 * @Author: TanJunDang
 * @Date: 2019/6/6
 * @Description:
 */

class JetPackActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jetpack)
    }

    fun LifeCycle(v: View) {
        StartActivity(LifecycleActivity::class.java)
    }

    fun ViewModel(v: View) {
        StartActivity(LifecycleActivity::class.java)
    }

    fun Room(v: View) {
        StartActivity(RoomActivity::class.java)
    }

    fun StartActivity(cls: Class<*>) {
        val intent = Intent(this@JetPackActivity, cls)
        startActivity(intent)
    }


}