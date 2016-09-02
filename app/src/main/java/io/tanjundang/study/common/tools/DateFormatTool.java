package io.tanjundang.study.common.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Developer: TanJunDang
 * Email: TanJunDang324@gmail.com
 */
public class DateFormatTool {

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    public static final String YYYY_MM_DD_HH_MM_CN = "yyyy年MM月dd日 HH:mm";

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String YYYY_MM_DD_CN = "yyyy年MM月dd日";

    public static final String HH_MM_SS = "HH:mm:ss";

    public static final String HH_MM = "HH:mm";

    public static int secondUnit = 1000;// 1秒
    private final static int minute = 60 * secondUnit;// 1分钟
    private final static int hour = 60 * minute;// 1小时
    private final static int day = 24 * hour;// 1天
    private final static int month = 31 * day;// 月
    private final static int year = 12 * month;// 年

    private static SimpleDateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD);

    /**
     * 获取默认的日期格式 YYYY_MM_DD
     *
     * @param date
     * @return
     */
    public static String getDefaultDateStrFormat(long date) {
        return dateFormat.format(new Date(date));
    }

    /**
     * @param date   时间戳
     * @param format DateFormatUtil的格式常量
     * @return
     */
    public static String getDateStrFormat(long date, String format) {
        dateFormat.applyPattern(format);
        return dateFormat.format(new Date(date));
    }

    /**
     * 刚刚、几分钟前、HH:mm、昨天、几天前、默认日期
     *
     * @param date
     * @return
     */
    public static String getPastDateFormat(long date) {
        String dateFormat = "";
        long offset = new Date().getTime() - date; //下发时间跟当前时间的时间差

        Calendar current = Calendar.getInstance();
        current.setTimeInMillis(date);
        if (offset < minute) { //1分钟内
            dateFormat = "刚刚";
        } else if (offset < hour) {//1小时内
            dateFormat = offset / minute + "分钟前";
        } else if (offset < day) { //1天内
            dateFormat = HH_MM;
        } else if (current.before(getToday()) && current.after(getYesterday())) {
            dateFormat = "昨天 " + HH_MM;
        } else {
            dateFormat = YYYY_MM_DD;
        }
        DateFormatTool.dateFormat.applyPattern(dateFormat);
        return DateFormatTool.dateFormat.format(new Date(date));
    }

    /**
     * 获取今天的时间界限
     *
     * @return
     */
    public static Calendar getToday() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.YEAR, today.get(Calendar.YEAR));
        today.set(Calendar.MONTH, today.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, today.get(Calendar.DAY_OF_MONTH));
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        return today;
    }

    // 获取昨天的时间界限
    private static Calendar getYesterday() {
        Calendar yesterday = Calendar.getInstance();
        yesterday.set(Calendar.YEAR, yesterday.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, yesterday.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, yesterday.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        return yesterday;
    }
}
