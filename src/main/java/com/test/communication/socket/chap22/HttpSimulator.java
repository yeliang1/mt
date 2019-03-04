package com.test.communication.socket.chap22;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class HttpSimulator {
	private Socket socket;
	private int port = 80;
	private String host = "127.0.0.1";
	private String request = "";
	private boolean isPort, isHead;

	public void run() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			if (!readHostAndPort(br))
				break;
			readHttpRequest(br);
			sendHttpRequest();
			readHttpResponse(br);
		}
	}

	private boolean readHostAndPort(BufferedReader consoleReader)
			throws Exception {
		System.out.println("host:prot>");
		String[] ss = null;
		String s = consoleReader.readLine();
		if (s.equals("q"))
			return false;
		else {
			ss = s.split("[:]");
			if (!ss[0].equals(""))
				host = ss[0];
			if (ss.length > 1)
				port = Integer.parseInt(ss[1]);
			System.out.println(host + ":" + port);
			return true;
		}
	}

	private void readHttpRequest(BufferedReader consoleReader) throws Exception {
		System.out.println("请输入HTTP请求：");
		String s = consoleReader.readLine();
		request = s + "\r\n";
		boolean isPost = s.substring(0, 4).equals("POST");
		boolean isHead = s.substring(0, 4).equals("HEAD");
		while (!(s = consoleReader.readLine()).equals(""))
			request = request + s + "\r\n";
		request = request + "\r\n";
		if (isPost) {
			System.out.println("请输入POST方法的内容：");
			s = consoleReader.readLine();
			request = request + s;
		}
	}

	private void sendHttpRequest() throws Exception {
		socket = new Socket();
		socket.setSoTimeout(10 * 1000);
		System.out.println("正在连接服务器");
		socket.connect(new InetSocketAddress(host, port), 10 * 1000);
		System.out.println("服务器连接成功！");
		OutputStream out = socket.getOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(out);
		writer.write(request);
		writer.flush();
	}

	private void readHttpResponse(BufferedReader consoleReader) {
		String s = "";
		try {
			InputStream in = socket.getInputStream();
			InputStreamReader inReader = new InputStreamReader(in);
			BufferedReader socketReader = new BufferedReader(inReader);
			System.out.println("---------HTTP头---------");
			boolean b = true; // true: 未读取消息头 false: 已经读取消息头
			while ((s = socketReader.readLine()) != null) {
				if (s.equals("") && b == true && !isHead) {
					System.out.println("------------------------");
					b = false;
					System.out.print("是否显示HTTP的内容(Y/N):");
					String choice = consoleReader.readLine();
					if (choice.equals("Y") || choice.equals("y")) {
						System.out.println("---------HTTP内容---------");
						continue;
					} else
						break;
				} else
					System.out.println(s);
			}
		} catch (Exception e) {
			System.out.println("err:" + e.getMessage());
		} finally {
			try {
				socket.close();
			} catch (Exception e) {
			}
		}
		System.out.println("------------------------");
	}

	public static void main(String[] args) {
		try {
			new HttpSimulator().run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
