package io.tanjundang.study.knowledge.scrollconflict;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.tanjundang.study.R;
import io.tanjundang.study.base.BaseActivity;
import io.tanjundang.study.common.view.CustomLayoutManager;
import io.tanjundang.study.common.view.ItemDivider;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class ScrollConflictActivity extends BaseActivity {

    RecyclerView recyclerview;
    ArrayList<TestListInfo> data = new ArrayList<>();
    TestAdapter mAdapter;
    //    ScrollView scrollView;
    LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_scroll_conflict);
        Button btnTest = (Button) findViewById(R.id.btnTest);

        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        mAdapter = new TestAdapter();
        manager = new CustomLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerview.addItemDecoration(new ItemDivider(getColor(R.color.transparent), ItemDivider.HORIZONTAL, 35));
        recyclerview.setLayoutManager(manager);
        recyclerview.setAdapter(mAdapter);
        recyclerview.addOnScrollListener(new StoreShowOnScrollListener());

    }

    public class StoreShowOnScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            int childCount = recyclerView.getChildCount();
            for (int j = 0; j < childCount; j++) {
                View v = recyclerView.getChildAt(j);
                float offset = (float) Math.abs(recyclerView.getWidth() / 2 - (v.getLeft() + v.getRight()) / 2) / recyclerView.getWidth();
                offset = Math.min(offset, 0.2f);
                v.setScaleX(1 - offset);
                v.setScaleY(1 - offset);
            }
        }
    }

    @Override
    protected void initData() {
        data.add(new TestListInfo(""));
        data.add(new TestListInfo(""));
        for (int i = 0; i < 10; i++) {
            data.add(new TestListInfo("DATA_" + i));
        }
        data.add(new TestListInfo(""));
        data.add(new TestListInfo(""));
        mAdapter.notifyDataSetChanged();
        //延迟加载解决偏移出错问题
        Observable.interval(1, TimeUnit.SECONDS).take(1).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                int scrollToPos = 2;
                manager.scrollToPosition(scrollToPos);
            }
        });
    }

    class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestHolder> {
        int DATA_TAG = R.layout.list_item_scroll_conflict;

        @Override
        public TestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_scroll_conflict, parent, false);
            return new TestHolder(view);
        }

        @Override
        public void onBindViewHolder(TestHolder holder, int position) {
            TestListInfo info = data.get(position);
            holder.rootview.setVisibility(info.getTitle().isEmpty() ? View.GONE : View.VISIBLE);

            holder.tvTitle.setText(info.getTitle());
            holder.rootview.setTag(DATA_TAG, position);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class TestHolder extends RecyclerView.ViewHolder {

            TextView tvTitle;
            View rootview;
            ImageView ivImage;

            public TestHolder(View itemView) {
                super(itemView);
                rootview = itemView;
                ivImage = (ImageView) rootview.findViewById(R.id.ivImage);
                tvTitle = (TextView) rootview.findViewById(R.id.tvTitle);
                rootview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = (int) v.getTag(DATA_TAG);
                        manager.scrollToPosition(position);
                    }
                });
            }
        }


    }

}
