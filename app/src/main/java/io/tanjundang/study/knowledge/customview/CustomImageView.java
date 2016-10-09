package io.tanjundang.study.knowledge.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import io.tanjundang.study.R;

/**
 * Developer: TanJunDang
 * Email: TanJunDang324@gmail.com
 * Date: 2016/9/30
 * 自定义ImageView，有带文本的效果。
 * 1.添加自定义属性：文本颜色、文本、图片
 * 2.重写onDraw方法、重写onMeasure方法。
 */

public class CustomImageView extends View {

    private Bitmap imageSrc;
    private String text;
    private int textColor;
    private Paint paint;
    private float textSize;
    private Rect mTextBound;
    private float mX;
    private float mY;
    private int mWidth;
    private int mHeight;

    public CustomImageView(Context context) {
        this(context, null);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomImageView);
        textColor = array.getColor(R.styleable.CustomImageView_textColor, Color.BLUE);
        text = array.getText(R.styleable.CustomImageView_text).toString();
        imageSrc = BitmapFactory.decodeResource(getResources(), array.getResourceId(R.styleable.CustomImageView_imageSrc, 0));
        textSize = array.getDimensionPixelSize(R.styleable.CustomImageView_textSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                12, getResources().getDisplayMetrics()));
        paint = new Paint();
        mTextBound = new Rect();
        paint.getTextBounds(text, 0, text.length(), mTextBound);
        array.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //这里获取的View的实际大小，如果View的宽高设置为warp_content，则为屏幕宽高
        //初始化宽高
        mWidth = w;
        mHeight = h;
        mX = w / 2;
        mY = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //将画布中心点移至屏幕中央
        canvas.translate(mX, mY);

        paint.setColor(textColor);
        paint.setTextSize(textSize);

        canvas.drawText(text, (float) (-imageSrc.getWidth() * 0.5), (float) (imageSrc.getHeight() * 0.7), paint);
        canvas.drawBitmap(imageSrc, -imageSrc.getWidth() / 2, -imageSrc.getHeight() / 2, paint);

        paint.setColor(Color.BLUE);
        canvas.drawRect(mTextBound, paint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setMeasuredDimension(getSize(widthMeasureSpec, true), getSize(heightMeasureSpec, false));
        return;
    }

    public int getSize(int measureSpec, boolean isWidth) {
        int measureResult = 0;
        int size = MeasureSpec.getSize(measureSpec);
        int mode = MeasureSpec.getMode(measureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            measureResult = size;
        } else if (mode == MeasureSpec.AT_MOST) {
            if (isWidth) {
                measureResult = Math.min(size, imageSrc.getWidth() + mTextBound.width());
            } else {
                measureResult = Math.min(size, imageSrc.getHeight() + mTextBound.height());
            }

        }
        return measureResult;
    }
}
