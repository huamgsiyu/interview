package com.hsy.queue;

/**
 * @author hsy
 * @createtime 2019/4/28 21:49
 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 传统版--生产者消费者模式
 *
 * 题目：一个初始值为0的变量，两个线程对其交替操作，一个加1一个减1，来5轮
 *
 * 1、线程     操作（方法）      资源类
 *
 * 2、判断     干活              通知
 *
 * 3、防止虚假唤醒机制（用if判断会出现虚假唤醒的情况，应该用while）
 */
public class ProdConsumer_TraditionDemo {

    public static void main(String[] args) {
        ShareData shareData = new ShareData();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                shareData.productor();
            }
        }, "AA").start();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                shareData.consumer();
            }
        }, "BB").start();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                shareData.productor();
            }
        }, "CC").start();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                shareData.consumer();
            }
        }, "DD").start();
    }
}

class ShareData{
    private int i = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void productor(){
        lock.lock();
        try{
            //此处用if会存在虚假唤醒的情况，当线程数大于2时，更加明显
            while (i != 0)
            {
                condition.await();
            }
            System.out.println(Thread.currentThread().getName() +
                "生产了");
            i++;
            condition.signalAll();
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            lock.unlock();
        }
    }

    public void consumer(){
        lock.lock();
        try{
            //此处用if会存在虚假唤醒的情况，当线程数大于2时，更加明显
            while (i == 0){
                condition.await();
            }
            System.out.println(Thread.currentThread().getName() +
                "消费了");
            i--;
            condition.signalAll();
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            lock.unlock();
        }
    }
}
