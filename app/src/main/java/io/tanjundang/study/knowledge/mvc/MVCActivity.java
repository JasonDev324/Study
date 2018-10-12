package io.tanjundang.study.knowledge.mvc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.tanjundang.study.R;

/**
 * @Author: TanJunDang
 * @Date: 2018/10/12
 * @Description: 视图层负责数据的显示，以及用户的操作响应
 */

public class MVCActivity extends AppCompatActivity {
    TextView tvContent;
    Button btnLoadData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvc);
        tvContent = (TextView) findViewById(R.id.tvContent);
        btnLoadData = (Button) findViewById(R.id.btnLoadData);
        final MVCController controller = new MVCController(this);
        btnLoadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.loadData();
            }
        });
    }

    public void updateUI(String text) {
        tvContent.setText(text);
    }

}
