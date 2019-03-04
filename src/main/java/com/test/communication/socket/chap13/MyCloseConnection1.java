package com.test.communication.socket.chap13;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MyCloseConnection1 {
	public static void printState(Socket socket){
		System.out.println("isInputShutdown:"+socket.isInputShutdown());
		System.out.println("isOutputShutdown:"+socket.isOutputShutdown());
		System.out.println("isClosed:"+socket.isClosed());
		System.out.println();
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket = new Socket("127.0.0.1",80);
		printState(socket);
		
		socket.shutdownInput();
		printState(socket);
		
		socket.shutdownOutput();
		printState(socket);
	}
}
