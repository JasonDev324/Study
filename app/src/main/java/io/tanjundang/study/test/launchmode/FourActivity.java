package io.tanjundang.study.test.launchmode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import io.tanjundang.study.R;

public class FourActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);
    }

    public void SkipToOne(View v) {
        Intent intent = new Intent(this, OneActivity.class);
        startActivity(intent);
    }
}
