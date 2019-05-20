package io.tanjundang.study.knowledge.mvc

/**
 * @Author: TanJunDang
 * @Date: 2018/10/12
 * @Description: 负责数据的封装处理、网络请求、数据库操作等。
 */
class MVCModel(internal var activity: MVCActivity) {

    //    加载数据
    fun loadData() {
        val data = "load data success"
        //        model要通知View，必须有view的引用或者提供接口
        activity.updateUI(data)
    }
}
