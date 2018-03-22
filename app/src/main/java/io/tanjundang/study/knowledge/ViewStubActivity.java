package io.tanjundang.study.knowledge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.tanjundang.study.R;

/**
 * @Author: TanJunDang
 * @Date: 2018/3/22
 * @Description: ViewStub
 * 作用：延迟加载View
 * 特性：加载一次后就消失,适用于只加载一次后就不变的布局
 * 使用参考：http://blog.csdn.net/cx1229/article/details/53505903
 * 场景参考：http://www.androidchina.net/5550.html
 */

public class ViewStubActivity extends AppCompatActivity {

    private ViewStub viewStub;
    private LinearLayout llRootView;
    private Button btnModify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stub);
        viewStub = (ViewStub) findViewById(R.id.viewStub);
        btnModify = (Button) findViewById(R.id.btnModify);
    }

    public void inflate(View v) {
        if (viewStub.getParent() != null) {
//            第一次点击 初始化布局
            viewStub.inflate();
            llRootView = (LinearLayout) findViewById(R.id.llRootView);
            btnModify.setText("收起");
        } else {
//            之后的点击就是show hide了
            if (llRootView.getVisibility() == View.GONE) {
                llRootView.setVisibility(View.VISIBLE);
                btnModify.setText("收起");
            } else {
                llRootView.setVisibility(View.GONE);
                btnModify.setText("展开");
            }
        }
    }


}
