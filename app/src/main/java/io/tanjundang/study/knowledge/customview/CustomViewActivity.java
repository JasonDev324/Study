package io.tanjundang.study.knowledge.customview;

import android.os.Bundle;
import android.view.View;

import io.tanjundang.study.R;
import io.tanjundang.study.base.BaseActivity;

public class CustomViewActivity extends BaseActivity {
    BatteryView batteryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_custom_view);
        batteryView= (BatteryView) findViewById(R.id.batteryView);
        batteryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                batteryView.updateColor();
            }
        });
    }

    @Override
    protected void initData() {

    }
}
