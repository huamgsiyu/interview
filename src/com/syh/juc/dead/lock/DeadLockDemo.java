package com.syh.juc.dead.lock;

/**
 * @author hsy
 * @createtime 2019/5/3 11:16
 */

import java.util.concurrent.TimeUnit;

/**
 * 死锁Demo
 */
public class DeadLockDemo {
    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";

        new Thread(new HoldLockThread(lockA, lockB),"threadAAA").start();
        new Thread(new HoldLockThread(lockB, lockA),"threadBBB").start();

        /**
         * linux ps -ef|grep xxx    ls -l
         * window下的java运行程序     也有类似ps的查看进程的命令，但是目前我们需要查看的只是java
         *      jps = java ps       jps -l
         */
    }
}

class HoldLockThread implements Runnable{
    private String lockA;
    private String lockB;

    public HoldLockThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {

        synchronized (lockA){
            System.out.println(Thread.currentThread().getName() +
                "\t 自己持有：" + lockA + ",尝试获取：" + lockB);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (lockB){
                System.out.println(Thread.currentThread().getName() +
                        "\t 自己持有：" + lockB + ",尝试获取：" + lockA);
            }
        }
    }
    public String getLockA() {
        return lockA;
    }

    public void setLockA(String lockA) {
        this.lockA = lockA;
    }

    public String getLockB() {
        return lockB;
    }

    public void setLockB(String lockB) {
        this.lockB = lockB;
    }
}