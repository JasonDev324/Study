package io.tanjundang.study.knowledge.scrollconflict;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

/**
 * Creator: KevinSu kevinsu917@126.com
 * Date 2016-03-03-17:19
 * Description:用于嵌套RecycleView时,拦截滑动事件的ScrollView
 */
public class NestScrollview extends NestedScrollView {
    private int downX;
    private int downY;
    private int mTouchSlop;
    private boolean scrollEnable = false;

    public NestScrollview(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public NestScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public NestScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) e.getRawX();
                downY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) e.getRawY();
                if (Math.abs(moveY - downY) > mTouchSlop) {
                    if (scrollEnable) {
                        return true;
                    } else {
                        return false;
                    }
                }
        }
        return super.onInterceptTouchEvent(e);
    }

    public void setScrollEnable(boolean scrollEnable) {
        this.scrollEnable = scrollEnable;

    }
}
