package com.test.thread.chap09;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {
	public static void main(String[] args) {
		//ExecutorService threadPool = Executors.newFixedThreadPool(3);
		//ExecutorService threadPool = Executors.newCachedThreadPool();
		ExecutorService threadPool = Executors.newSingleThreadExecutor();
		for(int j=1;j<=10;j++){
			final int task = j;
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					for(int i=1;i<=10;i++){
						try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println(Thread.currentThread().getName()+" is looping of "+i
								+" for task of "+task);
					}
				}
			});
		}
		System.out.println("all of 10 tasks has completed");
		threadPool.shutdown();
		
		Executors.newScheduledThreadPool(3).schedule(new Runnable() {
			@Override
			public void run() {
				System.out.println("bombing");
			}
		}, 10, TimeUnit.SECONDS);
	}
}
