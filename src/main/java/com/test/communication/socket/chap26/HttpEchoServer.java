package com.test.communication.socket.chap26;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpEchoServer extends Thread {
	private Socket socket;

	public HttpEchoServer(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(socket.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
			osw.write("HTTP/1.1 200 OK\r\n\r\n");
			String s = "";
			while (!(s = br.readLine()).equals(""))
				osw.write("<html><body>" + s + "<br></body></html>");
			osw.flush();
			socket.close();
		} catch (Exception e) {
		}
	}
	
	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket = new ServerSocket(8888);
		System.out.println("服务器已经启动，端口：8888");
		while (true) {
			Socket socket = serverSocket.accept();
			new HttpEchoServer(socket).start();
		}
	}
}
