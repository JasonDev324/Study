package io.tanjundang.study.knowledge.databinding;

import android.databinding.ObservableField;

/**
 * @Author: TanJunDang
 * @Email: TanJunDang@126.com
 * @Date: 2017/12/20
 * @Description:
 */

public class TestModel {
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> value = new ObservableField<>();

    public ObservableField<String> getTitle() {
        return title;
    }

    public ObservableField<String> getValue() {
        return value;
    }
}
