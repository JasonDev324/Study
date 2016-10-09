package io.tanjundang.study.knowledge.preference;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.view.MenuItem;

import java.util.List;

import io.tanjundang.study.R;

/**
 * Developer: TanJunDang
 * Email: TanJunDang324@gmail.com
 * Date: 2016/9/7
 */

public class PreSettingActivity extends PreferenceActivity {

    AppCompatDelegate mDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDelegate().installViewFactory();
        getDelegate().onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("设置");
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getDelegate().onPostCreate(savedInstanceState);
    }

    public ActionBar getSupportActionBar() {
        return getDelegate().getSupportActionBar();
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        super.onBuildHeaders(target);
        loadHeadersFromResource(R.xml.preference_headers, target);
    }


    AppCompatDelegate getDelegate() {
        if (mDelegate == null) {
            mDelegate = AppCompatDelegate.create(this, null);
        }
        return mDelegate;
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        //解决在4.4崩溃报错的问题
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

//    public void setSupportActionBar(@Nullable Toolbar toolbar) {
//        getDelegate().setSupportActionBar(toolbar);
//    }

//    @Override
//    public MenuInflater getMenuInflater() {
//        return getDelegate().getMenuInflater();
//    }

//    @Override
//    public void setContentView(@LayoutRes int layoutResID) {
//        getDelegate().setContentView(layoutResID);
//    }

//    @Override
//    public void setContentView(View view) {
//        getDelegate().setContentView(view);
//    }
//
//    @Override
//    public void setContentView(View view, ViewGroup.LayoutParams params) {
//        getDelegate().setContentView(view, params);
//    }

//    @Override
//    public void addContentView(View view, ViewGroup.LayoutParams params) {
//        getDelegate().addContentView(view, params);
//    }
//
//    @Override
//    protected void onPostResume() {
//        super.onPostResume();
//        getDelegate().onPostResume();
//    }
//
//    @Override
//    protected void onTitleChanged(CharSequence title, int color) {
//        super.onTitleChanged(title, color);
//        getDelegate().setTitle(title);
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        getDelegate().onConfigurationChanged(newConfig);
//    }


//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        getDelegate().onStop();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        getDelegate().onDestroy();
//    }
//
//    public void invalidateOptionsMenu() {
//        getDelegate().invalidateOptionsMenu();
//    }


}
