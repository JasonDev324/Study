package io.tanjundang.study.knowledge.viewpager

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.ScaleAnimation
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import io.tanjundang.study.R
import io.tanjundang.study.common.tools.Functions

/**
 * @Author: TanJunDang
 * @Date: 2019/5/20
 * @Description:
 */
class CustomTabActivity : AppCompatActivity() {

    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    var tabs = arrayOf("TAB1", "TAB2", "TAB3", "TAB4")
    var icons = arrayOf(R.drawable.ic_menu_camera, R.drawable.ic_menu_manage, R.drawable.ic_menu_send, R.drawable.ic_menu_slideshow)
    var fragments = ArrayList<Fragment>()
    var pageOneFragment: PageOneFragment = PageOneFragment.newInstance()
    var pageTwoFragment: PageTwoFragment = PageTwoFragment.newInstance()
    var pageThreeFragment: PageThreeFragment = PageThreeFragment.newInstance()
    var customFragment: CustomFragment = CustomFragment.newInstance()
    var logoAnimation = ScaleAnimation(1.0f, 1.3f, 1.0f, 1.3f, 1, 0.5f, 1, 0.5f)
    var floatBtn: FloatingActionButton? = null
    var mAdapter: CustomTabAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_tab)
        initFragment()
        tabLayout = findViewById<View>(R.id.tabLayout) as TabLayout
        floatBtn = findViewById(R.id.floatBtn) as FloatingActionButton?
        viewPager = findViewById(R.id.viewPager)
        mAdapter = CustomTabAdapter(supportFragmentManager)
        floatBtn!!.setOnClickListener(View.OnClickListener { Functions.toast("interesting") })
        viewPager!!.adapter = mAdapter
        tabLayout!!.setupWithViewPager(viewPager)
        for (i in 0 until tabLayout!!.tabCount) {
            var tab = tabLayout!!.getTabAt(i)
            tab!!.customView = mAdapter!!.getTabView(i)
        }

        updateSelected(0)
        logoAnimation!!.duration = 100L
        logoAnimation!!.fillAfter = true
        tabLayout!!.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.transparent))
        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val tvTitle = tab!!.getCustomView()!!.findViewById<View>(R.id.tvTitle) as TextView
                tvTitle.setTextColor(Color.GRAY)
                tab.getCustomView()!!.clearAnimation()
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tvTitle = tab!!.getCustomView()!!.findViewById<View>(R.id.tvTitle) as TextView
                tvTitle.setTextColor(Color.BLUE)
                viewPager!!.setCurrentItem(tab.getPosition())
                tab.getCustomView()!!.startAnimation(logoAnimation)
            }

        })

    }

    fun initFragment() {
        fragments.clear()
        fragments.add(pageOneFragment)
        fragments.add(pageTwoFragment)
        fragments.add(pageThreeFragment)
        fragments.add(customFragment)
    }

    fun updateSelected(position: Int) {
        for (i in 0 until tabLayout!!.tabCount) {
            var tab = tabLayout!!.getTabAt(i)
            var textView = tab!!.customView!!.findViewById<View>(R.id.tvTitle) as TextView
            textView!!.setTextColor(if (position == i) {
                Color.BLUE
            } else {
                Color.GRAY
            })
            if (position == i) {
                tab.customView!!.startAnimation(logoAnimation)
            }
        }
    }

    inner class CustomTabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        fun getTabView(pos: Int): View {
            var view = LayoutInflater.from(applicationContext).inflate(R.layout.custom_tab, null)
            var tab = view!!.findViewById<View>(R.id.tvTitle) as TextView
            tab!!.text = tabs[pos]
            var ivImage = view!!.findViewById<View>(R.id.ivImage) as ImageView
            ivImage!!.setImageResource(icons[pos])
            return view
        }

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