package io.tanjundang.study.knowledge.launchmode;

import android.os.Bundle;
import android.view.View;

import io.tanjundang.study.R;
import io.tanjundang.study.base.BaseActivity;

public class SingleTopActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_single_top);
    }

    @Override
    protected void initData() {

    }

    public void SkipToOne(View v) {
        OneActivity.SkipToActivityOne(this, LaunchModeActivity.SINGLETOP);
    }

}
