package io.tanjundang.study.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Developer: TanJunDang
 * Email: TanJunDang324@gmail.com
 * Date: 2016/10/10
 */

public abstract class BaseActivity extends AppCompatActivity {

    public String TAG = getClass().getName();
    public String AUTHOR = "TanJunDang";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    protected abstract void initView();

    protected abstract void initData();

}
