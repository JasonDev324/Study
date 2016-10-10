package io.tanjundang.study.knowledge.launchmode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import io.tanjundang.study.R;
import io.tanjundang.study.base.BaseActivity;

public class StandardActivity extends BaseActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_standard);
        textView = (TextView) findViewById(R.id.textView);
    }

    @Override
    protected void initData() {
        textView.setText(this + "");
    }

    public void Standard(View v) {
        Intent intent = new Intent(this, StandardActivity.class);
        startActivity(intent);
    }
}
