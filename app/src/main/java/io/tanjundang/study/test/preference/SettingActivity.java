package io.tanjundang.study.test.preference;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import io.tanjundang.study.R;
import io.tanjundang.study.base.Initial;

public class SettingActivity extends AppCompatActivity implements Initial {

    private FrameLayout content;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        initData();
    }

    @Override
    public void initView() {
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
