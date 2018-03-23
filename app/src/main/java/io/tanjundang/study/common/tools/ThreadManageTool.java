package io.tanjundang.study.common.tools;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: TanJunDang
 * @Email: TanJunDang@126.com
 * @Date: 2017/5/15
 * 参考：https://tanjundang.github.io/2015/11/18/JavaSE/#%E7%BA%BF%E7%A8%8B%E6%B1%A0%E7%9A%84%E7%90%86%E8%A7%A3
 */

public class ThreadManageTool {
    private ThreadPoolExecutor executor;
    //   阿里规范 通常为cpu数
    private int corePoolSize = 0;
    //    通常为cpu数*2
    private int maxPoolSize = 0;
    //    超时设置为1s
    private long keepAliveTime = 1;
    private TimeUnit timeUnit = TimeUnit.SECONDS;
    //    队列长度为5，阿里规范用的是LinkedBlockingQueue，默认值是Integer.MAX_VALUE
    private final int QUEUE_SIZE = 5;
    private int cpu;

    public ThreadManageTool() {
//        获取当前设备CPU数
        cpu = Runtime.getRuntime().availableProcessors();
        corePoolSize = cpu;
        maxPoolSize = cpu * 2;
        executor = new ThreadPoolExecutor(
//                核心线程数
                corePoolSize,
//                最大线程数
                maxPoolSize,
//                空闲线程超时时间
                keepAliveTime,
//                超时时间单位
                timeUnit,
//                队列
                new LinkedBlockingQueue<Runnable>(QUEUE_SIZE),
//                线程工厂
                Executors.defaultThreadFactory(),
//                采取丢弃任务 不抛异常的拒绝处理
                new ThreadPoolExecutor.DiscardPolicy());
    }

    public static ThreadManageTool getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static ThreadManageTool INSTANCE = new ThreadManageTool();
    }

    public void execute(Runnable task) {
        if (task == null) return;
        executor.execute(task);
    }

    public void cancel(Runnable task) {
        //判断线程对象是否存在,是否正在运行
        if (task != null) {
            if (executor != null && !executor.isShutdown()) {
                //获取线程池中排队的任务队列,移除任务
                executor.getQueue().remove(task);
            }
        }
    }
}
