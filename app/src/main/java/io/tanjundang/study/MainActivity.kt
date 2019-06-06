package io.tanjundang.study

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Button

import com.umeng.socialize.UMShareAPI

import java.util.ArrayList

import io.tanjundang.study.base.BaseActivity
import io.tanjundang.study.common.tools.Functions
import io.tanjundang.study.common.tools.PermissionTool
import io.tanjundang.study.common.tools.ShareTool
import io.tanjundang.study.common.view.photopick.ImageInfo
import io.tanjundang.study.knowledge.ViewStubActivity
import io.tanjundang.study.knowledge.actionbar.ActionBarStudyActivity
import io.tanjundang.study.knowledge.broadcast.NotifyReceiver
import io.tanjundang.study.knowledge.camera.CameraActivity
import io.tanjundang.study.knowledge.customview.CustomViewActivity
import io.tanjundang.study.knowledge.databinding.DatabindingActivity
import io.tanjundang.study.knowledge.datepicker.DatePickerActivity
import io.tanjundang.study.knowledge.greendao.DaoActivity
import io.tanjundang.study.knowledge.intent.IntentActivity
import io.tanjundang.study.knowledge.launchmode.LaunchModeActivity
import io.tanjundang.study.knowledge.locate.LocationActivity
import io.tanjundang.study.knowledge.lrucache.LruCacheActivity
import io.tanjundang.study.knowledge.mvc.MVCActivity
import io.tanjundang.study.knowledge.preference.PreSettingActivity
import io.tanjundang.study.knowledge.scrollconflict.ScrollConflictActivity
import io.tanjundang.study.knowledge.selector.SelectorActivity
import io.tanjundang.study.knowledge.animation.AnimationActivity
import io.tanjundang.study.knowledge.drawerlayout.DrawerLayoutActivity
import io.tanjundang.study.knowledge.kotlin.KotlinActivity
import io.tanjundang.study.knowledge.jetpack.JetPackActivity
import io.tanjundang.study.knowledge.service.ServiceActivity
import io.tanjundang.study.knowledge.shape.ShapeActivity
import io.tanjundang.study.knowledge.socket.SocketStudyActivity
import io.tanjundang.study.knowledge.threadpool.ThreadPoolActivity
import io.tanjundang.study.knowledge.viewpager.TabActivity
import io.tanjundang.study.knowledge.webview.WebViewActivity
import io.tanjundang.study.test.TestActivity

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, NotifyReceiver.NotifyCallback {

    private val toggle: ActionBarDrawerToggle? = null
    private val data = ArrayList<DataItemBean>()
    private val permissionList = ArrayList<String>()
    private var mAdapter: MainContentAdapter? = null
    private var recyclerview: RecyclerView? = null
    private var receiver: NotifyReceiver? = null
    private val mPickPhotos = ArrayList<ImageInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    public override fun initView() {
        setContentView(R.layout.activity_main)
        receiver = NotifyReceiver(this)
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, IntentFilter(NotifyReceiver.NOTIFY_ACTION))

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view -> Functions.snack(view, "666") }

        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()
        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        recyclerview = findViewById<View>(R.id.recyclerview) as RecyclerView
        mAdapter = MainContentAdapter()
        val manager = GridLayoutManager(this, 3)
        recyclerview!!.layoutManager = manager
        recyclerview!!.isFocusable = false
        recyclerview!!.isFocusableInTouchMode = false
        recyclerview!!.adapter = mAdapter
    }

    public override fun initData() {
        permissionList.add(Manifest.permission.CAMERA)
        permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)

        data.add(DataItemBean(R.string.main_text_study_animation, DataItemBean.Type.ANIMATION))
        data.add(DataItemBean(R.string.main_text_study_camera, DataItemBean.Type.CAMERA))
        data.add(DataItemBean(R.string.main_text_study_drawerlayout, DataItemBean.Type.DRAWERLAYOUT))
        data.add(DataItemBean(R.string.main_text_study_datepicker, DataItemBean.Type.DATEPICKER))
        data.add(DataItemBean(R.string.main_text_study_shape, DataItemBean.Type.SHAPE))
        data.add(DataItemBean(R.string.main_text_study_selector, DataItemBean.Type.SELECTOR))
        data.add(DataItemBean(R.string.main_text_study_preference, DataItemBean.Type.PREFERENCE))
        data.add(DataItemBean(R.string.main_text_study_actionbar, DataItemBean.Type.ACTIONBAR))
        data.add(DataItemBean(R.string.main_text_study_intent, DataItemBean.Type.INTENT))
        data.add(DataItemBean(R.string.main_text_study_launchmode, DataItemBean.Type.LAUNCHMODE))
        data.add(DataItemBean(R.string.main_text_study_broadcast, DataItemBean.Type.BROADCAST))
        data.add(DataItemBean(R.string.main_text_study_service, DataItemBean.Type.SERVICE))
        data.add(DataItemBean(R.string.main_text_study_guide, DataItemBean.Type.GUIDE))
        data.add(DataItemBean(R.string.main_text_study_customview, DataItemBean.Type.CUSTOMVIEW))
        data.add(DataItemBean(R.string.main_text_study_tablayout, DataItemBean.Type.TABLAYOUT))
        data.add(DataItemBean(R.string.main_text_study_umshare, DataItemBean.Type.UM_SHARE))
        data.add(DataItemBean(R.string.main_text_study_webview, DataItemBean.Type.WEBVIEW))
        data.add(DataItemBean(R.string.main_text_study_conflict, DataItemBean.Type.SCROLLCONFLICT))
        data.add(DataItemBean(R.string.main_text_study_socket, DataItemBean.Type.SOCKET))
        data.add(DataItemBean(R.string.main_text_study_databinding, DataItemBean.Type.DATABINDING))
        data.add(DataItemBean(R.string.main_text_study_gaode_map, DataItemBean.Type.GAODE_MAP))
        data.add(DataItemBean(R.string.main_text_study_viewstub, DataItemBean.Type.VIEWSTUB))
        data.add(DataItemBean(R.string.main_text_study_threadpool, DataItemBean.Type.THREAD_POOL))
        data.add(DataItemBean(R.string.main_text_study_lrucache, DataItemBean.Type.LRUCACHE))
        data.add(DataItemBean(R.string.main_text_study_mvc, DataItemBean.Type.MVC))
        data.add(DataItemBean(R.string.main_text_study_greendao, DataItemBean.Type.GREENDAO))
        data.add(DataItemBean(R.string.main_text_study_room, DataItemBean.Type.ROOM))
        data.add(DataItemBean(R.string.main_text_study_kotlin, DataItemBean.Type.KOTLIN))
        data.add(DataItemBean(R.string.main_text_study_jetpack, DataItemBean.Type.JETPACK))
        mAdapter!!.notifyDataSetChanged()

        mPickPhotos.add(ImageInfo("http://a.hiphotos.baidu.com/baike/w%3D268%3Bg%3D0/sign=36db4d32cb1349547e1eef626e75f565/63d9f2d3572c11dfc1e84a90632762d0f703c24c.jpg"))
        mPickPhotos.add(ImageInfo("http://b.hiphotos.baidu.com/baike/whfpf%3D72%2C72%2C0/sign=6acc9500fa1986184112bcc42cd01941/b64543a98226cffcc7c0afd3bb014a90f703eaed.jpg"))

    }

    override fun onBackPressed() {
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        //        toggle.syncState(); //
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_settings) {
            StartActivity(TestActivity::class.java)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {
        } else if (id == R.id.nav_slideshow) {
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }


    internal inner class MainContentAdapter : RecyclerView.Adapter<MainContentAdapter.ContentHolder>() {

        var DATA_TAG = R.layout.common_list_item_textview

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentHolder {
            //由于item里面是一个Button，会拦截RecyclerView的点击事件，所以需要设置Button的clickable属性
            val view = LayoutInflater.from(parent.context).inflate(R.layout.common_list_item_textview, parent, false)
            view.setOnClickListener { v ->
                val item = v.getTag(DATA_TAG) as DataItemBean
                if (item.getType() == DataItemBean.Type.ANIMATION) {
                    StartActivity(AnimationActivity::class.java)
                } else if (item.getType() == DataItemBean.Type.CAMERA) {
                    StartActivity(CameraActivity::class.java)
                } else if (item.getType() == DataItemBean.Type.DRAWERLAYOUT) {
                    StartActivity(DrawerLayoutActivity::class.java)
                } else if (item.getType() == DataItemBean.Type.DATEPICKER) {
                    StartActivity(DatePickerActivity::class.java)
                } else if (item.getType() == DataItemBean.Type.SHAPE) {
                    StartActivity(ShapeActivity::class.java)
                } else if (item.getType() == DataItemBean.Type.SELECTOR) {
                    StartActivity(SelectorActivity::class.java)
                } else if (item.getType() == DataItemBean.Type.PREFERENCE) {
                    StartActivity(PreSettingActivity::class.java)
                } else if (item.getType() == DataItemBean.Type.ACTIONBAR) {
                    StartActivity(ActionBarStudyActivity::class.java)
                } else if (item.getType() == DataItemBean.Type.INTENT) {
                    StartActivity(IntentActivity::class.java)
                } else if (item.getType() == DataItemBean.Type.LAUNCHMODE) {
                    StartActivity(LaunchModeActivity::class.java)
                } else if (item.getType() == DataItemBean.Type.BROADCAST) {
                    val intent = Intent(NotifyReceiver.NOTIFY_ACTION)
                    LocalBroadcastManager.getInstance(this@MainActivity).sendBroadcast(intent)
                } else if (item.getType() == DataItemBean.Type.SERVICE) {
                    StartActivity(ServiceActivity::class.java)
                } else if (item.getType() == DataItemBean.Type.GUIDE) {
                    StartActivity(GuideActivity::class.java)
                } else if (item.getType() == DataItemBean.Type.CUSTOMVIEW) {
                    StartActivity(CustomViewActivity::class.java)
                } else if (item.getType() == DataItemBean.Type.TABLAYOUT) {
                    StartActivity(TabActivity::class.java)
                } else if (item.getType() == DataItemBean.Type.UM_SHARE) {
                    ShareTool.getInstance().SendMessage(this@MainActivity)
                } else if (item.getType() == DataItemBean.Type.WEBVIEW) {
                    StartActivity(WebViewActivity::class.java)
                } else if (item.getType() == DataItemBean.Type.SCROLLCONFLICT) {
                    StartActivity(ScrollConflictActivity::class.java)
                } else if (item.getType() == DataItemBean.Type.SOCKET) {
                    StartActivity(SocketStudyActivity::class.java)
                } else if (item.getType() == DataItemBean.Type.DATABINDING) {
                    StartActivity(DatabindingActivity::class.java)
                } else if (item.getType() == DataItemBean.Type.GAODE_MAP) {
                    StartActivity(LocationActivity::class.java)
                } else if (item.getType() == DataItemBean.Type.VIEWSTUB) {
                    StartActivity(ViewStubActivity::class.java)
                } else if (item.getType() == DataItemBean.Type.THREAD_POOL) {
                    StartActivity(ThreadPoolActivity::class.java)
                } else if (item.getType() == DataItemBean.Type.LRUCACHE) {
                    StartActivity(LruCacheActivity::class.java)
                } else if (item.getType() == DataItemBean.Type.MVC) {
                    StartActivity(MVCActivity::class.java)
                } else if (item.getType() == DataItemBean.Type.GREENDAO) {
                    StartActivity(DaoActivity::class.java)
                } else if (item.getType() == DataItemBean.Type.KOTLIN) {
                    StartActivity(KotlinActivity::class.java)
                } else if (item.getType() == DataItemBean.Type.JETPACK) {
                    StartActivity(JetPackActivity::class.java)
                }
            }
            return ContentHolder(view)
        }

        override fun onBindViewHolder(holder: ContentHolder, position: Int) {
            val item = data[position]
            holder.btnValue.setText(item.title)
            holder.rootview.setTag(DATA_TAG, item)
        }

        override fun getItemCount(): Int {
            return data.size
        }

        internal inner class ContentHolder(var rootview: View) : RecyclerView.ViewHolder(rootview) {
            var btnValue: Button

            init {
                btnValue = rootview.findViewById<View>(R.id.btnValue) as Button
            }
        }

    }

    fun StartActivity(cls: Class<*>) {
        val intent = Intent(this@MainActivity, cls)
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        PermissionTool.getInstance(this).onRequestPermissionsResult(permissionList, requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
        if (requestCode == 60) {
            val mPickData = data.getSerializableExtra("data") as ArrayList<ImageInfo>
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver)
    }

    override fun refresh() {
        Functions.toast("成功收到了你发送的广播")
    }
}
