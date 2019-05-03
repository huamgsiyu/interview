package com.syh.juc.thread;

/**
 * @author hsy
 * @createtime 2019/5/1 21:02
 */
public class ThreadDemo extends Thread {
    @Override
    public void run(){
        System.out.println("第一种实现多线程的方式");
    }
}

class TestThread{
    public static void main(String[] args) {
        ThreadDemo threadDemo = new ThreadDemo();
        threadDemo.start();
    }
}
