package io.tanjundang.study.knowledge.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import io.tanjundang.study.common.tools.Functions;

/**
 * Created by TanJunDang on 2016/9/25.
 */

public class NetworkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Functions.toast("Network Change");
    }
}
