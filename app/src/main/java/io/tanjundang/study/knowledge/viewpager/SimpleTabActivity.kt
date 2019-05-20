package io.tanjundang.study.knowledge.viewpager

import android.graphics.Color
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import java.util.ArrayList

import io.tanjundang.study.R

class SimpleTabActivity : AppCompatActivity() {

    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null
    private val tabs = arrayOf("Tab1", "Tab2", "Tab3", "Tab4")
    private val fragments = ArrayList<Fragment>()
    private var pageOneFragment: PageOneFragment = PageOneFragment.newInstance()
    private var pageTwoFragment: PageTwoFragment = PageTwoFragment.newInstance()
    private var pageThreeFragment: PageThreeFragment = PageThreeFragment.newInstance()
    private var customFragment: CustomFragment = CustomFragment.newInstance()
    private var mAdapter: SimpleTabAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_tab)
        initFragment()
        tabLayout = findViewById<View>(R.id.tabLayout) as TabLayout?
        viewPager = findViewById<View>(R.id.viewPager) as ViewPager?
        mAdapter = SimpleTabAdapter(supportFragmentManager)
        viewPager!!.adapter = mAdapter
        tabLayout!!.setupWithViewPager(viewPager)
        tabLayout!!.setTabTextColors(Color.GRAY, Color.BLUE)//设置选中与通常情况下tab的颜色
        tabLayout!!.setBackgroundResource(R.color.common_text_alpha_white)//设置TabLayout背景色
        tabLayout!!.setSelectedTabIndicatorColor(Color.RED)//设置下划线的颜色
        tabLayout!!.setSelectedTabIndicatorHeight(5)//设置下划线高度
    }

    fun initFragment() {
        fragments.clear()
        fragments.add(pageOneFragment)
        fragments.add(pageTwoFragment)
        fragments.add(pageThreeFragment)
        fragments.add(customFragment)
    }

    internal inner class SimpleTabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return tabs.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return tabs[position]
        }
    }

}
