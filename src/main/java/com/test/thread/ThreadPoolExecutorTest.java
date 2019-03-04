package com.test.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTest {
	public static void main(String[] args) {
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 4, 3,
				TimeUnit.SECONDS, new SynchronousQueue<Runnable>(),
				new ThreadPoolExecutor.DiscardOldestPolicy());
		
		for(int i=0;i<100;i++){
			final int temp = i;
			threadPool.execute(new Runnable() {
				public void run() {
					System.out.println(temp);
				}
			});
		}
	}
}
