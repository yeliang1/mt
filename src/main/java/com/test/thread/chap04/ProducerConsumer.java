package com.test.thread.chap04;

public class ProducerConsumer {
	public static void main(String[] args) {
		final ProducerConsumer pc = new ProducerConsumer();
		
		for(int i=0;i<5;i++){
			new Thread() {   
			    public void run() {   
			    	while(true){
			    		try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
			    		pc.produce();
			    	}
			    }   
			}.start();  
		}
		for(int i=0;i<5;i++){
			new Thread() {   
			    public void run() {
			    	while(true){
			    		try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
			    		pc.consume();
			    	}
			    }   
			}.start();  
		}
	}
	
	private boolean flg = true;
	public synchronized void produce(){
		while(!flg){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("produce");
		flg = false;
		this.notify();
	}
	public synchronized void consume(){
		while(flg){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("consume");
		flg = true;
		this.notify();
	}
}
