package io.tanjundang.study.knowledge.threadpool

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import java.util.concurrent.BlockingQueue
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.RejectedExecutionHandler
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

import io.tanjundang.study.R
import io.tanjundang.study.common.tools.LogTool

/**
 * @Author: TanJunDang
 * @Date: 2018/3/22
 * @Description: 线程池总结：https://tanjundang.github.io/2015/11/18/JavaSE/#%E7%BA%BF%E7%A8%8B%E6%B1%A0%E7%9A%84%E7%90%86%E8%A7%A3
 */

class ThreadPoolActivity : AppCompatActivity() {
    internal var cachedThreadPool = Executors.newCachedThreadPool()
    internal var fixedThreadPool = Executors.newFixedThreadPool(5)
    internal var singleThreadExecutor = Executors.newSingleThreadExecutor()


    internal var number_of_cores = Runtime.getRuntime().availableProcessors()
    internal var keep_alive_time = 1
    internal var keep_alive_time_unit = TimeUnit.SECONDS
    internal var taskQueue: BlockingQueue<Runnable> = LinkedBlockingQueue()

    internal var aliService: ExecutorService = ThreadPoolExecutor(
            number_of_cores,
            number_of_cores * 2,
            keep_alive_time.toLong(),
            keep_alive_time_unit,
            taskQueue,
            Executors.defaultThreadFactory(), RejectedExecutionHandler { r, executor -> LogTool.v("task was rejected") })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread_pool)
    }

    fun ThreadManager(v: View) {
        aliService.execute { print() }
    }

    fun newCachedThreadPool(v: View) {
        cachedThreadPool.execute { print() }
    }

    fun newSingleThreadExecutor(v: View) {
        singleThreadExecutor.execute { print() }
    }

    fun newFixedThreadPool(v: View) {
        fixedThreadPool.execute { print() }
    }

    private fun print() {
        for (i in 0..99) {
            val id = Thread.currentThread().id
            LogTool.v("ThreadPoolActivity", "\nThreadName:" + Thread.currentThread().name + "\nThreadId:" + id + "\nCount:" + Thread.activeCount() + "\nnum:" + i)
        }
    }
}
