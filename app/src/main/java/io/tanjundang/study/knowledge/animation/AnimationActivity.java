package io.tanjundang.study.knowledge.animation;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import io.tanjundang.study.R;
import io.tanjundang.study.base.BaseActivity;

public class AnimationActivity extends BaseActivity implements View.OnClickListener {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
    }

    @Override
    protected void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        if (view.equals(toolbar)) {
            BtnAnimation animation = new BtnAnimation(toolbar);
            animation.setDuration(200);
            toolbar.startAnimation(animation);
        }
    }
}
