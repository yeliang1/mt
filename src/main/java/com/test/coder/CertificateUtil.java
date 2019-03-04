package com.test.coder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;


public class CertificateUtil {
	/**
	 * 从密钥库得到私钥
	 * @param keyStorePath
	 * @param alias
	 * @param password
	 * @return
	 */
	public static PrivateKey getPrivateKeyByKeystore(String keyStorePath, String alias, String password){
		try {
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
			FileInputStream is = new FileInputStream(keyStorePath);
			ks.load(is, password.toCharArray());
			is.close();
			
			return (PrivateKey)ks.getKey(alias, password.toCharArray());
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 从证书得到公钥
	 * @param certificatePath
	 * @return
	 */
	public static PublicKey getPublicKeyByCertificate(String certificatePath){
		try {
			CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
			FileInputStream is = new FileInputStream(certificatePath);
			Certificate certificate = certificateFactory.generateCertificate(is);
			is.close();
			
			return certificate.getPublicKey();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 私钥加解密
	 * @param data
	 * @param privateKey
	 * @param mode
	 *            0-加密，1-解密
	 * @return
	 */
	public static String codeByPrivateKey(String data, PrivateKey privateKey, int mode){
		try {
			Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
			if (mode == 0) {
				cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			} else {
				cipher.init(Cipher.DECRYPT_MODE, privateKey);
			}
			return Hex.encodeHexString(cipher.doFinal(Hex.decodeHex(data
					.toCharArray())));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (DecoderException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	/**
	 * 公钥加解密
	 * @param data
	 * @param publicKey
	 * @param mode
	 *            0-加密，1-解密
	 * @return
	 */
	public static String codeByPublicKey(String data, PublicKey publicKey, int mode){
		try {
			Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
			if (mode == 0) {
				cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			} else {
				cipher.init(Cipher.DECRYPT_MODE, publicKey);
			}
			return Hex.encodeHexString(cipher.doFinal(Hex.decodeHex(data
					.toCharArray())));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (DecoderException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	/**
	 * 签名
	 * @param data
	 * @param keyStorePath
	 * @param alias
	 * @param password
	 * @return
	 */
	public static String sign(String data, String keyStorePath, String alias, String password){
		try {
			//获得密钥库  
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
			FileInputStream is = new FileInputStream(keyStorePath);
			ks.load(is, password.toCharArray());
			is.close();
			//获得证书
			X509Certificate x509Certificate = (X509Certificate)ks.getCertificate(alias);
			
			Signature signature = Signature.getInstance(x509Certificate.getSigAlgName());
			PrivateKey privateKey = getPrivateKeyByKeystore(keyStorePath, alias, password);
			signature.initSign(privateKey);
			signature.update(Hex.decodeHex(data.toCharArray()));
			return Hex.encodeHexString(signature.sign());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (DecoderException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 验签
	 * @param data
	 * @param sign
	 * @param certificatePath
	 * @return
	 */
	public static boolean verify(String data, String sign, String certificatePath){
		try {
			//获得证书
			CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
			FileInputStream is = new FileInputStream(certificatePath);
			Certificate certificate = certificateFactory.generateCertificate(is);
			is.close();
			X509Certificate x509Certificate = (X509Certificate)certificate;
			
			Signature signature = Signature.getInstance(x509Certificate.getSigAlgName());
			signature.initVerify(x509Certificate);
			signature.update(Hex.decodeHex(data.toCharArray()));
			return signature.verify(Hex.decodeHex(sign.toCharArray()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (DecoderException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void main(String[] args) {
		String password = "123456";
		String alias = "yel";
		String certificatePath = "d:/yel.cer";
		String keyStorePath = "d:/yel.keystore";
		
		PrivateKey privateKey = getPrivateKeyByKeystore(keyStorePath, alias, password);
		System.out.println("从密钥库得到私钥:"+Hex.encodeHexString(privateKey.getEncoded()));
		PublicKey publicKey = getPublicKeyByCertificate(certificatePath);
		System.out.println("从证书得到公钥:"+Hex.encodeHexString(publicKey.getEncoded()));
		String data = "404142434445464748494a4b4c4d4e4f";
		System.out.println("原始数据："+data);
		String encode = codeByPrivateKey(data, privateKey, 0);
		System.out.println("私钥加密后："+encode);
		String decode = codeByPublicKey(encode, publicKey, 1);
		System.out.println("公钥解密后："+decode);
		encode = codeByPublicKey(data, publicKey, 0);
		System.out.println("公钥加密后："+encode);
		decode = codeByPrivateKey(encode, privateKey, 1);
		System.out.println("私钥解密后："+decode);
		String sign = sign(data, keyStorePath, alias, password);
		System.out.println("签名结果："+sign);
		System.out.println("验签结果："+verify(data, sign, certificatePath));
	}
}
