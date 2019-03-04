﻿package com.test.communication.socket.chap15;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class MySocketAddress {
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket1 = new Socket("127.0.0.1",80);
		SocketAddress socketAddress = socket1.getRemoteSocketAddress();
		socket1.close();
		
		Socket socket2 = new Socket();
		socket2.connect(socketAddress);
		socket2.close();
		
		InetSocketAddress inetSocketAddress1 = (InetSocketAddress)socketAddress;
		System.out.println("服务器域名:"+inetSocketAddress1.getAddress().getHostName());
		System.out.println("服务器IP:"+inetSocketAddress1.getAddress().getHostAddress());
		System.out.println("服务器Port:"+inetSocketAddress1.getPort());
		InetSocketAddress inetSocketAddress2 = (InetSocketAddress)socket2.getLocalSocketAddress();
		System.out.println("本地IP:"+inetSocketAddress2.getAddress().getLocalHost().getHostAddress());
		System.out.println("本地Port:"+inetSocketAddress2.getPort());
	}
}
