package io.tanjundang.study.knowledge.jpush;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import cn.jpush.android.api.JPushInterface;
import me.jessyan.autosize.utils.LogUtils;

/**
 * @Author: TanJunDang
 * @Date: 2019/2/22 11:15
 * @Description: 推送接收处理
 */
public class PushReceiver extends BroadcastReceiver {
    private NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getExtras();

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            LogUtils.d("JPush用户注册成功");

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            LogUtils.d("接受到推送下来的自定义消息");

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            LogUtils.d("接受到推送下来的通知");

            receivingNotification(bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            LogUtils.d("用户点击打开了通知");

            openNotification(bundle);

        } else {
            LogUtils.d("Unhandled intent - " + intent.getAction());
        }
    }

    private void receivingNotification(Bundle bundle) {
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        LogUtils.d(" title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        LogUtils.d("message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        LogUtils.d("extras : " + extras);
    }

    private void openNotification(Bundle bundle) {
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        try {
            LogUtils.d(extras);
        } catch (Exception e) {
            LogUtils.e("Unexpected: extras is not a valid json");
            return;
        }

    }
}