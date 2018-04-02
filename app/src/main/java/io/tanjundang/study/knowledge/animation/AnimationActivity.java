package io.tanjundang.study.knowledge.animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import io.tanjundang.study.R;
import io.tanjundang.study.base.BaseActivity;

/**
 * 加载Animation的两种方式 java、xml
 * xml：利用AnimationUtils的loadAnimation方法，加载anim目录下的动画
 * java：创建Animation对象，指明属性
 */
public class AnimationActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private Button alphaBtn;
    private Button translateBtn;
    private Button scaleBtn;
    private Button rotateBtn;
    private Button AnimSetBtn;
    private Button AnimListBtn;
    private ImageView ivAnimation;
    private AnimationDrawable frameDrawable;
    private Button objectAnimatorBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_animation);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        alphaBtn = (Button) findViewById(R.id.alphaBtn);
        translateBtn = (Button) findViewById(R.id.translateBtn);
        scaleBtn = (Button) findViewById(R.id.scaleBtn);
        rotateBtn = (Button) findViewById(R.id.rotateBtn);
        AnimSetBtn = (Button) findViewById(R.id.AnimSetBtn);
        AnimListBtn = (Button) findViewById(R.id.AnimListBtn);
        ivAnimation = (ImageView) findViewById(R.id.ivAnimation);
        objectAnimatorBtn = (Button) findViewById(R.id.objectAnimatorBtn);
        AnimListBtn.setOnClickListener(this);
        toolbar.setOnClickListener(this);
        alphaBtn.setOnClickListener(this);
        translateBtn.setOnClickListener(this);
        scaleBtn.setOnClickListener(this);
        rotateBtn.setOnClickListener(this);
        AnimSetBtn.setOnClickListener(this);
        objectAnimatorBtn.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        frameDrawable = (AnimationDrawable) ivAnimation.getBackground();

    }

    //    使用ActivityOptionsCompat进行View的动画过度
    public void skip(View v) {
        ActivityOptionsCompat compact = ActivityOptionsCompat.makeSceneTransitionAnimation(this, v, "tanjundang");
        ActivityCompat.startActivity(this, new Intent(this, SceneActivity.class), compact.toBundle());
    }

    @Override
    public void onClick(View view) {
        if (view.equals(toolbar)) {
            BtnAnimation animation = new BtnAnimation(toolbar);
            animation.setDuration(1000);
            toolbar.startAnimation(animation);
        } else if (view.equals(alphaBtn)) {
//            Java方式加载：
//            AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
//            alphaAnimation.setDuration(300);

//            XML方式加载：
            Animation alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_anim);
            ivAnimation.startAnimation(alphaAnimation);
        } else if (view.equals(translateBtn)) {
            Animation translateAnimation = AnimationUtils.loadAnimation(this, R.anim.translate_anim);
            ivAnimation.startAnimation(translateAnimation);
        } else if (view.equals(scaleBtn)) {
            Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_anim);
            ivAnimation.startAnimation(scaleAnimation);
        } else if (view.equals(rotateBtn)) {
            Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
            ivAnimation.startAnimation(rotateAnimation);
        } else if (view.equals(AnimSetBtn)) {
            Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.set_anim);
            ivAnimation.startAnimation(rotateAnimation);
        } else if (view.equals(AnimListBtn)) {
            if (frameDrawable.isRunning()) {
                frameDrawable.stop();
            } else {
                frameDrawable.start();
            }
        } else if (view.equals(objectAnimatorBtn)) {
//            XML方式加载：
//            Animator animator = AnimatorInflater.loadAnimator(this, R.animator.objectanimator_anim);
//            animator.setTarget(ivAnimation);
//            animator.start();

            ObjectAnimator xAnim = ObjectAnimator.ofFloat(ivAnimation, "scaleX", 1, 1.5f);
            ObjectAnimator yAnim = ObjectAnimator.ofFloat(ivAnimation, "scaleY", 1, 1.5f);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.play(xAnim).with(yAnim);
            animatorSet.start();
        }
    }
}
