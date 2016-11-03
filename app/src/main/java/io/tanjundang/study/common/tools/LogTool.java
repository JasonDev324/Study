package io.tanjundang.study.common.tools;

import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;

import io.tanjundang.study.BuildConfig;

/**
 * Developer: TanJunDang
 * Date: 2016/8/4
 * Email: TanJunDang324@gmail.com
 */
public class LogTool {

    public static boolean LOG_ENABLE = BuildConfig.DEBUG;//用于判断是否打开日志输出
    private static String CRASH_FILE = "crash.txt";//保存崩溃日志的文件

    public static boolean isLogEnable() {
        return LOG_ENABLE;
    }

    public static String LOG_RESOURCE = "TanJunDang_";//判断是否本程序输出

    /**
     * @param priority 优先级 LOG.DEBUG VERBORSE等
     * @param tag      标识
     * @param msg      输出信息 必须要判断是否为null
     */
    private static void v(int priority, String tag, Object... msg) {

        if (isLogEnable() && msg != null) {
            StringBuffer sb = new StringBuffer("");
            for (int i = 0; i < msg.length; i++) {
                if (msg[i] != null) {
                    sb.append(msg[i]);
                    sb.append(",");
                }
            }
            String str = Functions.replaceLastCharByEmpty(sb.toString(), ",");
            String result = LOG_RESOURCE + str; //添加输出位置

            switch (priority) {
                case Log.ERROR:
                    Log.e(tag, result);
                    break;
                case Log.WARN:
                    Log.w(tag, result);
                    break;
                case Log.INFO:
                    Log.i(tag, result);
                    break;
                case Log.DEBUG:
                    Log.d(tag, result);
                    break;
                case Log.VERBOSE:
                    Log.v(tag, result);
                    break;
            }

        }
    }

    /**
     * 封装输出方法
     *
     * @param tag
     * @param msg
     */
    public static void e(String tag, Object... msg) {
        v(Log.ERROR, tag, msg);
    }

    public static void w(String tag, Object... msg) {
        v(Log.WARN, tag, msg);
    }

    public static void i(String tag, Object... msg) {
        v(Log.INFO, tag, msg);
    }

    public static void d(String tag, Object... msg) {
        v(Log.DEBUG, tag, msg);
    }

    public static void v(String tag, Object... msg) {
        v(Log.VERBOSE, tag, msg);
    }

    /**
     * @param msg      待输出数据
     * @param filename 输出文件名
     */
    public static void log2File(String msg, String filename) {
        if (TextUtils.isEmpty(filename)) { //文件名初始化
            filename = CRASH_FILE;
        }

        File file = Functions.getSDCardFile("Log", filename);
        FileWriter fw = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file);
            fw.write(msg);
            fw.flush();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
            LogTool.e(e.getClass().getName(), e.getMessage());
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
