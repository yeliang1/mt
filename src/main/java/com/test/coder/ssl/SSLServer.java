package com.test.coder.ssl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

public class SSLServer {
	private SSLServerSocket serverSocket;
	private int PORT = 8443;
	private String workPath = SSLServer.class.getClass().getResource("/").getPath()+"com/test/coder/ssl/";
	private String serverKeyStorePath = workPath+"server.keystore";
	private String SERVER_KEY_STORE_PASSWORD = "123456";
	private String serverTrustKeyStorePath = workPath+"tserver.keystore";
	private String SERVER_TRUST_KEY_STORE_PASSWORD = "123456";

	public SSLServer() {
		try {
			KeyStore ks = KeyStore.getInstance("JKS"); // 创建JKS密钥库
			ks.load(new FileInputStream(serverKeyStorePath), SERVER_KEY_STORE_PASSWORD.toCharArray());
			// 创建管理JKS密钥库的X.509密钥管理器
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(ks, SERVER_KEY_STORE_PASSWORD.toCharArray());
			
			KeyStore tks = KeyStore.getInstance("JKS");
			tks.load(new FileInputStream(serverTrustKeyStorePath), SERVER_TRUST_KEY_STORE_PASSWORD.toCharArray());
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
			tmf.init(tks);

			SSLContext sslContext = SSLContext.getInstance("SSLv3");
			sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

			// 根据上面配置的SSL上下文来产生SSLServerSocketFactory,与通常的产生方法不同
			SSLServerSocketFactory factory = sslContext.getServerSocketFactory();
			serverSocket = (SSLServerSocket) factory.createServerSocket(PORT);
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void start(){
		while(true){
			try {
				SSLSocket socket = (SSLSocket)serverSocket.accept();
				InputStream in =socket.getInputStream();
				OutputStream out = socket.getOutputStream();
				
				byte[] b = new byte[5];
				in.read(b);
				System.out.println(new String(b));
				
				in.close();
				out.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		SSLServer sslServer = new SSLServer();
		sslServer.start();
	}
}
