package io.tanjundang.study;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.prolificinteractive.parallaxpager.ParallaxContainer;
import com.prolificinteractive.parallaxpager.ParallaxContextWrapper;
import com.prolificinteractive.parallaxpager.ParallaxPagerAdapter;

public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        getSupportFragmentManager().beginTransaction().add(R.id.layout_guide, new ParallaxFragment()).commit();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new ParallaxContextWrapper(newBase));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
