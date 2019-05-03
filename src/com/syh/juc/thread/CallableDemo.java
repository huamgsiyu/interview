package com.syh.juc.thread;

import java.util.concurrent.*;

/**
 * @author hsy
 * @createtime 2019/5/1 13:17
 */

/**
 * 第三种获得多线程的方式
 */
public class CallableDemo implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {

        System.out.println(Thread.currentThread().getName() + "调用Callable");
        try {
            TimeUnit.MILLISECONDS.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
        return 9999;
    }
}

class TestCallable{
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(new CallableDemo());

        for (int i = 0; i < 10; i++) {
            new Thread(futureTask, String.valueOf(i)).start();
        }
        //以上证明同一个futureTask，只会进去一个线程，执行一次方法

        //1、第一种方法，确认返回结果
//        System.out.println(futureTask.get());
//        System.out.println("证明FutureTask.get()方法是会阻塞main线程，" +
//                "直到call()方法结束。");
//        System.out.println("因为FutureTask.get()方法会阻塞，所以最好" +
//                "放到可以放的最后位置（放到需要用到该结果的代码前），以免阻塞其他线程");


        //2、第二种方法，确认返回结果，使用isDone()方法，判断call()方法执行完成与否
        while (!futureTask.isDone());
        System.out.println(futureTask.get());
        System.out.println("证明FutureTask.get()方法是会阻塞main线程，" +
                "直到call()方法结束。");
        System.out.println("因为FutureTask.get()方法会阻塞，所以最好" +
                "放到可以放的最后位置（放到需要用到该结果的代码前），以免阻塞其他线程");
    }
}
