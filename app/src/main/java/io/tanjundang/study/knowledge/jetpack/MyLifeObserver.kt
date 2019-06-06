package io.tanjundang.study.knowledge.jetpack

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import io.tanjundang.study.common.tools.Functions
import io.tanjundang.study.common.tools.LogTool

/**
 * @Author: TanJunDang
 * @Date: 2019/6/6
 * @Description:
 */
class MyLifeObserver(var lifecycle: Lifecycle) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        LogTool.v("MyLifeObserver", "执行了onCreate")
        Functions.toast("执行了生命周期，请看log,tag为MyLifeObserver")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        LogTool.v("MyLifeObserver", "执行了onStart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        LogTool.v("MyLifeObserver", "执行了onResume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        LogTool.v("MyLifeObserver", "执行了onPause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestory() {
        LogTool.v("MyLifeObserver", "执行了onDestory")
    }

}