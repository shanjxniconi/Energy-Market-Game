package util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utilities for password encryption. The main job of this class is to offer MD5
 * encryption.
 * 
 * @author Yichen.Hua on 2018/05/08.
 */
public class EncryptUtil {

	/**
	 * The salt of the encryption.
	 */
	private static final String COOKBOOK_SALT = "cookbook";

	/**
	 * Unit test for md5 encode.
	 * 
	 * @param args
	 *            parameters from console.
	 */
	public static void main(String[] args) {
		System.out.println(EncryptUtil.MD5("123"));
	}

	/**
	 * encrypt the password with MD5 encryption algorithm.
	 * 
	 * @param str:
	 *            string to be encrypted.
	 * 
	 * @return result encrypted string.
	 */
	public static String MD5(String str) {
		String newString = str + COOKBOOK_SALT;
		return encode(newString, "MD5");
	}

	/**
	 * Encode the password with MD5 encryption algorithm.
	 * 
	 * @param str
	 *            string to be encoded.
	 * 
	 * @param method
	 *            the method to encode the string.
	 * 
	 * @return the encoded password.
	 */
	private static String encode(String str, String method) {
		MessageDigest md = null;
		String dstr = null;
		try {
			md = MessageDigest.getInstance(method);
			md.update(str.getBytes());
			dstr = new BigInteger(1, md.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return dstr;
	}

}

