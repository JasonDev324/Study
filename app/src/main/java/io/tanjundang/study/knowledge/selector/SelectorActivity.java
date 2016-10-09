package io.tanjundang.study.knowledge.selector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import io.tanjundang.study.R;
import io.tanjundang.study.base.Initial;

public class SelectorActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, Initial {

    RadioButton rbOne;
    RadioButton rbTwo;
    RadioGroup radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);
        initView();
        initData();
    }


    @Override
    public void initView() {
        rbOne = (RadioButton) findViewById(R.id.rbOne);
        rbTwo = (RadioButton) findViewById(R.id.rbTwo);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        if (checkedId == rbOne.getId()) {
//            Functions.toast(rbOne.getText().toString());
//        } else if (checkedId == rbTwo.getId()) {
//            Functions.toast(rbTwo.getText().toString());
//        }
    }
}