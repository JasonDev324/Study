package io.tanjundang.study.knowledge.audio;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import io.tanjundang.study.AudioRecordTool;
import io.tanjundang.study.R;
import io.tanjundang.study.databinding.ActivityAudioRecordBinding;

/**
 * @Author: TanJunDang
 * @Date: 2018/3/16
 * @Description: 语音录制Demo
 */

public class AudioRecordActivity extends AppCompatActivity {
    ActivityAudioRecordBinding mBinding;
    AudioAdapter mAdapter;
    AudioModel model = new AudioModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_audio_record);
        mBinding.setModel(new AudioModel());
        mAdapter = new AudioAdapter(model.getList());
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AudioRecordTool.getInstance().stopPlayer();
    }
}
