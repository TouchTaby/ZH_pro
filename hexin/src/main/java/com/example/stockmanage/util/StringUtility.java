package com.example.stockmanage.util;


/**
 * 字符工具类
 * 
 * @author
 * 
 */
public class StringUtility {
	/**
	 * byte类型数组转十六进制字符串
	 * 
	 * @param b
	 *            byte类型数组
	 * @param size
	 *            数组长度
	 * @return 十六进制字符串
	 */
	public static String bytes2HexString(byte[] b, int size) {
		StringBuilder stringBuilder=new StringBuilder();
		try {
			for (int i = 0; i < size; i++) {
				String hex = Integer.toHexString(b[i] & 0xFF);
				if (hex.length() == 1) {
					hex = "0" + hex;
				}
				stringBuilder.append(hex.toUpperCase());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}

	/**
	 * byte转十六进制字符
	 *
	 * @param b
	 *            byte值
	 * @return 十六进制字符
	 */
	public static String byte2HexString(byte b) {
		String ret = "";

		try {
			String hex = Integer.toHexString(b & 0xFF);
			if (hex.length() == 1) {
				hex = "0" + hex;
			}
			ret += hex.toUpperCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * char类型数组转十六进制字符串
	 * 
	 * @param c
	 *            char类型数组
	 * @param size
	 *            数组大小
	 * @return 十六进制字符串
	 */
	public static String chars2HexString(char[] c, int size) {
		StringBuilder stringBuilder=new StringBuilder();// ret = "";

		try {
			int j = 0;
			for (int i = 0; i < size; i++) {
				j = Integer.valueOf(c[i]);
				String hex = Integer.toHexString(j);
				if (hex.length() == 1) {
					hex = "0" + hex;
				}
				stringBuilder.append(hex.toUpperCase());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}

	/**
	 * 十六进制字符串转换成char数组
	 *
	 * @param s
	 *            十六进制字符串
	 * @return char数组
	 */
	public static char[] hexString2Chars(String s) {

		s = s.replace(" ", "");

		char[] bytes = new char[s.length() / 2];

		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (char) Integer.parseInt(s.substring(2 * i, 2 * i + 2),
					16);
		}

		return bytes;
	}

	/**
	 * 十六进制字符串转byte数组
	 * 
	 * @param hexString
	 *            十六进制字符串
	 * @return byte数组
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * char转byte
	 * 
	 * @param c
	 *            char类型数组
	 * @return byte类型数组
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}





}
