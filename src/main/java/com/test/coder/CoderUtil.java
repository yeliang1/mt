package com.test.coder;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

public class CoderUtil {
	public static final String ENCODING = "UTF-8";

	/**
	 * BASE64编码
	 * @param data
	 * @return
	 */
	public static String Base64encode(String data) {
		try {
			byte[] b = Base64.encodeBase64(data.getBytes(ENCODING));
			return new String(b, ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * BASE64解码
	 * @param data
	 * @return
	 */
	public static String Base64decode(String data) {
		try {
			byte[] b = Base64.decodeBase64(data.getBytes(ENCODING));
			return new String(b, ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * MD5消息摘要
	 * @param data
	 * @return
	 */
	public static String MD5encode(String data) {
		return DigestUtils.md5Hex(data);
	}

	/**
	 * SHA1消息摘要
	 * @param data
	 * @return
	 */
	public static String SHA1encode(String data) {
		return DigestUtils.shaHex(data);
	}

	/**
	 * 生成HmacMD5算法的密钥
	 * @return
	 */
	public static String HmacMD5KeyGenerate() {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");
			SecretKey secretKey = keyGenerator.generateKey();
			return Hex.encodeHexString(secretKey.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * HmacMD5算法计算MAC
	 * @param key
	 * @param data
	 * @return
	 */
	public static String HmacMD5encode(String key, String data) {
		try {
			SecretKey secretKey = new SecretKeySpec(Hex.decodeHex(key
					.toCharArray()), "HmacMD5");
			Mac mac = Mac.getInstance(secretKey.getAlgorithm());
			mac.init(secretKey);
			byte[] macByte = mac.doFinal(Hex.decodeHex(data.toCharArray()));
			return Hex.encodeHexString(macByte);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (DecoderException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * DES ECB模式加解密
	 * @param key
	 * @param data
	 * @param mode
	 *            0-加密，1-解密
	 * @return
	 */
	public static String desecb(String key, String data, int mode) {
		try {
			DESKeySpec dks = new DESKeySpec(Hex.decodeHex(key.toCharArray()));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
			if (mode == 0) {
				cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			} else {
				cipher.init(Cipher.DECRYPT_MODE, secretKey);
			}
			return Hex.encodeHexString(cipher.doFinal(Hex.decodeHex(data
					.toCharArray())));
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (DecoderException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 基于口令加密PBE算法
	 * @param password 口令
	 * @param data     数据
	 * @param salt     盐
	 * @param mode
	 *            0-加密，1-解密
	 * @return
	 */
	public static String pbe(String password, String data, byte[] salt, int mode){
		try {
			//转换密码
			PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWITHMD5andDES");
			SecretKey secretKey = keyFactory.generateSecret(keySpec);
			
			PBEParameterSpec paramSpec = new PBEParameterSpec(salt, 100);
			Cipher cipher = Cipher.getInstance("PBEWITHMD5andDES");
			if (mode == 0) {
				cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
			} else {
				cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
			}
			return Hex.encodeHexString(cipher.doFinal(Hex.decodeHex(data
					.toCharArray())));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
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
	 * 初始化RSA密钥对
	 * @return
	 */
	public static String[] initRSAKey(){
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
			KeyPair keyPair = keyPairGen.generateKeyPair();
			RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
			RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
			return new String[]{Hex.encodeHexString(publicKey.getEncoded()), Hex.encodeHexString(privateKey.getEncoded())};		
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * RSA私钥加解密
	 * @param data
	 * @param key
	 * @param mode
	 *            0-加密，1-解密
	 * @return
	 */
	public static String RSAByPrivateKey(String data, String key, int mode){
		try {
			//取得私钥  
	        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Hex.decodeHex(key.toCharArray()));  
	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
	        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);  
			
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
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
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * RSA公钥加解密
	 * @param data
	 * @param key
	 * @param mode
	 *            0-加密，1-解密
	 * @return
	 */
	public static String RSAByPublicKey(String data, String key, int mode){
		try {
			//取得公钥  
	        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Hex.decodeHex(key.toCharArray()));  
	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
	        Key publicKey = keyFactory.generatePublic(x509KeySpec);  

			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
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
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * RSA私钥签名
	 * @param data
	 * @param key
	 * @return
	 */
	public static String RSAsign(String data, String key){
		try {
			//取得私钥  
	        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Hex.decodeHex(key.toCharArray()));  
	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
	        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);  
			
			Signature signature = Signature.getInstance("MD5withRSA");
			signature.initSign(privateKey);
			signature.update(Hex.decodeHex(data.toCharArray()));
			return Hex.encodeHexString(signature.sign());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (DecoderException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * RSA公钥验签
	 * @param data
	 * @param key
	 * @param sign
	 * @return
	 */
	public static boolean RSAverify(String data, String key, String sign){
		try {
			//取得公钥  
	        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Hex.decodeHex(key.toCharArray()));  
	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
	        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);  
			
			Signature signature = Signature.getInstance("MD5withRSA");
			signature.initVerify(publicKey);
			signature.update(Hex.decodeHex(data.toCharArray()));
			return signature.verify(Hex.decodeHex(sign.toCharArray()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (DecoderException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) throws NoSuchAlgorithmException, DecoderException {
		// String data = "yeliang";
		// System.out.println("原文："+data);
		// String encode = Base64encode(data);
		// System.out.println("Base64编码后："+encode);
		// String decode = Base64decode(encode);
		// System.out.println("Base64解码后："+decode);
		// String md5 = MD5encode(data);
		// System.out.println("MD5消息摘要："+md5);
		// String sha1 = SHA1encode(data);
		// System.out.println("SHA1消息摘要："+sha1);
		// String hmacMD5Key = HmacMD5KeyGenerate();
		// System.out.println("HmacMD5密钥："+hmacMD5Key);
		// String hmacMD5 = HmacMD5encode(hmacMD5Key,
		// "404142434445464748494a4b4c4d4f");
		// System.out.println("HmacMD5生成MAC："+hmacMD5);
		
//		String key = "404142434445464748494a4b4c4d4e4f";
//		String data = "404142434445464748494a4b4c4d4e4f";
//		String encode = desecb(key, data, 1);
//		System.out.println("DES加密后的密文：" + encode);
//		String decode = desecb(key, encode, 0);
//		System.out.println("DES解密后的明文：" + decode);
		
//		String password = "yel";
//		System.out.println("口令:"+password);
//		String data = "404142434445464748494a4b4c4d4e4f";
//		System.out.println("原始数据:"+data);
//		byte[] salt = new SecureRandom().generateSeed(8);
//		System.out.println("盐:"+Hex.encodeHexString(salt));
//		String encode = pbe(password, data, salt, 0);
//		System.out.println("PBE加密后的密文：" + encode);
//		String decode = pbe(password, encode, salt, 1);
//		System.out.println("PBE解密后的明文：" + decode);
		
//		String[] key = initRSAKey();
//		String publicKey = key[0];
//		System.out.println("RSA公钥："+publicKey);
//		String privateKey = key[1];
//		System.out.println("RSA私钥："+privateKey);
//		String data = "404142434445464748494a4b4c4d4e4f";
//		System.out.println("原始数据:"+data);
//		String encode = RSAByPrivateKey(data, privateKey, 0);
//		System.out.println("RSA私钥加密后："+encode);
//		String decode = RSAByPublicKey(encode, publicKey, 1);
//		System.out.println("RSA公钥解密后："+decode);
//		encode = RSAByPublicKey(data, publicKey, 0);
//		System.out.println("RSA公钥加密后："+encode);
//		decode = RSAByPrivateKey(encode, privateKey, 1);
//		System.out.println("RSA私钥解密后："+decode);
		
		String[] key = initRSAKey();
		String publicKey = key[0];
		System.out.println("RSA公钥："+publicKey);
		String privateKey = key[1];
		System.out.println("RSA私钥："+privateKey);
		String data = "404142434445464748494a4b4c4d4e4f";
		System.out.println("原始数据："+data);
		String sign = RSAsign(data, privateKey);
		System.out.println("私钥签名结果："+sign);
		System.out.println("公钥验签结果："+RSAverify(data, publicKey, sign));
	}
}
