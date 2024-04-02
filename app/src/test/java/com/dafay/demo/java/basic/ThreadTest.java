package com.dafay.demo.java.basic;

import org.junit.Test;

/**
 * @Des
 * @Author lipengfei
 * @Date 2024/4/1
 */
public class ThreadTest {

    /*
     * 资源
     */
    class Resource {
        private int orderNum = 0;
        // 定义标记
        private boolean flag = false;

        public synchronized void produce(String name) {
            if (flag) {
                // 线程等待
                try {
                    wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // 将标记改为true
                flag = true;
                // 输出生产了哪个商品
                System.out.println(Thread.currentThread().getName() + "..生产者 生产.." + (name + ++orderNum));
                // 唤醒消费者
                notify();
            }
        }

        public synchronized void consume(String name) {
            if (!flag) {
                // 线程等待
                try {
                    wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println(Thread.currentThread().getName() + "..消费者 消费.." + (name + orderNum));
                // 将标记改为false
                flag = false;
                // 唤醒生产者
                notify();
            }
        }
    }

    /*
     * 生产者
     */
    class Producer implements Runnable {
        Resource r;

        // 生产者一初始化就要有资源
        public Producer(Resource r) {
            this.r = r;
        }

        @Override
        public void run() {
            while (true) {
                r.produce("面包");
            }
        }
    }

    /*
     * 消费者
     */
    class Customer implements Runnable {
        Resource r;

        // 消费者一初始化就要有资源
        public Customer(Resource r) {
            this.r = r;
        }

        @Override
        public void run() {
            while (true) {
                r.consume("面包");
            }
        }
    }

    @Test
    public void test() {
        // 创建资源对象
        Resource r = new Resource();
        // 创建线程任务
        Producer p = new Producer(r);
        Customer c = new Customer(r);
        // 创建线程
        Thread t1 = new Thread(p);
        Thread t2 = new Thread(c);
        // 启动线程
        t1.start();
        t2.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        t1.interrupt();
        t2.interrupt();
    }
}
