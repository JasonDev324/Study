package io.tanjundang.study.knowledge.launchmode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import io.tanjundang.study.R;

public class SingleTopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_top);
    }

    public void SkipToOne(View v) {
        OneActivity.SkipToActivityOne(this, LaunchModeActivity.SINGLETOP);
    }

}
