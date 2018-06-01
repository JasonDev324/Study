package io.tanjundang.study.test;

/**
 * @Author: TanJunDang
 * @Date: 2018/5/11
 * @Description:
 */
public enum TestEnum {
    ADD {
        @Override
        double apply(double x, double y) {
            return x + y;
        }
    },
    SUB {
        @Override
        double apply(double x, double y) {
            return x - y;
        }
    };

    abstract double apply(double x, double y);

}
