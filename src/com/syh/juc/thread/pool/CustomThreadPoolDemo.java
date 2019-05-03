package com.syh.juc.thread.pool;

import java.util.concurrent.*;

/**
 * @author hsy
 * @createtime 2019/5/2 22:06
 */

/**
 * 手写线程池--以及四种线程池拒绝策略
 */
public class CustomThreadPoolDemo {
    public static void main(String[] args) {
        ExecutorService threadPool = new ThreadPoolExecutor(2,
                5,
                10L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                //四种拒绝策略
                new ThreadPoolExecutor.DiscardPolicy());

        try{
            for (int i = 0; i < 20; i++) {
                final int index = i;
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() +
                        "\t 办理业务" + index);
                });
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally{
            threadPool.shutdown();
        }
    }
}
