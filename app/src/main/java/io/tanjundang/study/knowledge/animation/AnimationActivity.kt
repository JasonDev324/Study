package io.tanjundang.study.knowledge.animation

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Path
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView

import io.tanjundang.study.R
import io.tanjundang.study.base.BaseActivity

/**
 * 加载Animation的两种方式 java、xml
 * xml：利用AnimationUtils的loadAnimation方法，加载anim目录下的动画
 * java：创建Animation对象，指明属性
 */
class AnimationActivity : BaseActivity(), View.OnClickListener {

    private var toolbar: Toolbar? = null
    private var alphaBtn: Button? = null
    private var translateBtn: Button? = null
    private var scaleBtn: Button? = null
    private var rotateBtn: Button? = null
    private var AnimSetBtn: Button? = null
    private var AnimListBtn = Button(this)
    private var ivAnimation: ImageView? = null
    private var frameDrawable: AnimationDrawable? = null
    private var objectAnimatorBtn: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        setContentView(R.layout.activity_animation)
        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        alphaBtn = findViewById<View>(R.id.alphaBtn) as Button
        translateBtn = findViewById<View>(R.id.translateBtn) as Button
        scaleBtn = findViewById<View>(R.id.scaleBtn) as Button
        rotateBtn = findViewById<View>(R.id.rotateBtn) as Button
        AnimSetBtn = findViewById<View>(R.id.AnimSetBtn) as Button
        AnimListBtn = findViewById<View>(R.id.AnimListBtn) as Button
        ivAnimation = findViewById<View>(R.id.ivAnimation) as ImageView
        objectAnimatorBtn = findViewById<View>(R.id.objectAnimatorBtn) as Button
        AnimListBtn!!.setOnClickListener(this)
        toolbar!!.setOnClickListener(this)
        alphaBtn!!.setOnClickListener(this)
        translateBtn!!.setOnClickListener(this)
        scaleBtn!!.setOnClickListener(this)
        rotateBtn!!.setOnClickListener(this)
        AnimSetBtn!!.setOnClickListener(this)
        objectAnimatorBtn!!.setOnClickListener(this)
    }

    override fun initData() {
        frameDrawable = ivAnimation!!.background as AnimationDrawable

    }

    //    使用ActivityOptionsCompat进行View的动画过度
    fun skip(v: View) {
        val compact = ActivityOptionsCompat.makeSceneTransitionAnimation(this, v, "tanjundang")
        ActivityCompat.startActivity(this, Intent(this, SceneActivity::class.java), compact.toBundle())
    }

    override fun onClick(view: View) {
        if (view == toolbar) {
            val animation = BtnAnimation(toolbar!!)
            animation.duration = 1000
            toolbar!!.startAnimation(animation)
        } else if (view == alphaBtn) {
            //            Java方式加载：
            //            AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
            //            alphaAnimation.setDuration(300);

            //            XML方式加载：
            val alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_anim)
            ivAnimation!!.startAnimation(alphaAnimation)
        } else if (view == translateBtn) {
            val translateAnimation = AnimationUtils.loadAnimation(this, R.anim.translate_anim)
            ivAnimation!!.startAnimation(translateAnimation)
        } else if (view == scaleBtn) {
            val scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_anim)
            ivAnimation!!.startAnimation(scaleAnimation)
        } else if (view == rotateBtn) {
            val rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_anim)
            ivAnimation!!.startAnimation(rotateAnimation)
        } else if (view == AnimSetBtn) {
            val rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.set_anim)
            ivAnimation!!.startAnimation(rotateAnimation)
        } else if (view == AnimListBtn) {
            if (frameDrawable!!.isRunning) {
                frameDrawable!!.stop()
            } else {
                frameDrawable!!.start()
            }
        } else if (view == objectAnimatorBtn) {
            //            XML方式加载：
            //            Animator animator = AnimatorInflater.loadAnimator(this, R.animator.objectanimator_anim);
            //            animator.setTarget(ivAnimation);
            //            animator.start();
            val xAnim = ObjectAnimator.ofFloat(ivAnimation, "scaleX", 1f, 1.5f)
            val yAnim = ObjectAnimator.ofFloat(ivAnimation, "scaleY", 1f, 1.5f)
            val animatorSet = AnimatorSet()
            animatorSet.play(xAnim).with(yAnim)
            animatorSet.start()
        }
    }
}
