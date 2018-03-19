package io.tanjundang.study.knowledge.audio;

import java.io.File;
import java.util.ArrayList;

import io.tanjundang.study.common.tools.DateFormatTool;
import io.tanjundang.study.common.tools.Functions;

/**
 * @Author: TanJunDang
 * @Date: 2018/3/16
 * @Description:
 */
public class AudioModel {

    ArrayList<AudioInfo> list = new ArrayList<>();

    public AudioModel() {
        loadData();
    }

    public ArrayList<AudioInfo> getList() {
        return list;
    }

    public void setList(ArrayList<AudioInfo> list) {
        this.list = list;
    }

    public void loadData() {
        File fileDir = Functions.getSDCardFolder("audio");

        for (File file : fileDir.listFiles()) {
            AudioInfo info = new AudioInfo();
            String date = Functions.getFileNameFromUrlNoTail(file.getAbsolutePath());
            long time = DateFormatTool.getDateFromStr("yyyy-MM-dd-HH-mm-ss", date);
            String newDate = DateFormatTool.getDateStrFormat(time, "yyyy-MM-dd HH:mm:ss");
            info.setDate(time);
            info.setTitleDate(newDate);
            info.setAudiodata(file.getAbsolutePath());
            list.add(info);
        }
    }

}
