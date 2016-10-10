package io.tanjundang.study.knowledge.launchmode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import io.tanjundang.study.R;
import io.tanjundang.study.base.BaseActivity;

public class LaunchModeActivity extends BaseActivity {

    public static String STANDARD = "STANDARD";
    public static String SINGLETOP = "SINGLETOP";
    public static String SINGLETASK = "SINGLETASK";
    public static String SINGLEINSTANCE = "SINGLEINSTANCE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_launch_mode);
    }

    @Override
    protected void initData() {

    }

    public void Standard(View v) {
        Intent intent = new Intent(this, StandardActivity.class);
        startActivity(intent);
    }

    public void SingleTop(View v) {
        Intent intent = new Intent(this, SingleTopActivity.class);
        startActivity(intent);
    }

    public void SingleTask(View v) {
        Intent intent = new Intent(this, SingleTaskActivity.class);
        startActivity(intent);
    }

    public void SingleInstance(View v) {
        Intent intent = new Intent(this, SingleTaskActivity.class);
        startActivity(intent);
    }
}
