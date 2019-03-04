package com.test.communication.socket.chap16;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class SocketTimeout {
	public static void main(String[] args) {
		long time1 = 0, time2 = 0;
		Socket socket = new Socket();

		try {
			time1 = System.currentTimeMillis();
			socket.connect(new InetSocketAddress("127.0.0.1", 80), 3000);// 设置连接超时
			socket.setSoTimeout(5000);// 设置读取数据超时
			time1 = System.currentTimeMillis();// 重置一次
			socket.getInputStream().read();
		} catch (SocketTimeoutException e) {
			if (!socket.isClosed() && socket.isConnected())
				System.out.println("读取数据超时!");
			else
				System.out.println("连接超时");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			time2 = System.currentTimeMillis();
			System.out.println(time2 - time1);
		}
	}
}
