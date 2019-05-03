package com.syh.juc.thread.pool;

/**
 * @author hsy
 * @createtime 2019/5/1 20:58
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 第四种使用多线程的方式--线程池
 */

/**
 * 问题：模拟10个用户办理业务，每个业务就是一个来自外部的请求线程
 */
public class ThreadPoolExecutorDemo {
    public static void main(String[] args) {
        newFixedThreadPool();
        newSingleThreadExecutor();
        newCachedThreadPool();
    }


    /**
     * 3.使用newCachedThreadPool()方式
     */
    public static void newCachedThreadPool(){
        ExecutorService threadPool = Executors.newCachedThreadPool();
        try{
            for (int i = 0; i < 10; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() +
                            "使用newCachedThreadPool()方式：\t 办理业务");
                });
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally{
            threadPool.shutdown();
        }
    }

    /**
     * 2.使用newSingleThreadExecutor()方法
     */
    public static void newSingleThreadExecutor(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        try{
            for (int i = 0; i < 10; i++) {
                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName() +
                            "newSingleThreadExecutor()方式：\t 办理业务");
                });
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally{
            executorService.shutdown();
        }
    }

    /**
     *  1.使用newFixedThreadPool()方法
     */
    public static void newFixedThreadPool(){
        ExecutorService threadPool = Executors.newFixedThreadPool(5);

        try{
            for (int i = 0; i < 10; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() +
                            "newFixedThreadPool()方式：\t 办理业务");
                });
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally{
            threadPool.shutdown();
        }
    }
}
