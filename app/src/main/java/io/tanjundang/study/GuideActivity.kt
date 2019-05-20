package io.tanjundang.study

import android.content.Context
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.prolificinteractive.parallaxpager.ParallaxContainer
import com.prolificinteractive.parallaxpager.ParallaxContextWrapper
import com.prolificinteractive.parallaxpager.ParallaxPagerAdapter

import io.tanjundang.study.base.BaseActivity

class GuideActivity : BaseActivity(), ViewPager.OnPageChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        setContentView(R.layout.activity_guide)
    }

    override fun initData() {
        supportFragmentManager.beginTransaction().add(R.id.layout_guide, ParallaxFragment()).commit()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ParallaxContextWrapper(newBase))
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {

    }

    override fun onPageScrollStateChanged(state: Int) {

    }
}
