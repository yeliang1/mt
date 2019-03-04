package com.test.communication.socket.chap13;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MyConnection {
	public static void main(String[] args) {
		try {
			String ip = "127.0.0.1";
			Socket socket = new Socket(ip,80);
			System.out.println("连接成功"+ip);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
