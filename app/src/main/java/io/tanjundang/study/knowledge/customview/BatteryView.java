package io.tanjundang.study.knowledge.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import io.tanjundang.study.R;
import io.tanjundang.study.common.tools.LogTool;


/**
 * @Author: TanJunDang
 * @Date: 2018/5/6
 * @Description: 未充电状态、正在充电、充电完成、充电结束
 */
public class BatteryView extends View {
    private int defaultWidth;
    private int defaultHeight;
    private Paint paint;
    private Paint colorPaint;
    private final int EMPTY_COLOR = Color.parseColor("#f2f2f2");
    private final int FULL_COLOR = Color.parseColor("#00CF7C");

    //    染色的长度
    private int currentTintColorLength = 0;
    private int smallRectLeft = 0;
    private int smallRectBottom = 0;

    public BatteryView(Context context) {
        this(context, null);
    }

    public BatteryView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BatteryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_battery_empty);
        defaultWidth = bitmap.getWidth();
        defaultHeight = bitmap.getHeight();
        smallRectLeft = defaultWidth - defaultWidth / 10;
        smallRectBottom = defaultHeight / 2 + defaultHeight / 4;
        bitmap.recycle();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        colorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        colorPaint.setColor(FULL_COLOR);
        paint.setColor(EMPTY_COLOR);
        currentTintColorLength = 45;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(EMPTY_COLOR);
//        画出view的形状
        canvas.drawRoundRect(new RectF(0, 0, smallRectLeft, defaultHeight), 15, 15, paint);
        canvas.drawRoundRect(new RectF(smallRectLeft, defaultHeight / 4, defaultWidth, smallRectBottom), 5, 5, paint);
//        改变view的颜色
        canvas.drawRect(new RectF(0, 0, currentTintColorLength - defaultWidth / 10, defaultHeight), colorPaint);
//        当颜色增加到默认宽度时，画小矩形
        if (currentTintColorLength >= smallRectLeft + defaultWidth / 10) {
            canvas.drawRect(new RectF(smallRectLeft, defaultHeight / 4, currentTintColorLength, smallRectBottom), colorPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = resolveSize(defaultWidth, widthMeasureSpec);
        int h = resolveSize(defaultHeight, heightMeasureSpec);
        setMeasuredDimension(w, h);
    }

    public void updateColor() {
        startAnim();
    }

    private void startAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(0, defaultWidth);
        animator.setDuration(5000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentTintColorLength = (int) animation.getAnimatedValue();
                double percent = currentTintColorLength / (double) defaultWidth * 100;
                LogTool.d(getClass().getName(), "百分比:" + percent);
                if (percent < 40) {
                    colorPaint.setColor(Color.RED);
                } else {
                    colorPaint.setColor(FULL_COLOR);
                }
                invalidate();
            }
        });
        animator.start();
    }
}
