package io.tanjundang.study.common.tools;

import android.app.Activity;
import android.content.Context;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Developer: TanJunDang
 * Email: TanJunDang324@gmail.com
 * Date: 2016/10/10
 * 在需要分享的页面重写onActivityResult方法，待改善
 */

public class ShareTool {

    public static final String DEFAULT_TITLE = "TanJunDang's Blog";
    public static final String DEFAULT_CONTENT = "Android 开发笔记";
    public static final String DEFAULT_URL = "https://tanjundang.github.io";


    private static class Holder {
        private static ShareTool INSTANCE = new ShareTool();
    }

    public static ShareTool getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 默认不需要回调的方法
     * 仅用于推荐博客
     *
     * @param context
     */
    public void SendMessage(Context context) {
        SendMessage(context, DEFAULT_TITLE, DEFAULT_CONTENT, DEFAULT_URL, null);
    }

    /**
     * @param context
     * @param title
     * @param content
     * @param url      url地址必须是包括http开头的，否则启动不了
     * @param callback 分享成功回调
     */
    public void SendMessage(Context context, String title, String content, String url, UMShareListener callback) {
        ShareContent shareContent = new ShareContent();
        shareContent.mTitle = title;
        shareContent.mText = content;
        shareContent.mTargetUrl = url;
        new ShareAction((Activity) context)
                .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setShareContent(shareContent)
                .setCallback(callback)
                .open();
    }


}
