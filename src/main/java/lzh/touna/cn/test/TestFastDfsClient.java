package lzh.touna.cn.test;

import fastdfs.lzh.cn.touna.FastDFSClient;

public class TestFastDfsClient {
	
	public static void test() throws Exception{
		FastDFSClient client = new FastDFSClient("fdfs_client.conf");
	    String uploadFile = client.uploadFile("d:/fdfs_client.conf", "conf");
	    System.out.println(uploadFile);//返回的是文件的
	}
	public static void main(String[] args) throws Exception {
		test();
	}
}
