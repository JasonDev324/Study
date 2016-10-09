package io.tanjundang.study.knowledge.viewpager;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import io.tanjundang.study.R;

public class SimpleTabActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] tabs = new String[]{"Tab1", "Tab2", "Tab3", "Tab4"};
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private PageOneFragment pageOneFragment;
    private PageTwoFragment pageTwoFragment;
    private PageThreeFragment pageThreeFragment;
    private CustomFragment customFragment;
    private SimpleTabAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_tab);
        initFragment();
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        mAdapter = new SimpleTabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(Color.GRAY, Color.BLUE);//设置选中与通常情况下tab的颜色
        tabLayout.setBackgroundResource(R.color.common_text_alpha_white);//设置TabLayout背景色
        tabLayout.setSelectedTabIndicatorColor(Color.RED);//设置下划线的颜色
        tabLayout.setSelectedTabIndicatorHeight(5);//设置下划线高度
    }

    public void initFragment() {
        fragments.clear();
        if (pageOneFragment == null) {
            pageOneFragment = PageOneFragment.newInstance();
        }
        if (pageTwoFragment == null) {
            pageTwoFragment = PageTwoFragment.newInstance();
        }
        if (pageThreeFragment == null) {
            pageThreeFragment = PageThreeFragment.newInstance();
        }

        if (customFragment == null) {
            customFragment = CustomFragment.newInstance();
        }

        fragments.add(pageOneFragment);
        fragments.add(pageTwoFragment);
        fragments.add(pageThreeFragment);
        fragments.add(customFragment);
    }

    class SimpleTabAdapter extends FragmentPagerAdapter {

        public SimpleTabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return tabs.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
    }

}
