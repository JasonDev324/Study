package io.tanjundang.study.knowledge.customview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;

/**
 * Developer: TanJunDang
 * Email: TanJunDang324@gmail.com
 * Date: 2016/10/14
 */

public class PointView extends View {

    private Point currentPoint;
    private Paint paint;
    private int RADIUS = 50;
    private int mColor;

    public PointView(Context context) {
        this(context, null);
    }

    public PointView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (currentPoint == null) {
            currentPoint = new Point(RADIUS, RADIUS);
            drawCircle(canvas);
            startAnim();
        } else {
            drawCircle(canvas);
        }

    }

    public void drawCircle(Canvas canvas) {
        canvas.drawCircle(currentPoint.getX(), currentPoint.getY(), RADIUS, paint);
    }

    public void startAnim() {
        //两个点决定变化过程
        Point startPoint = new Point(RADIUS, RADIUS);
        Point endPoint = new Point(getWidth() - RADIUS, getHeight() - RADIUS);
        ValueAnimator animator = ObjectAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });


        /**
         * ofObject(对象，属性值，计算器，示例值)
         * 从蓝色到红色的变化
         */
        ValueAnimator colorAnim = ObjectAnimator.ofObject(this, "mycolor", new ColorEvaluator(), Color.BLUE, Color.RED);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(3000);
        set.play(animator).with(colorAnim);
        set.setInterpolator(new BounceInterpolator());
        set.start();
    }

    /**
     * 让objectAnimator寻找的方法
     * 第一个字母必须大小，其余都是小写，否则找不到
     *
     * @param color
     */
    public void setMycolor(int color) {
        mColor = color;
        paint.setColor(mColor);
        invalidate();
    }

    public int getMycolor() {
        return mColor;
    }

}
