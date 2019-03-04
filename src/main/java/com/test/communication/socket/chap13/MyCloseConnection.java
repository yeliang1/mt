package com.test.communication.socket.chap13;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MyCloseConnection {
	public static void printState(Socket socket,String name){
		System.out.println(name+".isClosed():"+socket.isClosed());
		System.out.println(name+".isConnected():"+socket.isConnected());
		if(socket.isClosed()==false&&socket.isConnected()==true){
			System.out.println(name+"处于连接状态");
		}else{
			System.out.println(name+"处于非连接状态");
		}
		System.out.println();
	}
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket1=null,socket2=null;
		
		socket1 = new Socket("127.0.0.1",80);
		printState(socket1,"Socket1");
		
		socket1.getOutputStream().close();
		printState(socket1,"Socket1");
		
		socket2 = new Socket();
		printState(socket2,"Socket2");
		
		socket2.close();
		printState(socket2,"Socket2");
	}
}
