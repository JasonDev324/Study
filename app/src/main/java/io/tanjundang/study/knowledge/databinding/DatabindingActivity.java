package io.tanjundang.study.knowledge.databinding;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import io.tanjundang.study.R;
import io.tanjundang.study.base.BaseActivity;
import io.tanjundang.study.databinding.ActivityDatabindingBinding;

public class DatabindingActivity extends Activity {
    ActivityDatabindingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_databinding);
        binding.setItem(new DBItemBean("Tan","JunDang"));
    }
}
