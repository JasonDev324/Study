package io.tanjundang.study.knowledge.scrollconflict;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import io.tanjundang.study.R;
import io.tanjundang.study.base.BaseActivity;
import io.tanjundang.study.common.view.ItemDivider;

public class ScrollConflictActivity extends BaseActivity {

    RecyclerView recyclerview;
    ArrayList<TestListInfo> data = new ArrayList<>();
    TestAdapter mAdapter;
//    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_scroll_conflict);
//        scrollView = (ScrollView) findViewById(R.id.scrollView);

        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        mAdapter = new TestAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview.addItemDecoration(new ItemDivider(Color.parseColor("#1abc9c"), ItemDivider.HORIZONTAL, 10));
        recyclerview.setLayoutManager(manager);
        recyclerview.setAdapter(mAdapter);
//        recyclerview.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView view, int newState) {
//                super.onScrollStateChanged(view, newState);
//                if (isVisBottom(view)) {
//                    scrollView.setScrollEnable(true);
////                    view.getParent().requestDisallowInterceptTouchEvent(false);//true时，让view的父类不拦截事件
////                    scrollView.requestDisallowInterceptTouchEvent(false);
//                } else {
//                    scrollView.setScrollEnable(false);
//                    view.getParent().requestDisallowInterceptTouchEvent(true);
//                }
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//            }
//        });
    }


    public static boolean isVisBottom(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //屏幕中最后一个可见子项的position
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();
        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();
        //RecyclerView的滑动状态
        int state = recyclerView.getScrollState();
        if (visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == recyclerView.SCROLL_STATE_IDLE) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void initData() {
        for (int i = 0; i < 10; i++) {
            data.add(new TestListInfo("DATA_" + i));
        }
        mAdapter.notifyDataSetChanged();
    }

    class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestHolder> {
        @Override
        public TestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_scroll_conflict, parent, false);
            return new TestHolder(view);
        }

        @Override
        public void onBindViewHolder(TestHolder holder, int position) {
            holder.tvTitle.setText(data.get(position).getTitle());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class TestHolder extends RecyclerView.ViewHolder {

            TextView tvTitle;
            View rootview;

            public TestHolder(View itemView) {
                super(itemView);
                rootview = itemView;
                tvTitle = (TextView) rootview.findViewById(R.id.tvTitle);
            }
        }
    }

}
