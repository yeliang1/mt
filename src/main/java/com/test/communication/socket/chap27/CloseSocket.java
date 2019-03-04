package com.test.communication.socket.chap27;

import java.net.ServerSocket;
import java.net.Socket;

public class CloseSocket {
	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket = new ServerSocket(1234);
		while (true) {
			Socket socket = serverSocket.accept();
			socket.close();
		}
	}
}
