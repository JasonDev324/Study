package io.tanjundang.study.knowledge.launchmode;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import io.tanjundang.study.R;
import io.tanjundang.study.base.BaseActivity;

public class FourActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_four);
        TextView tvTest = (TextView) findViewById(R.id.tvTest);
        tvTest.setText("当前Activity实例：" + this);
    }

    @Override
    protected void initData() {

    }

    public void SkipToOne(View v) {
        OneActivity.SkipToActivityOne(this, LaunchModeActivity.SINGLETASK);
    }
}
