package io.tanjundang.study.common.tools;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: TanJunDang
 * @Email: TanJunDang@126.com
 * @Date: 2017/5/15
 */

public class ThreadManageTool {
    private ThreadPoolExecutor executor;
    private int corePoolSize = 3;
    private int maxPoolSize = 5;
    private long keepAliveTime = 30;
    private TimeUnit timeUnit = TimeUnit.MILLISECONDS;
    private final int QUEUE_SIZE = 5;
    private int cpu;

    public ThreadManageTool() {
//        获取当前设备CPU数
        cpu = Runtime.getRuntime().availableProcessors();
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
                new ArrayBlockingQueue<Runnable>(QUEUE_SIZE),
//                线程工厂
                Executors.defaultThreadFactory(),
//                采取丢弃抛不异常的拒绝处理
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
