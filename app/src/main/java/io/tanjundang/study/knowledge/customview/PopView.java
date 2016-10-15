package io.tanjundang.study.knowledge.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import io.tanjundang.study.R;
import io.tanjundang.study.common.tools.Functions;
import io.tanjundang.study.common.tools.LogTool;

/**
 * Author: TanJunDang
 * Email: TanJunDang324@gmail.com
 * Date: 2016/10/7
 * 对话泡泡窗View
 * 功能：可设置颜色
 * 学习：path的使用 - 画三角形、梯形等形状
 * 实现：当宽高为warp时，View的大小为屏幕的百分比、当宽高确定时，View的大小为该大小的百分比
 *
 */

public class PopView extends View {

    private int color;
    private Paint paint;
    private int mWidth;
    private int mHeight;
    private int widthSpec;
    private int heightSpec;
    private Path path;

    public PopView(Context context) {
        this(context, null);
    }

    public PopView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //        将屏幕宽的80%作为矩形的宽，屏幕高的40%作为矩形的高
        mWidth = (int) (0.8 * Functions.getScreenWidth(context));
        mHeight = (int) (0.4 * Functions.getScreenHeight());
//        DisplayMetrics metric = new DisplayMetrics();
//        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        manager.getDefaultDisplay().getMetrics(metric);
//        mWidth = (int) (metric.widthPixels * 0.8);     // 屏幕宽度（像素）
//        mHeight = (int) (metric.heightPixels * 0.4);
        paint = new Paint();
        path = new Path();
        paint.setAntiAlias(true);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PopView);
        color = array.getColor(R.styleable.PopView_popColor, ContextCompat.getColor(context, R.color.action_bar_color));
        paint.setColor(color);
        array.recycle();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(color);
//        画矩形
        canvas.drawRect(0, 0, mWidth, mHeight, paint);
//        画三角形
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        path.moveTo((float) (mWidth / 2 - mWidth * 0.1), mHeight);
        path.lineTo((float) (mWidth / 2 + mWidth * 0.1), mHeight);
        path.lineTo((float) (mWidth / 2), (float) (mHeight + mHeight * 0.2));
        path.close();
        canvas.drawPath(path, paint);
        LogTool.e("popview", "onDraw");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widthSpec = getSize(widthMeasureSpec, true);
        heightSpec = getSize(heightMeasureSpec, false);
        setMeasuredDimension(widthSpec, heightSpec);
        LogTool.e("popview", "onMeasure");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LogTool.e("popview", "onSizeChanged");
        LogTool.e("popview", "width:" + w + "height:" + h + "\noldWidth:" + oldw + "oldHeight:" + oldh);
    }

    public int getSize(int measureSpec, boolean isWidth) {
        int result = 0;
//        此处必须是View的完整高度，否则测量不完整
//        mHeight = (int) (mHeight + mHeight * 0.1);
        result = isWidth == true ? mWidth : (int) (mHeight + mHeight * 0.2);
        int size = MeasureSpec.getSize(measureSpec);
        int mode = MeasureSpec.getMode(measureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
            if (isWidth) {
                mWidth = (int) (result * 0.8);
            } else {
                mHeight = (int) (result * 0.4);
            }
        } else {
            result = Math.min(result, size);
        }

        return result;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        LogTool.e("popview", "onLayout");
    }
}
