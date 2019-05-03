package com.syh.juc.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
*/
public class TestLockOrdering {

	public static void main(String[] args) {
		Loop loop = new Loop();
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {					
					loop.LoopA();
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {					
					loop.LoopB();
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					loop.LoopC();
				}
			}
		}).start();
	}
}
class Loop{
	
	private int sign = 1;

	private Lock lock = new ReentrantLock();
	
	private Condition condition1 = lock.newCondition();
	
	private Condition condition2 = lock.newCondition();
	
	private Condition condition3 = lock.newCondition();
	
	public void LoopA(){
		lock.lock();
		try{
			if(sign != 1){
					condition1.await();
			}
			for (int i = 1; i <= 1; i++) {
				System.out.println("A");
			}
			sign = 2;
			condition2.signal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
	
	public void LoopB(){
		lock.lock();
		try{
			if(sign != 2){
				condition2.await();
			}
			for (int i = 1; i <= 1; i++) {
				System.out.println("B");
			}
			sign = 3;
			condition3.signal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
	
	public void LoopC(){
		lock.lock();
		try{
			if(sign != 3){
				condition3.await();
			}
			for (int i = 1; i <= 1; i++) {
				System.out.println("C");
			}
			sign = 1;
			condition1.signal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
}
