package encryptionAlgorithm;

import java.util.Vector;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AES {
	public static byte[] encode(String message, String passWord) {
		
		passWord = getRightPassword(passWord);
		
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			/* AES算法 */
			SecretKey secretKey = new SecretKeySpec(passWord.getBytes(), "AES");// 获得密钥
			/* 获得一个私鈅加密类Cipher，DESede-》AES算法，ECB是加密模式，PKCS5Padding是填充方式 */
			cipher.init(Cipher.ENCRYPT_MODE, secretKey); // 设置工作模式为加密模式，给出密钥
			byte[] resultBytes = cipher.doFinal(message.getBytes("UTF-8")); // 正式执行加密操作
			return resultBytes;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] encode(byte[] message, String passWord) {
		
		passWord = getRightPassword(passWord);
		
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			/* AES算法 */
			SecretKey secretKey = new SecretKeySpec(passWord.getBytes(), "AES");// 获得密钥
			/* 获得一个私鈅加密类Cipher，DESede-》AES算法，ECB是加密模式，PKCS5Padding是填充方式 */
			cipher.init(Cipher.ENCRYPT_MODE, secretKey); // 设置工作模式为加密模式，给出密钥
			byte[] resultBytes = cipher.doFinal(message); // 正式执行加密操作
			return resultBytes;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getRightPassword(String passWord) {
		if (passWord.length()>16){
			char[] a=passWord.toCharArray();
			passWord=new String();
			for (int i=0;i<32;i++) passWord+=a[i];
		}else if (passWord.length()<16){
			while (passWord.length()<16){
				passWord+="x";
			}
		}
		return passWord;
	}
	
	public static Vector <String> decodeAsVector(byte[] messageBytes, String passWord) {
		char[] ret=decode(messageBytes, passWord).toCharArray();
		Vector <String> ans=new Vector<String>();
		String now=new String();
		for (int i=0;i<ret.length;i++){
			if (ret[i]=='\r'&&i!=ret.length-1&&ret[i+1]=='\n'){
				ans.add(now);
				now=new String();
			}else if (ret[i]=='\n'){
				continue;
			}else{
				now+=ret[i];
			}
		}
		return ans;
	}
	
	public static String decode(byte[] messageBytes, String passWord) {
		String result = "";
		
		passWord = getRightPassword(passWord);
		
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			/* AES算法 */
			SecretKey secretKey = new SecretKeySpec(passWord.getBytes(), "AES");// 获得密钥
			cipher.init(Cipher.DECRYPT_MODE, secretKey); // 设置工作模式为解密模式，给出密钥
			byte[] resultBytes = cipher.doFinal(messageBytes);// 正式执行解密操作
			result = new String(resultBytes, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static byte[] decodeAsByte(byte[] messageBytes, String passWord) {
		byte[] result=null;
		
		passWord = getRightPassword(passWord);
		
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			/* AES算法 */
			SecretKey secretKey = new SecretKeySpec(passWord.getBytes(), "AES");// 获得密钥
			cipher.init(Cipher.DECRYPT_MODE, secretKey); // 设置工作模式为解密模式，给出密钥
			result = cipher.doFinal(messageBytes);// 正式执行解密操作
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
