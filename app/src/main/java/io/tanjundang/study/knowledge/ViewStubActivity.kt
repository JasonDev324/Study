package io.tanjundang.study.knowledge

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewStub
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

import io.tanjundang.study.R

/**
 * @Author: TanJunDang
 * @Date: 2018/3/22
 * @Description: ViewStub
 * 作用：延迟加载View
 * 特性：加载一次后就消失,适用于只加载一次后就不变的布局
 * 使用参考：http://blog.csdn.net/cx1229/article/details/53505903
 * 场景参考：http://www.androidchina.net/5550.html
 */

class ViewStubActivity : AppCompatActivity() {

    private var viewStub: ViewStub? = null
    private var llRootView: LinearLayout? = null
    private var btnModify: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_stub)
        viewStub = findViewById<View>(R.id.viewStub) as ViewStub
        btnModify = findViewById<View>(R.id.btnModify) as Button
    }

    fun inflate(v: View) {
        if (viewStub!!.parent != null) {
            //            第一次点击 初始化布局
            viewStub!!.inflate()
            llRootView = findViewById<View>(R.id.llRootView) as LinearLayout
            btnModify!!.text = "收起"
        } else {
            //            之后的点击就是show hide了
            if (llRootView!!.visibility == View.GONE) {
                llRootView!!.visibility = View.VISIBLE
                btnModify!!.text = "收起"
            } else {
                llRootView!!.visibility = View.GONE
                btnModify!!.text = "展开"
            }
        }
    }


}
