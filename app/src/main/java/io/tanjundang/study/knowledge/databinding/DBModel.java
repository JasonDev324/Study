package io.tanjundang.study.knowledge.databinding;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import io.tanjundang.study.common.tools.ImageLoaderTool;

/**
 * @Author: TanJunDang
 * @Email: TanJunDang@126.com
 * @Date: 2017/12/22
 * @Description: 当在xml里需要调用一些方法时，需要用到bindingAdapter来定义属性，从而在xml中使用
 */

public class DBModel {
    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView imageView, String url) {
        ImageLoaderTool.getInstance().loadImage(url, imageView);
    }

    @BindingConversion
    public static Drawable convertColorToDrawable(int drawable) {
        return new ColorDrawable(drawable);
    }
}
