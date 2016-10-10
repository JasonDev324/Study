package io.tanjundang.study.knowledge.selector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import io.tanjundang.study.R;
import io.tanjundang.study.base.BaseActivity;

public class SelectorActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    RadioButton rbOne;
    RadioButton rbTwo;
    RadioGroup radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void initView() {
        setContentView(R.layout.activity_selector);
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
