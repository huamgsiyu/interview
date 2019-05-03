package com.syh.juc.lock;

/**
 * @author hsy
 * @createtime 2019/4/28 22:50
 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 题目：多线程之间按顺序调用，实现A->B->C三个线程启动，要求如下：
 *      AA打印5次，BB打印10次，CC打印15次
 *      来十轮这样的输出
 */
public class ReentrantLockAndConditionDemo {
    public static void main(String[] args) {
        ShareData shareData = new ShareData();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareData.AA();
            }
        }, "AA").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareData.BB();
            }
        }, "BB").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareData.CC();
            }
        }, "CC").start();
    }
}

class ShareData{
    private int i = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public void AA(){
        lock.lock();
        try{
            while (i != 0){
                condition1.await();
            }
            for (int j = 0; j < 5; j++) {
                System.out.println("AA");
            }
            i = 1;
            condition2.signal();
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            lock.unlock();
        }
    }

    public void BB(){
        lock.lock();
        try{
            while (i != 1){
                condition2.await();
            }
            for (int j = 0; j < 10; j++) {
                System.out.println("BB");
            }
            i = 2;
            condition3.signal();
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            lock.unlock();
        }
    }

    public void CC(){
        lock.lock();
        try{
            while (i != 2){
                condition3.await();
            }
            for (int j = 0; j < 15; j++) {
                System.out.println("CC");
            }
            i = 0;
            condition1.signal();
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            lock.unlock();
        }
    }
}
