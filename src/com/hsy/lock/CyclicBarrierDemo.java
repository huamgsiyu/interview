package com.hsy.lock;

/**
 * @author hsy
 * @createtime 2019/4/28 13:24
 */

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 1、CyclicBarrier
 *  1.1定义：可循环（Cyclic）使用的屏障（Barrier）。它要做的事情是，让一组线程到达一个屏障（也可以
 *      叫同步点）时被阻塞，直到最后一个线程到达屏障时，屏障才会开发，所有被屏障拦截的线程才会继续干活，线程
 *      进入屏障通过CyclicBarrier的await()方法；
 *
 *
 * 2、CyclicBarrier和CountDownLatch的区别
 *  CyclicBarrier：从0增长到一个值后停止
 *  CountDownLatch:从一个值减到0后停止
 *
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        //    public CyclicBarrier(int parties, Runnable barrierAction)
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,
                () -> {
            System.out.println("*********召唤神龙");
        });

        for (int i = 0; i < 7; i++) {
            final int index = i;
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() +
                        "\t 收集到第：" + index + "龙珠");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
    }
}
