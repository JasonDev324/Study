package io.tanjundang.study.knowledge.camera;

import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;

import io.tanjundang.study.R;
import io.tanjundang.study.base.BaseActivity;

public class CustomCameraActivity extends BaseActivity {

    private SurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_custom_camera);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
    }

    @Override
    protected void initData() {

    }

    public void takePhoto(View v) {

    }


}
