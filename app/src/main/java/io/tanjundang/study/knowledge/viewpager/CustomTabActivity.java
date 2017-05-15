package io.tanjundang.study.knowledge.viewpager;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import io.tanjundang.study.R;
import io.tanjundang.study.common.tools.Functions;
import io.tanjundang.study.common.tools.ThreadManageTool;

/**
 * TabLayout+ViewPager学习
 * TabLayout的用处，设置tab标题，设置自定义View，关联ViewPager
 * ViewPager的用处，创建FragmentPagerAdaper，设置adapter。
 * 遇到的问题：
 * 1.tab为文本时，需要用到哪些方法？
 * 2.tab为自定义View时，需要用到哪些方法？
 * 3.如何初始化tab的默认选择项？
 * 4.为什么CustomTab点击Tab会失效？
 */
public class CustomTabActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] tabs = new String[]{"TAB1", "TAB2", "TAB3", "TAB4"};
    private int[] icons = new int[]{R.drawable.ic_menu_camera, R.drawable.ic_menu_send, R.drawable.ic_menu_manage, R.drawable.ic_menu_slideshow};
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private PageOneFragment pageOneFragment;
    private PageTwoFragment pageTwoFragment;
    private PageThreeFragment pageThreeFragment;
    private CustomFragment customFragment;
    private CustomTabAdapter mAdapter;
    final Animation logoAnimation = new ScaleAnimation(1.0F, 1.3F, 1.0F, 1.3F, 1, 0.5F, 1, 0.5F);//选中放大的动画
    private FloatingActionButton floatBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_tab);
        initFragment();
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        floatBtn = (FloatingActionButton) findViewById(R.id.floatBtn);
        floatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.toast("interesting");
            }
        });
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        mAdapter = new CustomTabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);

        tabLayout.setupWithViewPager(viewPager);//关联起ViewPager

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(mAdapter.getTabView(i));
        }
        updateSelected(0);
        logoAnimation.setDuration(100L);
        logoAnimation.setFillAfter(true);

        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.transparent));
        /**
         * 当tab是自定义View的时候，需要设置监听器Tab选中的监听器，从而修改选中文本的颜色
         * 当tab是普通文本的时候，可以通过setTabTextColors方法设置选中以及普通情况下tab文本的颜色，需要初始化选中项
         */


        /**
         * tabLayout选中监听器
         */
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView tvTitle = (TextView) tab.getCustomView().findViewById(R.id.tvTitle);
                tvTitle.setTextColor(Color.BLUE);
                viewPager.setCurrentItem(tab.getPosition());
                tab.getCustomView().startAnimation(logoAnimation);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView tvTitle = (TextView) tab.getCustomView().findViewById(R.id.tvTitle);
                tvTitle.setTextColor(Color.GRAY);
                tab.getCustomView().clearAnimation();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
    }

    /**
     * 初始化tab默认选中项
     *
     * @param position
     */
    private void updateSelected(int position) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                TextView textView = (TextView) tab.getCustomView().findViewById(R.id.tvTitle);
                textView.setTextColor(i == position ? Color.BLUE : Color.GRAY);
                if (i == position) {
                    tab.getCustomView().startAnimation(logoAnimation);
                }

            }
        }
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


    class CustomTabAdapter extends FragmentPagerAdapter {

        public CustomTabAdapter(FragmentManager fm) {
            super(fm);
        }

        //设置了自定义View不能调用tabLayout的setTabGravity方法，否则布局出错
        public View getTabView(int position) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_tab, null);
            TextView tab = (TextView) view.findViewById(R.id.tvTitle);
            tab.setText(tabs[position]);
            ImageView ivImage = (ImageView) view.findViewById(R.id.ivImage);
            ivImage.setImageResource(icons[position]);
            return view;
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
