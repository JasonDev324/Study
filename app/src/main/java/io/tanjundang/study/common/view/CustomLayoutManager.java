package io.tanjundang.study.common.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.tanjundang.study.common.tools.Functions;
import io.tanjundang.study.common.tools.LogTool;

/**
 * Developer: TanJunDang
 * Email: TanJunDang324@gmail.com
 * Date: 2016/11/7
 */

public class CustomLayoutManager extends LinearLayoutManager {

    private int baseWidth;

    public CustomLayoutManager(Context context) {
        super(context);
        baseWidth = Functions.getScreenWidth();
    }

    public CustomLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        baseWidth = Functions.getScreenWidth();
    }

    /**
     * 对偏移量进行处理
     *
     * @param position
     */
    @Override
    public void scrollToPosition(int position) {
        super.scrollToPosition(position);
        View view = findViewByPosition(position);
        if (view == null) return;
        int left = getDecoratedLeft(view);//获取目标View的左边界的x坐标
        int right = getDecoratedRight(view);
        int viewWidth = right - left;//获取目标view的宽度
        int offset = baseWidth / 2 - viewWidth / 2;
        //滑动到position位置的item，距离开始边界offset
        scrollToPositionWithOffset(position, offset);
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            int firstVisible = findFirstVisibleItemPosition();
            if (firstVisible == 0) {
                firstVisible = 1;
            }
            int lastVisible = findLastVisibleItemPosition();
            int center = (int) Math.floor((double) (lastVisible + firstVisible) / 2);
            LogTool.e("tag", "center" + center + "");
            scrollToPosition(center);
        }
    }

}
