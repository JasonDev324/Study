package io.tanjundang.study.knowledge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

import io.tanjundang.study.R;

/**
 * @Author: TanJunDang
 * @Date: 2018/3/22
 * @Description: ViewStub
 * 作用：延迟加载View
 * 特性：加载一次后就消失,适用于只加载一次后就不变的布局
 * 参考：http://blog.csdn.net/cx1229/article/details/53505903
 */

public class ViewStubActivity extends AppCompatActivity {

    private ViewStub viewStub;
    private TextView tvContent;
    private Button btnMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stub);
        viewStub = (ViewStub) findViewById(R.id.viewStub);
        btnMsg = (Button) findViewById(R.id.btnMsg);
    }

    public void inflate(View v) {
        if (viewStub.getParent() != null) {
            viewStub.inflate();
            btnMsg.setVisibility(View.GONE);
        }
        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    public void doSomeThing(View v) {
        tvContent.setText("777");
    }
}
