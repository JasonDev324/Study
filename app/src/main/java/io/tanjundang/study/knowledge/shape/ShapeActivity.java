package io.tanjundang.study.knowledge.shape;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import io.tanjundang.study.R;
import io.tanjundang.study.base.BaseActivity;

public class ShapeActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivAnimation;
    private ImageView ivClip;
    private AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_shape);
        ivAnimation = (ImageView) findViewById(R.id.ivAnimation);
        ivAnimation.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        animationDrawable = (AnimationDrawable) ivAnimation.getBackground();
        animationDrawable.start();
    }

    @Override
    public void onClick(View v) {
        if (animationDrawable.isRunning()) {
            animationDrawable.stop();
        } else {
            animationDrawable.start();
        }
    }
}
