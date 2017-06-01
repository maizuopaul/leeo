package lzh.touna.cn.test;

import java.io.UnsupportedEncodingException;

public class DecodeTest {

	public static void main(String[] args) throws UnsupportedEncodingException {
	/*	byte str[] = "abcABC".getBytes("utf-8");
		byte str2[] = "abcABC中".getBytes();
		System.out.println(str.length);
		for(byte b : str){
			System.out.print(b+ "=" + Integer.toBinaryString(b) +"\t");
		}
		System.out.println("\n============");
		for(byte b : str2){
			System.out.print(b+ "=" + Integer.toBinaryString(b) +"\t");
		}
		*/
		System.out.println("\n============");
		byte[] str = "中国".getBytes("UTF-16");
		
		
		for(byte b : str){
			System.out.println(b+ "=" + Integer.toBinaryString(b));
		}
		System.out.println("++++++++++++++");
		byte z[] = new byte[]{str[0],str[1]};
		System.out.println(new String(z,"UTF-16"));
	}

}
