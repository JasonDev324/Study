package io.tanjundang.study;

/**
 * Developer: TanJunDang
 * Email: TanJunDang324@gmail.com
 * Date: 2016/9/5
 */

public class DateItemBean {
    private int title;
    Type type;

    public enum Type {
        ANIMATION,
        DRAWERLAYOUT,
        SHAPE,
        SELECTOR,
        PREFERENCE,
        ACTIONBAR,
        INTENT,
        LAUNCHMODE,
        GUIDE
    }

    public DateItemBean(int title, Type type) {
        this.title = title;
        this.type = type;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
