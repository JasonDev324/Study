package io.tanjundang.study.common.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * @Author: TanJunDang
 * @Date: 2018/4/3
 * @Description: 参考
 * https://blog.csdn.net/chenrushui/article/details/52768660
 */
public class BitmapCacheTool {
    LruCache<String, Bitmap> lruCache;
    Context mContext;
    public static final int HANDLE_SUCCESS = 0xff;
    public static final int HANDLE_ERROR = 0xfa;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLE_SUCCESS:
                    BitmapInfo info = (BitmapInfo) msg.obj;
                    info.iv.setImageBitmap(info.bitmap);
                    break;
                case HANDLE_ERROR:
                    LogTool.v(getClass().getName(), "bitmap 下载出错");
                    break;
            }
        }
    };

    public BitmapCacheTool(Context context) {
        mContext = context;
        int maxSize = (int) (Runtime.getRuntime().maxMemory() / 8);
        lruCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    /**
     * fd
     * 从缓存中获取bitmap
     *
     * @param key
     * @return
     */
    public Bitmap get(String key) {
        return lruCache.get(key);
    }

    public void put(String key, Bitmap bitmap) {
        lruCache.put(key, bitmap);
    }

    public void display(final ImageView imageView, final String url) {
        final Bitmap bitmap = get(url);
        if (bitmap == null) {
//            执行下载并显示
//            Glide须运行在主线程，所以此处没有开启新线程来下载
            Glide.with(mContext).load(url).asBitmap().toBytes().into(new SimpleTarget<byte[]>() {
                @Override
                public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
                    try {
                        Bitmap downLoadBmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                        添加到缓存
                        put(url, downLoadBmp);
                        BitmapInfo info = new BitmapInfo();
                        info.iv = imageView;
                        info.bitmap = downLoadBmp;
                        Message message = handler.obtainMessage(HANDLE_SUCCESS, info);
                        message.sendToTarget();
                    } catch (Exception e) {
                        handler.sendEmptyMessage(HANDLE_ERROR);
                    }
                }
            });

        } else {
            imageView.setImageBitmap(bitmap);
        }
    }


    class BitmapInfo {
        ImageView iv;
        Bitmap bitmap;
    }

}
