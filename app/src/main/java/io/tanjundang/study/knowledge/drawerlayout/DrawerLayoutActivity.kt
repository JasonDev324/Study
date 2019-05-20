package io.tanjundang.study.knowledge.drawerlayout

import android.content.res.Configuration
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View

import io.tanjundang.study.R
import io.tanjundang.study.base.BaseActivity

class DrawerLayoutActivity : BaseActivity() {

    internal var drawerLayout: DrawerLayout? = null
    internal var toogle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        setContentView(R.layout.activity_toggle)
    }

    override fun initData() {
        supportActionBar!!.title = "TanJunDang"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)// 给左上角图标的左边加上一个返回的图标
        drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        toogle = object : ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            override fun onDrawerOpened(drawerView: View?) {
                invalidateOptionsMenu()
            }

            override fun onDrawerClosed(drawerView: View?) {
                invalidateOptionsMenu()// 重新绘制actionBar上边的菜单项
            }
        }
        drawerLayout!!.setDrawerListener(toogle)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        //控制屏幕转换时，同步状态
        toogle!!.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toogle!!.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (toogle!!.onOptionsItemSelected(item)) {
            true  //控制打开抽屉
        } else super.onOptionsItemSelected(item)
    }
}
