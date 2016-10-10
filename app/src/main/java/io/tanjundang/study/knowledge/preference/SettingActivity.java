package io.tanjundang.study.knowledge.preference;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.FrameLayout;

import io.tanjundang.study.R;
import io.tanjundang.study.base.BaseActivity;

public class SettingActivity extends BaseActivity {

    private FrameLayout content;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_setting);
        content = (FrameLayout) findViewById(R.id.flContent);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.flContent, new SettingFragment());
        transaction.commit();
    }

    @Override
    public void initData() {

    }
}
