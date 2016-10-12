package io.tanjundang.study.common.tools;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import io.tanjundang.study.BuildConfig;
import io.tanjundang.study.R;


/**
 * Developer: TanJunDang
 * Email: TanJunDang324@gmail.com
 */
public class Functions {

    public static int MALE = 0;//男
    public static int FEMALE = 1;//女
    public static int REQ_PICTURE = 0XFF;
    private static Context appContext;

    public static void init(Context context) {
        appContext = context;
    }

    public static void toast(CharSequence text) {
        if (appContext == null) {
            return;
        }
        Toast.makeText(appContext, text, Toast.LENGTH_SHORT).show();
    }

    public static void snack(View view, CharSequence text) {
        if (view == null) {
            return;
        }
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
    }

    public static void snack(View view, CharSequence mainText, CharSequence actionText, final View.OnClickListener listener) {
        if (view == null) {
            return;
        }
        Snackbar.make(view, mainText, Snackbar.LENGTH_SHORT)
                .setAction(actionText, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null) {
                            listener.onClick(view);
                        }
                    }
                })
                //还可以通过Color.parse来修改颜色
                .setActionTextColor(Color.parseColor("#2ecc71"))
                .show();
    }


    /**
     * 隐藏软键盘
     *
     * @param view
     */
    public static void hideInputMethod(View view) {
        InputMethodManager imm = (InputMethodManager) appContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘
     *
     * @param view
     */
    public static void showInputMethodForQuery(View view) {

        InputMethodManager imm = (InputMethodManager) appContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, 0);
        }
    }


    /**
     * 根据名字获取对应的图片资源id
     * 不需要R.drawable前缀
     *
     * @param s
     * @return
     */
    public static int getResourceIdByString(String s) {
        try {
            Field field = R.drawable.class.getField(s);
            return Integer.parseInt(field.get(null).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.drawable.ic_menu_share;
    }

    /**
     * 此方法只是获取手机的当前时间
     * 必须要加上服务器下发的时间跟系统的时间差(offset = serverTime - systemTime)。
     *
     * @return
     */
    public static long getCurrentFixTime() {
        int offset = 0;
        return Calendar.getInstance().getTimeInMillis() + offset;
    }

    /**
     * 获取屏幕的高度
     *
     * @return
     */
    public static float getScreenHeight() {
        WindowManager manager = (WindowManager) appContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static float getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) appContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
//        return ((Activity)context).getWindowManager().getDefaultDisplay().getWidth();//另一种获取屏幕的方式
        return metrics.widthPixels;
    }

    /**
     * dp 转 px
     *
     * @param dpVal
     * @return
     */
    public static int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, appContext.getResources().getDisplayMetrics());
    }


    /**
     * px 转  dp
     *
     * @param pxVal
     * @return
     */
    public static float px2dp(float pxVal) {
        final float scale = appContext.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * 根据值来显示男女
     */

//    public static String getStringByGender(Context context, int gender) {
//        Resources resources = context.getResources();
//        String sex = "";
//        if (gender == MALE) {
//            sex = resources.getString(R.string.common_sex_male);
//        } else {
//            sex = resources.getString(R.string.common_sex_female);
//        }
//        return sex;
//    }

    /**
     * 将一个String集合进行逗号拼装
     *
     * @param list
     * @return
     */
    public static String getStringListByPoint(ArrayList<String> list) {
        String result = "";
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            String text = (String) iterator.next();
            result += text + ",";
        }
        result = result.substring(0, result.length() - 1);
        return result;
    }

    /**
     * 判断是否以某个字符结尾,是则去掉字符串最后一个字符
     *
     * @param str
     * @return
     */
    public static String replaceLastCharByEmpty(String str, String Char) {
        String result = str.endsWith(Char) ? str.substring(0, str.length() - 1) : str;
        return result;
    }


    /**
     * 获取图片的File路径
     *
     * @param uri
     * @return
     */
    @SuppressLint("NewApi")
    private static String getPath(final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(appContext, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(appContext, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(appContext, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(appContext, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

//    /**
//     * 需要在onactivityresult里用REQ_PICTURE接收本地图片数据
//     *
//     * @param context
//     */
//    public static void SkipToImagePickActivity(Context context) {
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("image/*");
//        ((Activity) context).startActivityForResult(Intent.createChooser(intent, context.getString(R.string.pick_title_choose_picture)), REQ_PICTURE);
//    }

    /**
     * 从Intent获取本地path
     * 用法：用在onActivityResult里
     *
     * @param context
     * @param data
     * @return
     */
//    public static void getImageFromIntent(Context context, Intent data, PictureCallBack callback) {
//        String path = "";
//        Uri uri = data.getData();
//        path = Functions.getPath(context, uri);
//        upload(path, callback);
//    }


    /**
     * 获取sd卡路径,如果没有sd卡路径,就在/data/data下创建
     *
     * @return
     */
    public static String getSDPath() {
        String sdPath = "";
        if (isSDCardExist()) {
            sdPath = Environment.getExternalStorageDirectory() + "/TJD_NBA/";
        } else {
            sdPath = Environment.getDataDirectory() + "/TJD_NBA/";
        }

        File file = new File(sdPath); //创建路径
        if (!file.exists()) {
            file.mkdirs();
        }
        return sdPath;
    }

    /**
     * 判断SD卡是否存在
     *
     * @return
     */
    public static boolean isSDCardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 查看APK版本 展示所用 1.2.0
     */
    public static String getAppVersionName() {
        String versionName = "";
//        PackageManager manager = context.getPackageManager();
//        try {
//            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
//            versionName = info.versionName;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
        versionName = BuildConfig.VERSION_NAME;
        return versionName;
    }

    public static String getChannel() {
        String chanel = "Offcial";
        String gitVersion = "";
        try {
            ApplicationInfo info = appContext.getPackageManager().getApplicationInfo(appContext.getPackageName(), PackageManager.GET_META_DATA);
            gitVersion = info.metaData.getString("GIT");
//            chanel = info.metaData.getString("UMENG_CHANNEL");
//            chanel = String.format("%s-%s-%s", chanel, gitVersion, BuildConfig.BUILD_TYPE);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        chanel = String.format("%s-%s-%s", BuildConfig.FLAVOR, gitVersion, BuildConfig.BUILD_TYPE);
        return chanel;
    }

    /**
     * 获取版本ID 用于与服务端版本号对比，从而决定是否需要升级
     * <p/>
     * 可使用BuildType直接获取版本号、版本、渠道号、BuildType等数据
     *
     * @return
     */
    public static int getAppVersionCode() {
        int versionCode = 0;
//        PackageManager manager = appContext.getPackageManager();
//        try {
//            PackageInfo info = manager.getPackageInfo(appContext.getPackageName(), 0);
//            versionCode = info.versionCode;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
        versionCode = BuildConfig.VERSION_CODE;
        return versionCode;
    }

    public static void SkipToAppDetail() {
        //跳转到当前应用详情页面
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.fromParts("package", appContext.getPackageName(), null));
        appContext.startActivity(intent);
    }
}
