package com.syh.juc.queue;

/**
 * @author hsy
 * @createtime 2019/4/29 7:51
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 队列版--生产者消费者
 *
 *
 * 题目：一个初始值为0的变量，两个线程对其交替操作，一个加1一个减1，来5轮
 */


public class ProdConsumer_QueueDemo {
    public static void main(String[] args) throws Exception {
        MyResource myResource = new MyResource(new ArrayBlockingQueue(1), 5L);
        for (int i = 0; i < 1; i++) {

            new Thread(() -> {
                try {
                    myResource.prod();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },String.valueOf("aa:" + i)).start();
        }
        for (int i = 0; i < 1; i++) {

            new Thread(() -> {
                try {
                    myResource.consumer();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },String.valueOf("bb:" + i)).start();
        }
        TimeUnit.MILLISECONDS.sleep(15000);
        myResource.stop();
    }

}

class MyResource<T>{
    private volatile boolean FLAG = true;
    private BlockingQueue blockingQueue = null;
    private AtomicInteger atomicInteger = new AtomicInteger();
    private static long timeout = 0L;


    public MyResource(BlockingQueue blockingQueue, Long timeout){
        this.blockingQueue = blockingQueue;
        this.timeout = timeout;
        System.out.println("BlockingQueue：" + blockingQueue.getClass().getName());

    }

    public void prod() throws Exception{
        String value = null;
        while (FLAG){
            System.out.println();
            value = String.valueOf(atomicInteger.incrementAndGet());
            boolean insertRs = blockingQueue.offer(value, timeout, TimeUnit.SECONDS);
            if(insertRs){
                System.out.println(Thread.currentThread().getName() + "\t 插入" + value + "成功");
            }else{
                System.out.println(Thread.currentThread().getName() + "\t 插入" + value + "失败");
            }
            TimeUnit.MILLISECONDS.sleep(1000);
        }
    }

    public void consumer() throws Exception{
        while (FLAG){
            System.out.println();
            String pollValue = (String) blockingQueue.poll(timeout, TimeUnit.SECONDS);
            if(pollValue == null || pollValue.equals("")){
                System.out.println(Thread.currentThread().getName() + "\t 取出失败，值为" + pollValue);
                FLAG = false;
                return;
            }else{
                System.out.println(Thread.currentThread().getName() + "\t 取出成功，值为" + pollValue);
            }
            TimeUnit.MILLISECONDS.sleep(1000);
        }
    }

    public void stop(){
        this.FLAG = false;
    }
}
