package io.tanjundang.study.test.animation;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Developer: TanJunDang
 * Email: TanJunDang324@gmail.com
 * Date: 2016/8/26
 */

public class BtnAnimation extends Animation {

    View view;
    int width;
    int widthto;

    public BtnAnimation(View view) {
        this.view = view;
        width = view.getWidth();
        if (width == view.getHeight()) {
            widthto = 4 * width;
        } else {
            widthto = view.getHeight();
        }
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        view.getLayoutParams().width = width + (int) ((widthto - width) * interpolatedTime);
        view.requestLayout();
    }


}
