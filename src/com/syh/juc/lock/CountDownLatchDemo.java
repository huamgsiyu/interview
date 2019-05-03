package com.syh.juc.lock;

/**
 * @author hsy
 * @createtime 2019/4/26 7:55
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch
 * 1.并发中控制多线程，当每个线程都完成后才继续跑下去
 */
public class CountDownLatchDemo {

    private volatile static CountDownLatch cdl = new CountDownLatch(10);

    public static void main(String[] args) {

    }

    //CountDownLatch对线程的控制
    public static void countDown(){
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }

                System.out.println(Thread.currentThread().getName()+"\t");
                cdl.countDown();
            },i + "").start();
        }
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis()-start);
    }
}
