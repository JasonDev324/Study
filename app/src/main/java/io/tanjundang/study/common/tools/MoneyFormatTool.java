package io.tanjundang.study.common.tools;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @Author: TanJunDang
 * @Date: 2018/6/1
 * @Description: DecimalFormat的使用
 * https://blog.csdn.net/z69183787/article/details/20055739
 */
public class MoneyFormatTool {

    /**
     * Number默认带两位小数
     */
    public static NumberFormat numFormat = NumberFormat.getCurrencyInstance();
//    public static NumberFormat numFormat = new DecimalFormat("#,###.##");

    /**
     * 返回货币形式的金额（带顿号）
     *
     * @param money
     * @return ￥100，1000.12
     */
    public static String getPointMoney(double money) {
        return numFormat.format(money);
    }

    /**
     * @Author: TanJunDang
     * @Date: 2018/6/1
     * @Description: 相加
     */
    public static double add(double x, double y) {
        BigDecimal xValue = new BigDecimal(Double.toString(x));
        BigDecimal yValue = new BigDecimal(Double.toString(y));
        return xValue.add(yValue).doubleValue();
    }

    /**
     * @Author: TanJunDang
     * @Date: 2018/6/1
     * @Description: 相减
     */
    public static double sub(double x, double y) {
        BigDecimal xValue = new BigDecimal(Double.toString(x));
        BigDecimal yValue = new BigDecimal(Double.toString(y));
        return xValue.subtract(yValue).doubleValue();
    }

    /**
     * @Author: TanJunDang
     * @Date: 2018/6/1
     * @Description: 相乘
     */
    public static double mul(double x, double y) {
        BigDecimal xValue = new BigDecimal(Double.toString(x));
        BigDecimal yValue = new BigDecimal(Double.toString(y));
        return xValue.multiply(yValue).doubleValue();
    }

    /**
     * @Author: TanJunDang
     * @Date: 2018/6/1
     * @Description: 除法
     */

    public static double divide(double dividend, double divisor) {
//        默认除法精度
        int scale = 2;
        BigDecimal xValue = new BigDecimal(Double.toString(dividend));
        BigDecimal yValue = new BigDecimal(Double.toString(divisor));
        return xValue.divide(yValue, scale, RoundingMode.HALF_UP).doubleValue();
    }

}
