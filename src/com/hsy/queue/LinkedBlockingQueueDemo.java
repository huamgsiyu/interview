package com.hsy.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author hsy
 * @createtime 2019/4/28 15:58
 */

/**
 * 核心方法：
 *  3.1 抛出异常组：add(e)/remove()/element()
 *  3.2 特殊值：offer(e)/poll()/peek()
 *  3.3 阻塞：put(e)/take()
 *  3.4 offer(e,time,unit)/poll(time,unit)
 */

public class LinkedBlockingQueueDemo {
    private static LinkedBlockingQueue blockingQueue = null;

    public static void main(String[] args) throws Exception{
        addRemoveElement();
        offerPollPeek();
        putTake();
        offerPollPeekHasTime();
    }

    public static void offerPollPeekHasTime() throws Exception{
        System.out.println("offerPollPeekHasTime()--开始");
        blockingQueue = new LinkedBlockingQueue<>(3);

        blockingQueue.offer("1111",1000, TimeUnit.MILLISECONDS);
        blockingQueue.offer("1112",1000, TimeUnit.MILLISECONDS);
        blockingQueue.offer("1113",1000, TimeUnit.MILLISECONDS);

        //如果队列满后，等待一秒后，还是放不进去，返回false
        blockingQueue.offer("1114",1000, TimeUnit.MILLISECONDS);

        System.out.println(blockingQueue.poll(1000, TimeUnit.MILLISECONDS));
        System.out.println(blockingQueue.poll(1000, TimeUnit.MILLISECONDS));
        System.out.println(blockingQueue.poll(1000, TimeUnit.MILLISECONDS));

        //如果队列为空，等待一秒后，还是为空，返回null
        System.out.println(blockingQueue.poll(1000, TimeUnit.MILLISECONDS));

        System.out.println("offerPollPeekHasTime()--结束");
        System.out.println();
    }

    public static void putTake() throws Exception{
        System.out.println("putTake()--开始");
        blockingQueue = new LinkedBlockingQueue<>(3);
        blockingQueue.put("111");
        blockingQueue.put("112");
        blockingQueue.put("113");
        //如果队列已满，put将会一直阻塞
//        blockingQueue.put("114");

        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());

        //如果队列为空，take将会一直阻塞
//        System.out.println(blockingQueue.take());

        System.out.println("putTake()--结束");
        System.out.println();
    }

    public static void offerPollPeek(){
        System.out.println("offerPollPeek()--开始");
        blockingQueue = new LinkedBlockingQueue<>(3);

        blockingQueue.offer("11");
        blockingQueue.offer("12");
        blockingQueue.offer("13");

        System.out.println("查看队列的头元素：" + blockingQueue.peek());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());

        System.out.println("offerPollPeek()--结束");
        System.out.println();
    }

    public static void addRemoveElement(){
        System.out.println("addRemoveElement()--开始");
        blockingQueue = new LinkedBlockingQueue<>(3);
        blockingQueue.add("1");
        blockingQueue.add("2");
        blockingQueue.add("3");

        System.out.println("查看队列头的元素：" + blockingQueue.element());

        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println("addRemoveElement()--结束");
        System.out.println();
    }
}
