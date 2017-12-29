package io.tanjundang.study.knowledge.databinding;

import android.view.View;

import io.tanjundang.study.common.tools.Functions;

/**
 * @Author: TanJunDang
 * @Email: TanJunDang@126.com
 * @Date: 2017/12/20
 * @Description: click事件必须传递view参数
 */

public interface EventListener {

    //    此处必须有View 的参数
    void onTitleClick(View view);
}
