package io.tanjundang.study.test.launchmode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import io.tanjundang.study.R;

public class LaunchModeActivity extends AppCompatActivity {

    public static String STANDARD = "STANDARD";
    public static String SINGLETOP = "SINGLETOP";
    public static String SINGLETASK = "SINGLETASK";
    public static String SINGLEINSTANCE = "SINGLEINSTANCE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_mode);
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
