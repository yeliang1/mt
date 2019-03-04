package com.test.communication.socket.chap13;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MyConnection1 extends Thread{
	private int minPort,maxPort;

	public MyConnection1(int minPort, int maxPort) {
		this.minPort = minPort;
		this.maxPort = maxPort;
	}
	
	public void run(){
		for(int i=minPort;i<=maxPort;i++){
			try {
				Socket socket = new Socket("127.0.0.1",i);
				System.out.println("port:"+i+" OK!");
				socket.close();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		int minPort=1,maxPort=80;
		int threadCount = 1;
		//模拟线程
		int portIncrement=((maxPort-minPort+1)/threadCount)+(((maxPort-minPort+1)%threadCount)==0?0:1);
		MyConnection1[] instances = new MyConnection1[threadCount];
		for(int i=0;i<threadCount;i++){
			instances[i]=new MyConnection1(minPort+portIncrement*i,minPort+portIncrement-1+portIncrement*i);
			instances[i].start();
		}
	}
}
