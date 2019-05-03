package com.syh.juc.lock;
/**
 * 以下程序存在虚假唤醒问题，该怎么解决？
*/
public class TestConsumerAndProductor {
	public static void main(String[] args) {
		Clerk clerk = new Clerk();
		
		Productor productor = new Productor(clerk);
		Consumer consumer = new Consumer(clerk);
		
		new Thread(productor, "生产者1：").start();
		new Thread(consumer, "消费者2：").start();
		new Thread(productor, "生产者3：").start();
		new Thread(consumer, "消费者4：").start();
	}
}

//店员
class Clerk{
	private int product = 0;
	
	public synchronized void get(){
		if(product >= 20){
			try {
				System.out.println("货物已满，不需要进货");
				this.wait();
			} catch (InterruptedException e) {
			}
		}else{
			System.out.println(Thread.currentThread().getName() + "剩下产品:" + ++product);
			this.notifyAll();
		}
	}
	
	public synchronized void sale(){
		if(product <= 0){
			System.out.println("产品数量为0，需要进货");
			try {
				this.wait();
			} catch (InterruptedException e) {
			}
		}else{
			System.out.println(Thread.currentThread().getName() + "消费后产品数量为：" + --product);
			this.notifyAll();
		}
	}
}

//生产者
class Productor implements Runnable{
	private Clerk clerk;
	
	public Productor(Clerk clerk){
		this.clerk = clerk;
	}

	@Override
	public void run() {
		for (int i = 0; i < 50; i++) {			
			clerk.get();
		}
	}
}

//消费者
class Consumer implements Runnable{
	private Clerk clerk;

	public Consumer(Clerk clerk){
		this.clerk = clerk;
	}
	@Override
	public void run() {
		for (int i = 0; i < 50; i++) {			
			clerk.sale();
		}
	}
	
}