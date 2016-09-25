package io.tanjundang.study.test.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by TanJunDang on 2016/9/26.
 */

public class NotifyReceiver extends BroadcastReceiver {

    public static final String NOTIFY_ACTION = "io.tanjundang.study.NotifyReceiver";

    public NotifyCallback callback;

    public NotifyReceiver() {
    }

    public NotifyReceiver(NotifyCallback imp) {
        callback = imp;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (callback != null) {
            callback.refresh();
        }
    }

    public interface NotifyCallback {
        void refresh();
    }
}
