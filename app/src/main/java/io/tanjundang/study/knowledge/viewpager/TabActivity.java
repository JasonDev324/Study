package io.tanjundang.study.knowledge.viewpager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import io.tanjundang.study.MainActivity;
import io.tanjundang.study.R;

public class TabActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
    }

    public void SimpleTab(View v) {
        Intent intent = new Intent(this, SimpleTabActivity.class);
        startActivity(intent);
    }

    public void CustomTab(View v) {
        Intent intent = new Intent(this, CustomTabActivity.class);
        startActivity(intent);
    }
}
