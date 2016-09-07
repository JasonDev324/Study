package io.tanjundang.study;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import io.tanjundang.study.base.Initial;
import io.tanjundang.study.common.tools.Functions;
import io.tanjundang.study.test.selector.SelectorActivity;
import io.tanjundang.study.test.animation.AnimationActivity;
import io.tanjundang.study.test.drawerlayout.DrawerLayoutActivity;
import io.tanjundang.study.test.shape.ShapeActivity;

public class MainActivity extends AppCompatActivity implements Initial, NavigationView.OnNavigationItemSelectedListener {

    private ActionBarDrawerToggle toggle;
    private ArrayList<DateItemBean> data = new ArrayList<>();
    private MainContentAdapter mAdapter;
    private RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    @Override
    public void initView() {
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
        data.add(new DateItemBean(R.string.main_text_study_animation, DateItemBean.Type.ANIMATION));
        data.add(new DateItemBean(R.string.main_text_study_drawerlayout, DateItemBean.Type.DRAWERLAYOUT));
        data.add(new DateItemBean(R.string.main_text_study_shape, DateItemBean.Type.SHAPE));
        data.add(new DateItemBean(R.string.main_text_study_selector, DateItemBean.Type.SELECTOR));
        data.add(new DateItemBean(R.string.main_text_study_preference, DateItemBean.Type.PREFERENCE));
        mAdapter.notifyDataSetChanged();
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
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

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
                    DateItemBean item = (DateItemBean) v.getTag(DATA_TAG);
                    if (item.getType().equals(DateItemBean.Type.ANIMATION)) {
                        StartActivity(AnimationActivity.class);
                    } else if (item.getType().equals(DateItemBean.Type.DRAWERLAYOUT)) {
                        StartActivity(DrawerLayoutActivity.class);
                    } else if (item.getType().equals(DateItemBean.Type.SHAPE)) {
                        StartActivity(ShapeActivity.class);
                    } else if (item.getType().equals(DateItemBean.Type.SELECTOR)) {
                        StartActivity(SelectorActivity.class);
                    } else if (item.getType().equals(DateItemBean.Type.PREFERENCE)) {
                        StartActivity(SelectorActivity.class);
                    }
                }
            });
            return new ContentHolder(view);
        }

        @Override
        public void onBindViewHolder(ContentHolder holder, int position) {
            DateItemBean item = data.get(position);
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

    private void StartActivity(Class cls) {
        Intent intent = new Intent(MainActivity.this, cls);
        startActivity(intent);
    }

}
