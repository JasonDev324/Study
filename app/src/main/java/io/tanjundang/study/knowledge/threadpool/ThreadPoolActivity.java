package io.tanjundang.study.knowledge.threadpool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.tanjundang.study.R;
import io.tanjundang.study.common.tools.LogTool;

/**
 * @Author: TanJunDang
 * @Date: 2018/3/22
 * @Description: 线程池总结：https://tanjundang.github.io/2015/11/18/JavaSE/#%E7%BA%BF%E7%A8%8B%E6%B1%A0%E7%9A%84%E7%90%86%E8%A7%A3
 */

public class ThreadPoolActivity extends AppCompatActivity {
    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
    ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool);
    }


    int number_of_cores = Runtime.getRuntime().availableProcessors();
    int keep_alive_time = 1;
    TimeUnit keep_alive_time_unit = TimeUnit.SECONDS;
    BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();

    ExecutorService aliService = new ThreadPoolExecutor(
            number_of_cores,
            number_of_cores * 2,
            keep_alive_time,
            keep_alive_time_unit,
            taskQueue,
            Executors.defaultThreadFactory(), new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            LogTool.v("task was rejected");
        }
    });

    public void ThreadManager(View v) {
        aliService.execute(new Runnable() {
            @Override
            public void run() {
                print();
            }
        });
    }

    public void newCachedThreadPool(View v) {
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                print();
            }
        });
    }

    public void newSingleThreadExecutor(View v) {
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                print();
            }
        });
    }

    public void newFixedThreadPool(View v) {
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                print();
            }
        });
    }

    private void print() {
        for (int i = 0; i < 100; i++) {
            long id = Thread.currentThread().getId();
            LogTool.v("ThreadPoolActivity", "\nThreadName:" + Thread.currentThread().getName() + "\nThreadId:" + id + "\nCount:" + Thread.activeCount() + "\nnum:" + i);
        }
    }
}
