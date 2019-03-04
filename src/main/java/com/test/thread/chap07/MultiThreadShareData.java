package com.test.thread.chap07;

public class MultiThreadShareData {
	public static void main(String[] args) {
		final ShareData1 data1 = new ShareData1();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				data1.decrement();
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				data1.increment();
			}
		}).start();
	}
}

class MyRunnable implements Runnable{
	private ShareData1 data1;
	public MyRunnable(ShareData1 data1) {
		this.data1 = data1;
	}
	@Override
	public void run() {
		data1.decrement();
	}
}

class ShareData1 {
	
	int j = 0;
	public synchronized void increment(){
		j++;
	}
	public synchronized void decrement(){
		j--;
	}
}
