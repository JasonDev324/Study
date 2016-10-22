package io.tanjundang.study.knowledge.scrollconflict;

import java.io.Serializable;

/**
 * Developer: TanJunDang
 * Email: TanJunDang324@gmail.com
 * Date: 2016/10/22
 */

public class TestListInfo implements Serializable {
    private String title;

    public TestListInfo(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
