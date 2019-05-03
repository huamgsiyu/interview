package com.syh.juc.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
*/
public class TestAtomicDemo {
	
	public static void main(String[] args){		
		AtomicDemo ad = new AtomicDemo();
		
		for(int i = 0; i < 10; i++){
			new Thread(ad).start();
		}
	}
}

class AtomicDemo implements Runnable{

	
//	private int serialNumber = 0;
	
	private AtomicInteger serialNumber = new AtomicInteger();
	@Override
	public void run() {
		try {
			;
			System.out.println(getSerialNumber());
			Thread.sleep(200);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
//	public int getSerialNumber(){
//		return serialNumber++;
//	}
	public int getSerialNumber(){
		return serialNumber.getAndIncrement();
	}
}
