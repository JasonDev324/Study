package io.tanjundang.study.knowledge.animation

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation

/**
 * Developer: TanJunDang
 * Email: TanJunDang324@gmail.com
 * Date: 2016/8/26
 */

class BtnAnimation(internal var view: View) : Animation() {
    internal var width: Int = 0
    internal var widthto: Int = 0

    init {
        width = view.width
        if (width == view.height) {
            widthto = 4 * width
        } else {
            widthto = view.height
        }
    }

    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        super.applyTransformation(interpolatedTime, t)
        view.layoutParams.width = width + ((widthto - width) * interpolatedTime).toInt()
        view.requestLayout()
    }


}
