package file.encode.test;

import kafka.lzh.cn.touna.MD5;

public class Test {
	
	@org.junit.Test
	public void test() throws Exception{
		System.out.println(System.currentTimeMillis());
		
		Long clientTime = System.currentTimeMillis();
		String accessKeyId = "MLtcw5QFVaX1FjJCE1R5bzeLsKuJJaJc";
		String md5 = MD5.md5(clientTime+accessKeyId);
		System.out.println(md5);
	}
}
