package com.hsy.lock;

/**
 * @author hsy
 * @createtime 2019/4/25 21:16
 */

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 1、独占锁：指该锁一次只能被一个线程所持有。对于ReentrantLock和Sychronized而言都是独占锁
 *
 * 2、共享锁：指该锁能够被多个线程所持有
 *
 * 3、ReentrantReadWriteLock：
 *  3.1 它的读锁是共享锁，写锁是独占锁
 *  3.2 读锁的共享锁可以保证并发读是高效率的，读写、写读、写写这三个过程是互斥的
 */
public class ReentrantReadWriteLockDemo {

//    Lock lock = new ReentrantReadWriteLock();
    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        for (int i = 0; i < 5; i++) {
            final int ivalue = i;
            new Thread(() -> {
                myCache.put(ivalue + "", ivalue);
            },String.valueOf(i)).start();
        }

        for (int i = 0; i < 5; i++) {
            final int ivalue = i;
            new Thread(() -> {
                myCache.get(ivalue + "");
            },String.valueOf(i)).start();
        }
    }
}

/*
 * 手写一个缓存，分布式缓存三步走：读，写，清缓存
 *  在分布式缓存中，读写同时存在
 * 1.不加锁，出现了写操作不是原子性的问题
 * 2.使用ReentrantLock()，可以解决写操作被打断的问题，但是读和写操作都加锁，效率有些低
 * 3.使用ReentrantReadWriteLock()，可以进行读读操作，但是不能同时进行读写，写读，写写操作，效率相对以上两种高效
 *
 */
class MyCache{
    private volatile Map<String, Object> map = new HashMap<>();
//    private volatile Lock lock = new ReentrantLock();
    private volatile ReentrantReadWriteLock rrwl = new ReentrantReadWriteLock();

    public void put(String key, Object value){
//        lock.lock();
        rrwl.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t 正在写入："+key);
            try {TimeUnit.MILLISECONDS.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
            map.put(key, value);
            System.out.println(Thread.currentThread().getName()+"\t 写入完成");
        } finally {
//            lock.unlock();
            rrwl.writeLock().unlock();
        }
    }

    public void get(String key){
//        lock.lock();
        rrwl.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t 正在读取：");
            try {TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }
            Object value = map.get(key);
            System.out.println(Thread.currentThread().getName()+"\t 读取完成"+value);
        } finally {
//            lock.unlock();
            rrwl.readLock().unlock();
        }
    }

    public void cleanMap(Map<String, Object> map){
        map.clear();
    }
}
