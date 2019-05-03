package com.syh.juc.volatiles;
/**
* 一、volatile关键字：当多个线程操作共享数据时，可以保证内存中的数据是可见的
*
*	flag共享变量有volatile修饰和没有的区别：
*
*	synchronized和valatile的区别：
*		1、volatile 不具备“互斥性”
*		2、volatile 不能够保证变量的“原子性”
*/
public class VolatileDemo {
	
	public static void main(String[] args){		
		ThreadDemo td = new ThreadDemo();
		new Thread(td).start();
		
		while(true){
			if(td.isFlag()){
				System.out.println("------------");
				break;
			}
		}
	}
	
}

class ThreadDemo implements Runnable {

	//1、有volatile，共享变量flag，在内存中数据可见
//	private volatile boolean flag = false;
	
	//2、没有volatile，共享变量flag，在内存中数据不可见
	private boolean flag = false;
	
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	@Override
	public void run() {
		try {
			Thread.sleep(2000);
			flag = true;
			System.out.println("Flag: " + flag);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
}
