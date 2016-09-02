package io.tanjundang.study.base;

import android.app.Application;

import io.tanjundang.study.common.tools.Functions;
import io.tanjundang.study.common.tools.ImageLoaderTool;

/**
 * Developer: TanJunDang
 * Email: TanJunDang324@gmail.com
 * Date: 2016/8/5
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderTool.initImageLoader(getApplicationContext());
        Functions.init(getApplicationContext());
    }
}
