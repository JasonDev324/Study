package io.tanjundang.study.knowledge.selector

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup

import io.tanjundang.study.R
import io.tanjundang.study.base.BaseActivity

class SelectorActivity : BaseActivity(), RadioGroup.OnCheckedChangeListener {

    internal var rbOne: RadioButton? = null
    internal var rbTwo: RadioButton? = null
    internal var radioGroup: RadioGroup? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    public override fun initView() {
        setContentView(R.layout.activity_selector)
        rbOne = findViewById<RadioButton>(R.id.rbOne)
        rbTwo = findViewById<RadioButton>(R.id.rbTwo)
        radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        radioGroup!!.setOnCheckedChangeListener(this)
    }

    public override fun initData() {

    }

    override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
        //        if (checkedId == rbOne.getId()) {
        //            Functions.toast(rbOne.getText().toString());
        //        } else if (checkedId == rbTwo.getId()) {
        //            Functions.toast(rbTwo.getText().toString());
        //        }
    }
}
