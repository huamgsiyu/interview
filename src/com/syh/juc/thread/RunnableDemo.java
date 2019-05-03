package com.syh.juc.thread;

/**
 * @author hsy
 * @createtime 2019/5/1 13:16
 */

/**
 * 第二种获得多线程方法
 */
public class RunnableDemo implements Runnable{
    @Override
    public void run() {
        System.out.println("调用Runnable接口");
    }
}

class TestRunnable{
    public static void main(String[] args) {
        RunnableDemo runnableDemo = new RunnableDemo();
        new Thread(runnableDemo).start();
    }
}