package io.tanjundang.study.common.tools;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.text.TextUtils;

import com.nostra13.universalimageloader.cache.disc.impl.ext.LruDiskCache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;


/**
 * @Author: TanJunDang
 * @Date: 2017/12/6
 * @Description: 处理拍照后的图片
 * 拍照后的图片由于文件过大，所以在上传前一般会进行压缩。因此会进行一系列的操作。
 * 压缩后的图片，exif信息会丢失
 * 1.加载图片到bitmap
 * 2.压缩图片
 * 3.翻转图片
 * 4.保存图片
 */

public class PhotoTool {

    public static PhotoTool getInstance() {
        return Holder.INSTANCE;
    }

    static class Holder {
        static PhotoTool INSTANCE = new PhotoTool();
    }


    /**
     * 压缩图片
     *
     * @param sourceImgPath 原图片路径
     * @param outputPath    压缩后图片的输出路径
     * @param fileName      输出文件名
     * @return 压缩后的bitmap，无exif
     */
    public Bitmap getZipImage(String sourceImgPath, String outputPath, String fileName, int reqWidth, int reqHeight) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(outputPath, fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        Bitmap bmp = null;

        int w = opts.outWidth;
        int h = opts.outHeight;

        int sampleSize = 1;
        if (w > h && w > reqWidth) {
            sampleSize = (int) (w / reqWidth);
        } else if (w < h && h > reqHeight) {
            sampleSize = (int) (h / reqHeight);
        }
        if (sampleSize <= 0)
            sampleSize = 1;
        opts.inSampleSize = sampleSize;
        opts.inJustDecodeBounds = false;
        bmp = BitmapFactory.decodeFile(sourceImgPath, opts);
        bmp.compress(Bitmap.CompressFormat.JPEG, 10, fos);

        return bmp;
    }

    /**
     * 返回一个压缩过的bitmap
     *
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeBitmapFromRes(Resources res, int resId, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
//        inJustDecodeBounds=true 解析获取图片尺寸,不会加载图片
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

//        根据图片分辨率以及需要显示的大小计算采样率
//     todo  inSampleSize=2表示宽高均压缩为原来的二分之一

        int sampleSize = 1;
        int width = options.outWidth;
        int height = options.outHeight;
        sampleSize = calcSampleSizeNormal(width, height, reqWidth, reqHeight);
        options.inSampleSize = sampleSize;
//        设置压缩率并解码
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 通常计算采样率的方法
     *
     * @param width
     * @param height
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calcSampleSizeNormal(int width, int height, int reqWidth, int reqHeight) {
        int sampleSize = 1;
        if (width > reqWidth || height > reqHeight) {
            float fW = Math.round((float) width / reqWidth);
            float fH = Math.round((float) height / reqHeight);
            sampleSize = (int) Math.min(fW, fH);
        }
        return sampleSize;
    }


    /**
     * 开发探索艺术推荐
     * sampleSize应为2的指数
     *
     * @param width
     * @param height
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calcSampleSize(int width, int height, int reqWidth, int reqHeight) {
        int sampleSize = 1;
        if (width > reqWidth || height > reqHeight) {
            int halfW = width / 2;
            int halfH = height / 2;
            while (halfH / sampleSize >= reqHeight && (halfW / sampleSize >= reqWidth)) {
                sampleSize *= 2;
            }
        }
        return sampleSize;
    }

    /**
     * 旋转压缩后的bitmap
     *
     * @param bitmap       压缩后的图片
     * @param srcImagePath 原图片的路径(获取原图片的exif信息,加载到bitmap)
     */
    public Bitmap rotateZipImage(Bitmap bitmap, String srcImagePath) {
        int degree = getPicRotate(srcImagePath);
        if (degree != 0) {
            Matrix m = new Matrix();
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            m.setRotate(degree); // 旋转angle度
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, m, true);// 从新生成图片
        }
        return bitmap;
    }

    /**
     * 保存bitmap到本地
     *
     * @param zipBitmap 压缩后的bitmap
     * @param savePath  路径名
     * @param fileName  文件名
     * @return
     */
    public boolean saveZipBitmap(Bitmap zipBitmap, String savePath, String fileName) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(savePath, fileName));
            zipBitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 读取图片文件旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片旋转的角度
     */
    private int getPicRotate(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 由于压缩后的图片会翻转，所以需要获取原图片的exif信息
     * 复制原图片的exif信息，将原图片的exif信息复制到压缩后的图片上
     *
     * @param oldFilePath 原图片的path
     * @param newFilePath 压缩后图片的path
     * @throws Exception
     */
    public static void saveExif(String oldFilePath, String newFilePath) throws Exception {
        ExifInterface oldExif = new ExifInterface(oldFilePath);
        ExifInterface newExif = new ExifInterface(newFilePath);
        Class<ExifInterface> cls = ExifInterface.class;
        Field[] fields = cls.getFields();
        for (int i = 0; i < fields.length; i++) {
            String fieldName = fields[i].getName();
            if (!TextUtils.isEmpty(fieldName) && fieldName.startsWith("TAG")) {
                String fieldValue = fields[i].get(cls).toString();
                String attribute = oldExif.getAttribute(fieldValue);
                if (attribute != null) {
                    newExif.setAttribute(fieldValue, attribute);
                }
            }
        }
        newExif.saveAttributes();
    }

}
