package io.tanjundang.study.test.launchmode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import io.tanjundang.study.R;

public class StandardActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard);
        textView = (TextView) findViewById(R.id.textView);
        textView.setText(this + "");
    }

    public void Standard(View v) {
        Intent intent = new Intent(this, StandardActivity.class);
        startActivity(intent);
    }
}
