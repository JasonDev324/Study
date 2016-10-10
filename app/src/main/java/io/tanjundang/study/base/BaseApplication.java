package io.tanjundang.study.base;

import android.app.Application;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

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
        UMShareAPI.get(this);
//        PlatformConfig.setSinaWeibo("3688550464", "570706d7dd026e57e4bffea3e7c94938");
        PlatformConfig.setQQZone("1105742570", "WbVyNv1fGXenRLoz");
    }
}
