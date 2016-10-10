package io.tanjundang.study.knowledge.launchmode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.tanjundang.study.R;
import io.tanjundang.study.base.BaseActivity;
import io.tanjundang.study.common.tools.Functions;

public class ThreeActivity extends BaseActivity {

    private String type = "";
    TextView tvTest;
    LinearLayout rootview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_single_top_three);
        tvTest = (TextView) findViewById(R.id.tvTest);
        rootview = (LinearLayout) findViewById(R.id.activity_single_top_three);
    }

    @Override
    protected void initData() {
        type = getIntent().getStringExtra("type");
        tvTest.setText("当前Activity实例：" + this);
        if (type.equals(LaunchModeActivity.SINGLETASK)) {
            for (int i = 0; i < rootview.getChildCount(); i++) {
                rootview.getChildAt(i).setVisibility(View.GONE);
            }
            rootview.getChildAt(1).setVisibility(View.VISIBLE);
            rootview.getChildAt(2).setVisibility(View.VISIBLE);
        }
    }

    public static void SkipToActivityThree(Context context, String type) {
        Intent intent = new Intent(context, ThreeActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }


    public void SkipToThree(View v) {
        Intent intent = new Intent(this, ThreeActivity.class);
        startActivity(intent);
    }

    public void SkipToFour(View v) {
        Intent intent = new Intent(this, FourActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Functions.toast("复用旧实例，不进行跳转，但是执行onNewIntent方法");
    }
}
