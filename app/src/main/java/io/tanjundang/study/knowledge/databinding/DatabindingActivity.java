package io.tanjundang.study.knowledge.databinding;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import io.tanjundang.study.R;
import io.tanjundang.study.databinding.ActivityDatabindingBinding;

public class DatabindingActivity extends Activity {
    ActivityDatabindingBinding binding;
    TestModel data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_databinding);
        data = new TestModel();
        data.title.set("Tan");
        data.value.set("JunDang");
        binding.setModel(data);
        binding.setListener(new EventListener() {
            @Override
            public void onTitleClick(View view) {
                data.title.set("Chen");
                data.value.set("https://tanjundang.github.io/img/lrving.jpg");
            }
        });
    }
}
