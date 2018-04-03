package io.tanjundang.study.common.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * @Author: TanJunDang
 * @Date: 2018/4/3
 * @Description:
 */
public class BitmapCacheUtil {
    LruCache<String, Bitmap> lruCache;
    Context mContext;

    public BitmapCacheUtil(Context context) {
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
        Bitmap bitmap = get(url);
        if (bitmap == null) {
//            执行下载并显示
            Glide.with(mContext).load(url).asBitmap().toBytes().into(new SimpleTarget<byte[]>() {
                @Override
                public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
                    try {
                        Bitmap downLoadBmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                        添加到缓存
                        put(url, downLoadBmp);
                        imageView.setImageBitmap(downLoadBmp);
                        LogTool.v(getClass().getName(), "下载图片\n地址：" + url);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            LogTool.v(getClass().getName(), "缓存图片\nkey：" + url);
            imageView.setImageBitmap(bitmap);
        }
    }

}
