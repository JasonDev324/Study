package io.tanjundang.study.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LayerRasterizer;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import io.tanjundang.study.common.tools.Functions;

/**
 * Developer: TanJunDang
 * Email: TanJunDang324@gmail.com
 * Date: 2016/9/24
 */

public class TestCustomView extends View {

    private Paint paint;
    private int mWidth;
    private int mHeight;

    public TestCustomView(Context context) {
        super(context);
        paint = new Paint();
    }

    public TestCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    public TestCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //这里获取的View的实际大小，如果View的宽高设置为warp_content，则为屏幕宽高
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2, mHeight / 2); //将画布原点移到中间
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10.0f);
        canvas.drawPoint(0, 0, paint);
        paint.setStrokeWidth(1.0f);
        paint.setColor(Color.GRAY);
        canvas.drawLine(-mWidth / 2 * 0.8f, 0, mWidth / 2 * 0.8f, 0, paint);
        canvas.drawLine(0, -mHeight / 2 * 0.8f, 0, mHeight / 2 * 0.8f, paint);
        paint.setStrokeWidth(2.0f);
        paint.setStyle(Paint.Style.STROKE);

        paint.setColor(Color.parseColor("#1abc9c"));
        canvas.drawRect(new Rect(-mWidth / 8, -mHeight / 8, mWidth / 8, mHeight / 8), paint);
        canvas.save(); //save方法跟restore方法之间的所有状态都不保存，，例如在这两者间旋转了画布，restore方法之后，画布恢复到正常
        paint.setColor(Color.BLUE);
        canvas.rotate(90);
        canvas.drawRect(new RectF(-mWidth / 8, -mHeight / 8, mWidth / 8, mHeight / 8), paint);
        canvas.restore();

        paint.setColor(Color.RED);

        canvas.drawRect(new Rect(-mWidth / 4, -mHeight / 4, mWidth / 4, mHeight / 4), paint);


    }
}
