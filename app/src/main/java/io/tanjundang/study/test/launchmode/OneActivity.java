package io.tanjundang.study.test.launchmode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import io.tanjundang.study.R;

import static io.tanjundang.study.test.launchmode.LaunchModeActivity.SINGLETASK;
import static io.tanjundang.study.test.launchmode.LaunchModeActivity.STANDARD;
import static io.tanjundang.study.test.launchmode.TwoActivity.SkipToActivityTwo;

public class OneActivity extends AppCompatActivity {

    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_top_one);
        TextView tvTest = (TextView) findViewById(R.id.tvTest);
        tvTest.setText("当前Activity实例：" + this);
//        type
    }

    public void SkipToTwo(View v) {
        SkipToActivityTwo(this, SINGLETASK);
    }


}
