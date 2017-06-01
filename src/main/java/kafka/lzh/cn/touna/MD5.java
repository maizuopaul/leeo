package kafka.lzh.cn.touna;

import java.security.MessageDigest;

/**
 * 
 *                       
 * @Filename MD5.java
 *
 * @Description md5加密工具类
 *
 * @Version 1.0
 *
 * @Author Lijie
 *
 * @Email lijiewj39069@touna.cn
 *       
 * @History
 *<li>Author: Lijie</li>
 *<li>Date: 2017年3月1日</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
public class MD5 {
	
	/**
	 * md5加密方法
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String md5(String data) throws Exception {
		// 将字符串转为字节数组
		byte[] strBytes = (data).getBytes();
		// 加密器
		MessageDigest md = MessageDigest.getInstance("MD5");
		// 执行加密
		md.update(strBytes);
		// 加密结果
		byte[] digest = md.digest();
		// 返回
		return byteArrayToHex(digest);
	}
	
	/**
	 * hash转换
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteArrayToHex(byte[] byteArray) {
		// 首先初始化一个字符数组，用来存放每个16进制字符
		
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
				'E', 'F' };
		
		// new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
		
		char[] resultCharArray = new char[byteArray.length * 2];
		
		// 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
		
		int index = 0;
		
		for (byte b : byteArray) {
			
			resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
			
			resultCharArray[index++] = hexDigits[b & 0xf];
			
		}
		
		// 字符数组组合成字符串返回
		
		return new String(resultCharArray);
	}
	
}
