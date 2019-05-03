package com.syh.juc.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock + Condition解决生产者消费者问题，还有虚假唤醒问题
*/
public class TestLockCondition {
	
	public static void main(String[] args) {
		ClerkCondition clerk = new ClerkCondition();
		
		ProductorCondition productor = new ProductorCondition(clerk);
		ConsumerCondition consumer = new ConsumerCondition(clerk);
		
		new Thread(productor, "生产者1").start();
		new Thread(consumer, "消费者2").start();

		new Thread(productor, "生产者3").start();
		new Thread(consumer, "消费者4").start();
	}
}

//店员
class ClerkCondition{
	private int product = 0;
	
	private Lock lock = new ReentrantLock();
	
	private Condition condition =  lock.newCondition();
	
	//进货
	public void get(){
		try {
			lock.lock();
			while(product >= 2){
				Thread.sleep(500);
				System.out.println("货物已满");
				condition.await();
			}
			condition.signalAll();
			System.out.println(Thread.currentThread().getName() + 
					"生产商品:" + ++product);
		} catch (InterruptedException e) {
		} finally{
			lock.unlock();
		}
		
	}
	//卖货
	public void sale(){
		try{
			lock.lock();
			while(product <= 0){
				Thread.sleep(500);
				condition.await();
				System.out.println("需要进货");
			}
			condition.signalAll();
			System.out.println(Thread.currentThread().getName() + 
					"购买商品后剩余:" + --product);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
}
//生产者
class ProductorCondition implements Runnable{
	private ClerkCondition clerk;
	
	public ProductorCondition(ClerkCondition clerk){
		this.clerk = clerk;
	}

	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			clerk.get();
		}
	}
}

//消费者
class ConsumerCondition implements Runnable{
	private ClerkCondition clerk;
	
	public ConsumerCondition(ClerkCondition clerk){
		this.clerk = clerk;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			clerk.sale();
		}
		
	}
	
}