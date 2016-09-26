package io.tanjundang.study.test.service;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import io.tanjundang.study.MainActivity;
import io.tanjundang.study.R;
import io.tanjundang.study.common.tools.Functions;

/**
 * 两种启动Service的实例
 */
public class ServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
    }

    public void StartService(View v) {
        //只需要指定跳转那个Service并调用startService方法即可，不需要添加Action
        Intent intent = new Intent(ServiceActivity.this, CalcService.class);
        intent.putExtra("msg", 1000);
        startService(intent);
    }

    public void BindService(View v) {
        Intent intent = new Intent(ServiceActivity.this, BindActivity.class);
        startActivity(intent);
    }
}
