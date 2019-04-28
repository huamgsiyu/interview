package com.hsy.lock;

/**
 * @author hsy
 * @createtime 2019/4/28 13:52
 */

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 1 Semaphore
 *  1.1 信号量主要用于两个目的：
 *      1.1.1 用于多个共享资源的互斥使用
 *      1.1.2 用于并发线程数的控制
 *  1.2 当semaphore的permits=1时，相当于sychrozied
 * 2 停车位的demo
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);

        for (int i = 0; i < 7; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() +
                            "\t抢到了车位");
                    try {
                        TimeUnit.MILLISECONDS.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            },String.valueOf(i)).start();
        }
    }
}
