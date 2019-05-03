package com.syh.juc.queue;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author hsy
 * @createtime 2019/4/28 16:04
 */
public class SynchronousQueueDemo {
    public static void main(String[] args) throws Exception{
        BlockingQueue<String> blockingDeque = new SynchronousQueue<>();
        
        new Thread(() -> {
            try {
                System.out.println("生产了一个，值为1");
                blockingDeque.put("1");
                System.out.println("生产了一个，值为2");
                blockingDeque.put("2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();
        new Thread(() -> {
            try {
                try {TimeUnit.MILLISECONDS.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }
                blockingDeque.take();
                System.out.println("取出了一个，值为1");

                try {TimeUnit.MILLISECONDS.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }
                blockingDeque.take();
                System.out.println("取出了一个，值为2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }
}
