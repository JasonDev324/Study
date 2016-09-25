package io.tanjundang.study.test;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import io.tanjundang.study.R;


/**
 * Developer: TanJunDang
 * Email: TanJunDang324@gmail.com
 * Date: 2016/9/24
 */

public class MoodView extends View {

    private Paint paint;
    private int mWidth;
    private int mHeight;
    private static int DEFAULT_COLOR = R.color.red;
    private int defaultColor;
    private float mX;
    private float mY;
    private int DEFAULT_SIZE;

    public MoodView(Context context) {
        this(context, null);
        paint = new Paint();
        paint.setColor(ContextCompat.getColor(context, defaultColor));
    }

    public MoodView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        paint = new Paint();
    }

    public MoodView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        DEFAULT_SIZE = context.getResources().getDimensionPixelSize(R.dimen.default_size);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MoodView);
        defaultColor = array.getColor(R.styleable.MoodView_moodColor, ContextCompat.getColor(context, DEFAULT_COLOR));
        paint = new Paint(); //在这里设置颜色 无效
        paint.setAntiAlias(false);
        array.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //这里获取的View的实际大小，如果View的宽高设置为warp_content，则为屏幕宽高
        //初始化宽高
        mWidth = w;
        mHeight = h;
        mX = mWidth / 2;
        mY = mHeight / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(defaultColor);
        canvas.translate(mX, mY); //将画布原点移到中间

//        paint.setColor(Color.BLACK);
//        paint.setStrokeWidth(10.0f);
//        canvas.drawPoint(0, 0, paint);
//        paint.setStrokeWidth(1.0f);
//        paint.setColor(Color.GRAY);
//        canvas.drawLine(-mWidth / 2 * 0.8f, 0, mWidth / 2 * 0.8f, 0, paint);
//        canvas.drawLine(0, -mHeight / 2 * 0.8f, 0, mHeight / 2 * 0.8f, paint);
//        paint.setStrokeWidth(2.0f);
//        paint.setStyle(Paint.Style.STROKE);
//
//        paint.setColor(Color.parseColor("#1abc9c"));
//        canvas.drawRect(new Rect(-mWidth / 8, -mHeight / 8, mWidth / 8, mHeight / 8), paint);
//        canvas.save(); //save方法跟restore方法之间的所有状态都不保存，，例如在这两者间旋转了画布，restore方法之后，画布恢复到正常
//        paint.setColor(Color.BLUE);
//        canvas.rotate(90);
//        canvas.drawRect(new RectF(-mWidth / 8, -mHeight / 8, mWidth / 8, mHeight / 8), paint);
//        canvas.restore();
//
//        paint.setColor(Color.RED);
//
//        canvas.drawRect(new Rect(0, 0,100, mHeight / 4), paint);
//        //x轴开始坐标、y轴开始坐标、x轴结束坐标、y轴结束坐标

//        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10.0f);
        canvas.drawPoint(40, -40, paint);
        canvas.drawPoint(-40, -40, paint);
        RectF rect = new RectF(-50, -50, 50, 50);
//        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(rect, 0, 180, false, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //获取点击的坐标、并且重绘，设置为画布原点的XY即可。
        mX = event.getX();
        mY = event.getY();
        invalidate();
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setMeasuredDimension(getSize(widthMeasureSpec), getSize(heightMeasureSpec));
    }

    public int getSize(int measureSpec) {
        int result = DEFAULT_SIZE;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else if (mode == MeasureSpec.AT_MOST) {
            result = Math.min(DEFAULT_SIZE, size);
        }
        return result;
    }
}
