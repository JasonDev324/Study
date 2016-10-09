package io.tanjundang.study.knowledge.launchmode;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import io.tanjundang.study.R;

public class TwoActivity extends AppCompatActivity {

    private String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_top_two);
        TextView tvTest = (TextView) findViewById(R.id.tvTest);
        tvTest.setText("当前Activity实例：" + this);
        type = getIntent().getStringExtra("type");
    }

    public void SkipToThree(View v) {
        ThreeActivity.SkipToActivityThree(this, type);
    }

    public static void SkipToActivityTwo(Context context, String type) {
        Intent intent = new Intent(context, TwoActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }
}
