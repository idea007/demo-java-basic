package com.dafay.demo.java.basic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Des
 * @Author lipengfei
 * @Date 2024/4/1
 */
public class ThreadTest {

    private static final int CAPACITY = 10;
    private static List<Integer> buffer = new ArrayList<>();


    @Test
    public void test_producer(){
        Object lock = new Object();
        Thread producer = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    while (buffer.size() == CAPACITY) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    int item = (int) (Math.random() * 100);
                    buffer.add(item);
                    System.out.println("Produced: " + item);
                    lock.notify();
                }
            }
        });

        Thread consumer = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    while (buffer.isEmpty()) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    int item = buffer.remove(0);
                    System.out.println("Consumed: " + item);
                    lock.notify();
                }
            }
        });

        producer.start();
        consumer.start();
    }
}
