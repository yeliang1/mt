package com.test.thread.chap03;

public class TraditionalThreadSynchronized {
	public static void main(String[] args) {
		TraditionalThreadSynchronized th = new TraditionalThreadSynchronized();
		th.init();
	}
	
	class Outputer{
		public void output(String name){
			int len = name.length();
			synchronized (this) {
				for(int i=0;i<len;i++){
					System.out.print(name.charAt(i));
				}
			}
			System.out.println();
		}
		
		public synchronized void output2(String name){
			int len = name.length();
			for(int i=0;i<len;i++){
				System.out.print(name.charAt(i));
			}
			System.out.println();
		}
	}
	
	private void init(){
		final Outputer out = new Outputer();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					out.output("amiee");
				}
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					out.output("zhronger");
				}
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					out.output("yeliang");
				}
			}
		}).start();
	}
}
