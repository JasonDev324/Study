package io.tanjundang.study.knowledge.jetpack

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import io.tanjundang.study.R

/**
 * @Author: TanJunDang
 * @Date: 2019/6/6
 * @Description:
 * 若UI需要处理声明周期可以通过lifecycle处理
 * Lifecycles管理生命周期
 * 1.声明观察处理类实现LifecycleObserver接口
 * 2.使用OnLifecycleEvent注解
 * 3.Activity中调用lifecycle注册观察类
 *
 * 参考https://www.jianshu.com/p/639574344a0f
 */

class LifecycleActivity : AppCompatActivity() {

    var myObserver: MyLifeObserver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jetpack)
        myObserver = MyLifeObserver(lifecycle)
        lifecycle.addObserver(myObserver!!)
    }


}