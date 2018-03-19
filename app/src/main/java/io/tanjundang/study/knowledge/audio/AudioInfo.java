package io.tanjundang.study.knowledge.audio;

import android.graphics.drawable.AnimationDrawable;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import io.tanjundang.study.AudioRecordTool;
import io.tanjundang.study.R;
import io.tanjundang.study.common.tools.Functions;

/**
 * @Author: TanJunDang
 * @Date: 2018/3/19
 * @Description:
 */
public class AudioInfo {
    String titleDate;
    String audiodata;
    long date;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getAudiodata() {
        return audiodata;
    }

    public void setAudiodata(String audiodata) {
        this.audiodata = audiodata;
    }

    public void setTitleDate(String date) {
        this.titleDate = date;
    }

    public String getTitleDate() {
        return titleDate;
    }


    public void playAudio(ImageView imageView) {
        final AnimationDrawable anim = (AnimationDrawable) imageView.getBackground();
        AudioRecordTool.getInstance().play(audiodata, new AudioRecordTool.AudioPlayListener() {
            @Override
            public void start() {
                anim.start();
            }

            @Override
            public void stop() {
                anim.setVisible(true, true);
                anim.stop();
            }
        });
    }
}
