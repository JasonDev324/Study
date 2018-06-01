package io.tanjundang.study;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;

import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;

import io.tanjundang.study.base.BaseActivity;
import io.tanjundang.study.common.tools.Functions;
import io.tanjundang.study.common.tools.PermissionTool;
import io.tanjundang.study.common.tools.ShareTool;
import io.tanjundang.study.common.view.photopick.ImageInfo;
import io.tanjundang.study.common.view.photopick.PhotoPickActivity;
import io.tanjundang.study.common.view.photopick.PhotoPickDetailActivity;
import io.tanjundang.study.knowledge.ViewStubActivity;
import io.tanjundang.study.knowledge.actionbar.ActionBarStudyActivity;
import io.tanjundang.study.knowledge.broadcast.NotifyReceiver;
import io.tanjundang.study.knowledge.camera.CameraActivity;
import io.tanjundang.study.knowledge.customview.CustomViewActivity;
import io.tanjundang.study.knowledge.databinding.DatabindingActivity;
import io.tanjundang.study.knowledge.datepicker.DatePickerActivity;
import io.tanjundang.study.knowledge.intent.IntentActivity;
import io.tanjundang.study.knowledge.launchmode.LaunchModeActivity;
import io.tanjundang.study.knowledge.locate.LocationActivity;
import io.tanjundang.study.knowledge.lrucache.LruCacheActivity;
import io.tanjundang.study.knowledge.preference.PreSettingActivity;
import io.tanjundang.study.knowledge.scrollconflict.ScrollConflictActivity;
import io.tanjundang.study.knowledge.selector.SelectorActivity;
import io.tanjundang.study.knowledge.animation.AnimationActivity;
import io.tanjundang.study.knowledge.drawerlayout.DrawerLayoutActivity;
import io.tanjundang.study.knowledge.service.ServiceActivity;
import io.tanjundang.study.knowledge.shape.ShapeActivity;
import io.tanjundang.study.knowledge.socket.SocketStudyActivity;
import io.tanjundang.study.knowledge.threadpool.ThreadPoolActivity;
import io.tanjundang.study.knowledge.viewpager.TabActivity;
import io.tanjundang.study.knowledge.webview.WebViewActivity;
import io.tanjundang.study.test.TestActivity;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, NotifyReceiver.NotifyCallback {

    private ActionBarDrawerToggle toggle;
    private ArrayList<DataItemBean> data = new ArrayList<>();
    private ArrayList<String> permissionList = new ArrayList<>();
    private MainContentAdapter mAdapter;
    private RecyclerView recyclerview;
    private NotifyReceiver receiver;
    private ArrayList<ImageInfo> mPickPhotos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
        receiver = new NotifyReceiver(this);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(NotifyReceiver.NOTIFY_ACTION));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Functions.snack(view, "666");
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        mAdapter = new MainContentAdapter();
        LinearLayoutManager manager = new GridLayoutManager(this, 3);
        recyclerview.setLayoutManager(manager);
        recyclerview.setFocusable(false);
        recyclerview.setFocusableInTouchMode(false);
        recyclerview.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        permissionList.add(Manifest.permission.CAMERA);
        permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        data.add(new DataItemBean(R.string.main_text_study_animation, DataItemBean.Type.ANIMATION));
        data.add(new DataItemBean(R.string.main_text_study_camera, DataItemBean.Type.CAMERA));
        data.add(new DataItemBean(R.string.main_text_study_drawerlayout, DataItemBean.Type.DRAWERLAYOUT));
        data.add(new DataItemBean(R.string.main_text_study_datepicker, DataItemBean.Type.DATEPICKER));
        data.add(new DataItemBean(R.string.main_text_study_shape, DataItemBean.Type.SHAPE));
        data.add(new DataItemBean(R.string.main_text_study_selector, DataItemBean.Type.SELECTOR));
        data.add(new DataItemBean(R.string.main_text_study_preference, DataItemBean.Type.PREFERENCE));
        data.add(new DataItemBean(R.string.main_text_study_actionbar, DataItemBean.Type.ACTIONBAR));
        data.add(new DataItemBean(R.string.main_text_study_intent, DataItemBean.Type.INTENT));
        data.add(new DataItemBean(R.string.main_text_study_launchmode, DataItemBean.Type.LAUNCHMODE));
        data.add(new DataItemBean(R.string.main_text_study_broadcast, DataItemBean.Type.BROADCAST));
        data.add(new DataItemBean(R.string.main_text_study_service, DataItemBean.Type.SERVICE));
        data.add(new DataItemBean(R.string.main_text_study_guide, DataItemBean.Type.GUIDE));
        data.add(new DataItemBean(R.string.main_text_study_customview, DataItemBean.Type.CUSTOMVIEW));
        data.add(new DataItemBean(R.string.main_text_study_tablayout, DataItemBean.Type.TABLAYOUT));
        data.add(new DataItemBean(R.string.main_text_study_umshare, DataItemBean.Type.UM_SHARE));
        data.add(new DataItemBean(R.string.main_text_study_webview, DataItemBean.Type.WEBVIEW));
        data.add(new DataItemBean(R.string.main_text_study_conflict, DataItemBean.Type.SCROLLCONFLICT));
        data.add(new DataItemBean(R.string.main_text_study_socket, DataItemBean.Type.SOCKET));
        data.add(new DataItemBean(R.string.main_text_study_databinding, DataItemBean.Type.DATABINDING));
        data.add(new DataItemBean(R.string.main_text_study_gaode_map, DataItemBean.Type.GAODE_MAP));
        data.add(new DataItemBean(R.string.main_text_study_viewstub, DataItemBean.Type.VIEWSTUB));
        data.add(new DataItemBean(R.string.main_text_study_threadpool, DataItemBean.Type.THREAD_POOL));
        data.add(new DataItemBean(R.string.main_text_study_lrucache, DataItemBean.Type.LRUCACHE));
        mAdapter.notifyDataSetChanged();

        mPickPhotos.add(new ImageInfo("http://a.hiphotos.baidu.com/baike/w%3D268%3Bg%3D0/sign=36db4d32cb1349547e1eef626e75f565/63d9f2d3572c11dfc1e84a90632762d0f703c24c.jpg"));
        mPickPhotos.add(new ImageInfo("http://b.hiphotos.baidu.com/baike/whfpf%3D72%2C72%2C0/sign=6acc9500fa1986184112bcc42cd01941/b64543a98226cffcc7c0afd3bb014a90f703eaed.jpg"));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        toggle.syncState(); //
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            StartActivity(TestActivity.class);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            PermissionTool.getInstance(this).requestPermissions(permissionList, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 666);
                }
            });
        } else if (id == R.id.nav_gallery) {
            PhotoPickDetailActivity.SkipToPhotoDetail(this, mPickPhotos, 0);
        } else if (id == R.id.nav_slideshow) {
            PhotoPickActivity.SkipToPhotoPickForResult(this, 6, 60);
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    class MainContentAdapter extends RecyclerView.Adapter<MainContentAdapter.ContentHolder> {

        int DATA_TAG = R.layout.common_list_item_textview;

        @Override
        public ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //由于item里面是一个Button，会拦截RecyclerView的点击事件，所以需要设置Button的clickable属性
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.common_list_item_textview, parent, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataItemBean item = (DataItemBean) v.getTag(DATA_TAG);
                    if (item.getType().equals(DataItemBean.Type.ANIMATION)) {
                        StartActivity(AnimationActivity.class);
                    } else if (item.getType().equals(DataItemBean.Type.CAMERA)) {
                        StartActivity(CameraActivity.class);
                    } else if (item.getType().equals(DataItemBean.Type.DRAWERLAYOUT)) {
                        StartActivity(DrawerLayoutActivity.class);
                    } else if (item.getType().equals(DataItemBean.Type.DATEPICKER)) {
                        StartActivity(DatePickerActivity.class);
                    } else if (item.getType().equals(DataItemBean.Type.SHAPE)) {
                        StartActivity(ShapeActivity.class);
                    } else if (item.getType().equals(DataItemBean.Type.SELECTOR)) {
                        StartActivity(SelectorActivity.class);
                    } else if (item.getType().equals(DataItemBean.Type.PREFERENCE)) {
                        StartActivity(PreSettingActivity.class);
                    } else if (item.getType().equals(DataItemBean.Type.ACTIONBAR)) {
                        StartActivity(ActionBarStudyActivity.class);
                    } else if (item.getType().equals(DataItemBean.Type.INTENT)) {
                        StartActivity(IntentActivity.class);
                    } else if (item.getType().equals(DataItemBean.Type.LAUNCHMODE)) {
                        StartActivity(LaunchModeActivity.class);
                    } else if (item.getType().equals(DataItemBean.Type.BROADCAST)) {
                        Intent intent = new Intent(NotifyReceiver.NOTIFY_ACTION);
                        LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intent);
                    } else if (item.getType().equals(DataItemBean.Type.SERVICE)) {
                        StartActivity(ServiceActivity.class);
                    } else if (item.getType().equals(DataItemBean.Type.GUIDE)) {
                        StartActivity(GuideActivity.class);
                    } else if (item.getType().equals(DataItemBean.Type.CUSTOMVIEW)) {
                        StartActivity(CustomViewActivity.class);
                    } else if (item.getType().equals(DataItemBean.Type.TABLAYOUT)) {
                        StartActivity(TabActivity.class);
                    } else if (item.getType().equals(DataItemBean.Type.UM_SHARE)) {
                        ShareTool.getInstance().SendMessage(MainActivity.this);
                    } else if (item.getType().equals(DataItemBean.Type.WEBVIEW)) {
                        StartActivity(WebViewActivity.class);
                    } else if (item.getType().equals(DataItemBean.Type.SCROLLCONFLICT)) {
                        StartActivity(ScrollConflictActivity.class);
                    } else if (item.getType().equals(DataItemBean.Type.SOCKET)) {
                        StartActivity(SocketStudyActivity.class);
                    } else if (item.getType().equals(DataItemBean.Type.DATABINDING)) {
                        StartActivity(DatabindingActivity.class);
                    } else if (item.getType().equals(DataItemBean.Type.GAODE_MAP)) {
                        StartActivity(LocationActivity.class);
                    } else if (item.getType().equals(DataItemBean.Type.VIEWSTUB)) {
                        StartActivity(ViewStubActivity.class);
                    } else if (item.getType().equals(DataItemBean.Type.THREAD_POOL)) {
                        StartActivity(ThreadPoolActivity.class);
                    } else if (item.getType().equals(DataItemBean.Type.LRUCACHE)) {
                        StartActivity(LruCacheActivity.class);
                    }
                }
            });
            return new ContentHolder(view);
        }

        @Override
        public void onBindViewHolder(ContentHolder holder, int position) {
            DataItemBean item = data.get(position);
            holder.btnValue.setText(item.getTitle());
            holder.rootview.setTag(DATA_TAG, item);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ContentHolder extends RecyclerView.ViewHolder {

            View rootview;
            Button btnValue;

            public ContentHolder(View itemView) {
                super(itemView);
                rootview = itemView;
                btnValue = (Button) rootview.findViewById(R.id.btnValue);
            }
        }

    }

    public void StartActivity(Class cls) {
        Intent intent = new Intent(MainActivity.this, cls);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionTool.getInstance(this).onRequestPermissionsResult(permissionList, requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (requestCode == 60) {
            ArrayList<ImageInfo> mPickData = (ArrayList<ImageInfo>) data.getSerializableExtra("data");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    public void refresh() {
        Functions.toast("成功收到了你发送的广播");
    }
}
