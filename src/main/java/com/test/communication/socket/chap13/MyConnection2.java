package com.test.communication.socket.chap13;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MyConnection2 {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("127.0.0.1",80);
			//发送数据
			OutputStream ops = socket.getOutputStream();
			OutputStreamWriter opsw = new OutputStreamWriter(ops);
			BufferedWriter bw = new BufferedWriter(opsw);
			bw.write("hello world\r\n\r\n");
			bw.flush();
			//接收数据
			InputStream ips = socket.getInputStream();
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String s="";
			while((s=br.readLine())!=null){
				System.out.println(s);
			}
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
