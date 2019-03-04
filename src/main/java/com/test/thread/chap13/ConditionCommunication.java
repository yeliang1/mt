package com.test.thread.chap13;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionCommunication {
	public static void main(String[] args) {
		final Business business = new Business();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i=0;i<=50;i++){
					business.sub(i);
				}
			}
		}).start();
		
		for(int i=0;i<=50;i++){
			business.main(i);
		}
	}
	
}

class Business {
	private boolean bShouldSub = true;
	Lock lock = new ReentrantLock();
	Condition condition = lock.newCondition();
	
	public void sub(int i){
		lock.lock();
		try {
			while(!bShouldSub){
				condition.await();
			}
			for(int j=1;j<=10;j++){
				System.out.println("sub thread sequece of "+j+",loop of "+i);
			}
			bShouldSub = false;
			condition.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public void main(int i){
		lock.lock();
		try {
			while(bShouldSub){
				condition.await();
			}
			for(int j=1;j<=5;j++){
				System.out.println("main thread sequece of "+j+",loop of "+i);
			}
			bShouldSub = true;
			condition.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
}
