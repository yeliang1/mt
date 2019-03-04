package com.test.communication.socket.chap27;

import java.net.Socket;

public class Client {
	public static void main(String[] args) throws Exception {
		Socket socket = new Socket("127.0.0.1", 1234);
		Thread.sleep(1000);
		// socket.getOutputStream().write(1);
		System.out.println("read() = " + socket.getInputStream().read());
		System.out.println("isConnected() = " + socket.isConnected());
		System.out.println("isClosed() = " + socket.isClosed());
	}
}
