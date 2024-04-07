package com.dafay.demo.java.basic;

import com.dafay.demo.lib.base.utils.LogExtKt;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Des
 * @Author lipengfei
 * @Date 2024/3/29
 */
public class ExecutorsUnitTest {
    private int queueCapacity = 20;
    BlockingQueue blockingQueue = new ArrayBlockingQueue(queueCapacity);

    class MyRunnable implements Runnable {
        private String taskName;

        public MyRunnable(String taskName) {
            this.taskName = taskName;
        }

        @Override
        public void run() {
            LogExtKt.println(this, "start " + taskName);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LogExtKt.println(this, "end " + taskName);
        }
    }

    @Test
    public void test_threadPoolExecutor() {
        // 当任务数 < corePoolSize+ queueCapacity
        // 使用核心线程执行任务，其他任务在阻塞队列等待
        int corePoolSize = 5;
        int maxPoolSize = 10;
        long keepAliveTime = 2;
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                blockingQueue,
                new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < 10; i++) {
            Runnable temp = new MyRunnable("task " + i);
            executor.execute(temp);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        LogExtKt.println(this, "执行结束");
    }

    @Test
    public void test_threadPoolExecutor1() {
        // 当 corePoolSize+ queueCapacity  < 任务数 <  maxPoolSize+queueCapacity
        // 创建 6 个线程执行任务，其他任务在阻塞队列等待
        int corePoolSize = 5;
        int maxPoolSize = 10;
        long keepAliveTime = 2;
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                blockingQueue,
                new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < 26; i++) {
            Runnable temp = new MyRunnable("task " + i);
            executor.execute(temp);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        LogExtKt.println(this, "执行结束");
    }

    @Test
    public void test_threadPoolExecutor2() {
        //  任务数 > maxPoolSize + queueCapacity,
        int corePoolSize = 5;
        int maxPoolSize = 10;
        long keepAliveTime = 2;
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                blockingQueue,
                new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 35; i++) {
            Runnable temp = new MyRunnable("task " + i);
            executor.execute(temp);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        LogExtKt.println(this, "执行结束");
    }
}
