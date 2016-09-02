package io.tanjundang.study.common.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ListView;
import android.widget.TextView;

import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;

import io.tanjundang.study.R;
import io.tanjundang.study.common.tools.Functions;

/**
 * Developer: TanJunDang
 * Date: 2016/7/11
 * Email: TanJunDang324@gmail.com
 * 自定义ListView加载更多、下拉刷新控件
 */
public class RefreshLayout extends SwipeRefreshLayout {

    public static final String DATA_NULL = "DATA_NULL";
    public static final String DATA_MORE = "DATA_MORE";
    public static final String DATA_ERROR = "DATA_ERROR";

    private final int mTouchSlop; //最小滑动距离
    private ListView mListView;
    private OnLoadDataListener mOnLoadDataListener;
    private OnPullRefreshListener mOnPullRefreshListener;
    private View footerLayout;
    private TextView tvLoad;
    private GoogleProgressBar progressBar;
    private float firstTouchY;
    private float lastTouchY;

    private boolean isLoading = false;

    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        footerLayout = LayoutInflater.from(context).inflate(R.layout.common_footer_view, null);
        tvLoad = (TextView) footerLayout.findViewById(R.id.tvLoad);
        progressBar = (GoogleProgressBar) footerLayout.findViewById(R.id.progressBar);
    }

    /**
     * 将上拉、下拉监听器封装在一起
     *
     * @param mListView
     * @param onLoadDataListener 注册加载更多监听器 (不需要提供setOnLoadDataListener)
     * @param onRefreshListener  注册下拉刷新监听器
     */
    public void init(final ListView mListView, OnLoadDataListener onLoadDataListener, OnPullRefreshListener onRefreshListener) {
        this.mListView = mListView;
        mOnLoadDataListener = onLoadDataListener;
        mOnPullRefreshListener = onRefreshListener;
        this.mListView.setFooterDividersEnabled(false);//关闭footer下方的divider
        if (mOnPullRefreshListener != null) {
            setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refresh();
                }
            });
        }
//        this.mListView.addFooterView(footerLayout);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                firstTouchY = event.getRawY(); //记录第一次点击的坐标
                break;

            case MotionEvent.ACTION_UP:
                lastTouchY = event.getRawY(); //记录第二次点击的坐标
                /**
                 * 当滑动到底部,再向上滑的时候再加载数据
                 */
                if (canLoadMore()) {
                    loadData();
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    private boolean canLoadMore() {
        return isBottom() && !isLoading && isPullingUp();
    }

    private boolean isBottom() {
        if (mListView.getCount() > 0) {
            if (mListView.getLastVisiblePosition() == mListView.getAdapter().getCount() - 1 &&
                    mListView.getChildAt(mListView.getChildCount() - 1).getBottom() <= mListView.getHeight()) {

                return true;
            }
        }

        return false;
    }

    private boolean isPullingUp() {
        return (firstTouchY - lastTouchY) >= mTouchSlop;
    }

    private void loadData() {
        if (mOnLoadDataListener != null) {
            setLoading(true);
        }
    }

    /**
     * 请求完数据后关闭loading
     *
     * @param loading
     */
    public void setLoading(boolean loading) {
        if (mListView == null) return;
        isLoading = loading;
        if (loading) {
            if (isRefreshing()) {
                setRefreshing(false);
            }
            mListView.setSelection(mListView.getAdapter().getCount() - 1); //设置最上面显示的item
            mOnLoadDataListener.onLoad();
        } else {
            firstTouchY = 0;
            lastTouchY = 0;
        }
        progressBar.setVisibility(isLoading == true ? VISIBLE : GONE);
        tvLoad.setVisibility(isLoading != true ? VISIBLE : GONE);
    }

    public interface OnLoadDataListener {
        void onLoad();
    }

    public interface OnPullRefreshListener {
        void onRefresh();
    }

    public void refresh() {
        mOnPullRefreshListener.onRefresh();
    }

    public void setRefreshing(boolean isRefresh) {
        super.setRefreshing(isRefresh);
    }


    /**
     * 更新footerview
     * 为了解决list为null, 还会显示footview的问题,所以应该remove重新添加
     *
     * @param status
     */
    public void setFooterViewStatus(String status) {
        if (status.equals(DATA_NULL)) {
            if (mListView.getFooterViewsCount() > 0) { //直接remove是为了防止一开始就下发null,导致footerview的显示。
                mListView.removeFooterView(footerLayout);
            }
            Functions.toast("暂无数据");
        } else if (status.equals(DATA_MORE)) {
            if (mListView.getFooterViewsCount() == 0) {
                mListView.addFooterView(footerLayout);
            }
            mListView.setFooterDividersEnabled(true);//关闭footer下方的divider
            tvLoad.setText("加载更多");
        } else if (status.equals(DATA_ERROR)) {
            if (mListView.getFooterViewsCount() > 0) {
                mListView.removeFooterView(footerLayout);
            }
        }
    }
}
