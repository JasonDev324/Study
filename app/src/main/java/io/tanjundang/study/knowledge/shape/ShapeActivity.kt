package io.tanjundang.study.knowledge.shape

import android.graphics.drawable.AnimationDrawable
import android.view.View
import android.widget.Button
import android.widget.ImageView
import io.tanjundang.study.R
import io.tanjundang.study.base.BaseActivity

/**
 * @Author: TanJunDang
 * @Date: 2019/5/15
 * @Description:
 */
class ShapeActivity : BaseActivity(), View.OnClickListener {

    var ivAnimation: ImageView? = null
    var animationDrawable: AnimationDrawable? = null

    override fun initView() {
        setContentView(R.layout.activity_shape)
        ivAnimation = findViewById<View>(R.id.ivAnimation) as ImageView
        ivAnimation!!.setOnClickListener(this)
    }

    override fun initData() {
        animationDrawable = ivAnimation!!.background as AnimationDrawable?;
    }

    override fun onClick(v: View?) {
        if (animationDrawable!!.isRunning) {
            animationDrawable!!.stop()
        } else {
            animationDrawable!!.start()
        }
    }

}