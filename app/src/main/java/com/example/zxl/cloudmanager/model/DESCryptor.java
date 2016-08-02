package com.example.zxl.cloudmanager.model;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import android.annotation.SuppressLint;
import android.util.Base64;
import android.util.Log;

public class DESCryptor {

	private static String cryptkey = "@ChuanKe";

	public static String Encryptor(String original) throws Exception {
		Log.e("ori", original);
		return Encryptor(original, cryptkey);
	}

	@SuppressLint("TrulyRandom")
	public static String Encryptor(String original, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		return encodeBase64(cipher.doFinal(original.getBytes("UTF-8")));
	}

	public static String Decryptor(String original) throws Exception {
		return Decryptor(original, cryptkey);
	}

	public static String Decryptor(String original, String key) throws Exception {
		byte[] bytesrc = decodeBase64(original);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		byte[] retByte = cipher.doFinal(bytesrc);
		return new String(retByte);
	}

	@SuppressWarnings("unused")
	private static byte[] convertHexString(String ss) {
		byte digest[] = new byte[ss.length() / 2];
		for (int i = 0; i < digest.length; i++) {
			String byteString = ss.substring(2 * i, 2 * i + 2);
			int byteValue = Integer.parseInt(byteString, 16);
			digest[i] = (byte) byteValue;
		}

		return digest;
	}

	@SuppressWarnings("unused")
	private static String toHexString(byte b[]) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String plainText = Integer.toHexString(0xff & b[i]);
			if (plainText.length() < 2)
				plainText = "0" + plainText;
			hexString.append(plainText);
		}
		return hexString.toString();
	}

	private static String encodeBase64(byte[] b) {
		return Base64.encodeToString(b, Base64.DEFAULT);
	}

	public static byte[] decodeBase64(String base64String) {
		return Base64.decode(base64String, Base64.DEFAULT);
	}

}
