package io.tanjundang.study.knowledge.customview;

import android.animation.TypeEvaluator;

/**
 * Developer: TanJunDang
 * Email: TanJunDang324@gmail.com
 * Date: 2016/10/14
 */

public class PointEvaluator implements TypeEvaluator<Point> {

    @Override
    public Point evaluate(float fraction, Point startValue, Point endValue) {
        float x = startValue.getX() + fraction * (endValue.getX() - startValue.getX());
        float y = startValue.getY() + fraction * (endValue.getY() - startValue.getY());
        return new Point(x, y);
    }
}
