package com.test.communication.socket.chap28;

import java.io.IOException;
import java.net.ServerSocket;

public class RandomPort {
	public static void main(String[] args) throws IOException {
		for(int i=0;i<5;i++){
			System.out.print("Random Port" + i + "：");
			System.out.println(new ServerSocket(0).getLocalPort());
		}
	}
}
