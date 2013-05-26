package com.lube.utils;

import java.io.ByteArrayOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * @author
 * @version 1.0 
 */
public class MD5EncryptUtils {

	private MD5EncryptUtils() {
	}

	public static String MD5Encode(String origin) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return byteArrayToString(md.digest(origin.getBytes()));
	}

	/**
	 * 转换字节数组为16进制字符串或者全数组字符串
	 * @param b 字节数组
	 * @return 16进制字串
	 */
	private static String byteArrayToString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			byte aB = b[i];
			resultSb.append(byteToHexString(aB));
		}
		return resultSb.toString();
	}

	/**
	 * 转换字节数组为十六进制字符串
	 * @param b byte
	 * @return String
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * 十六进制字符
	 */
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	/**
	 * 64位加密key
	 */
	private final static String hexString = "0123456789abcdef";
	public static void main(String[] args) {
		System.out.println("-------->" + MD5Encode("000000"));
		System.out.println(String.valueOf(hexDigits.toString().toCharArray()));
		System.out.println(pass64Encode("ewww"));
		System.out.println(pass64Decode(pass64Encode("ewww")));
	}

	/**
	 * 将字符串编码成16进制数字,适用于所有字符（包括中文）
	 */
	public static String pass64Encode(String str) {
		// 根据默认编码获取字节数组
		byte[] bytes = str.getBytes();
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		BASE64Encoder enc = new BASE64Encoder();
		//加密运算之后 将byte[]转化为base64的String        
		String encryptedtext = enc.encode(sb.toString().getBytes());
		return encryptedtext.toString();
	}

	/**
	 * 将16进制数字解码成字符串,适用于所有字符（包括中文）
	 */
	public static String pass64Decode(String bytes) {
		
		// 解密运算 将base64的String转化为byte[] 
		BASE64Decoder dec = new BASE64Decoder();  
		String encryptedData = null;
		try { 
			encryptedData = new String(dec.decodeBuffer(bytes));  
			ByteArrayOutputStream baos = new ByteArrayOutputStream(
					bytes.length() / 2);
			// 将每2位16进制整数组装成一个字节
			for (int i = 0; i < encryptedData.length(); i += 2){
				baos.write((hexString.indexOf(encryptedData.charAt(i)) << 4 | hexString
						.indexOf(encryptedData.charAt(i + 1))));
			}
			encryptedData =new String(baos.toByteArray());
		} catch (Exception e) {
			e.printStackTrace(); 
		} 
		return new String(encryptedData);
	}
}
