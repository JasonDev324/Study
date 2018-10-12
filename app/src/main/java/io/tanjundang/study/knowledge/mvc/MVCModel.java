package io.tanjundang.study.knowledge.mvc;

/**
 * @Author: TanJunDang
 * @Date: 2018/10/12
 * @Description: 负责数据的封装处理、网络请求、数据库操作等。
 */
public class MVCModel {

    MVCActivity activity;

    public MVCModel(MVCActivity activity) {
        this.activity = activity;
    }

//    加载数据
    public void loadData() {
        String data = "load data success";
//        model要通知View，必须有view的引用或者提供接口
        activity.updateUI(data);
    }
}
