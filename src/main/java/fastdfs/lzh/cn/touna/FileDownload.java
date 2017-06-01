package fastdfs.lzh.cn.touna;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.csource.common.FdfsException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

public class FileDownload {
	public static void main(String[] args) throws IOException, FdfsException{
		URL resource = FileUpload2FastDfs.class.getClassLoader().getResource("fdfs_client.conf");
	
		String canonicalPath = new File(resource.getFile()).getCanonicalPath();
		ClientGlobal.init(canonicalPath);
		
		TrackerClient tracker = new TrackerClient(); 
		
		 TrackerServer trackerServer = tracker.getConnection(); 
         StorageServer storageServer = null;

         StorageClient storageClient = new StorageClient(trackerServer, storageServer); 
         String remote_filename = "M00/B6/10/CgAEjlkdFkeASP9SAAABDV-uTKc04.conf";
		 byte[] b = storageClient.download_file("group1", remote_filename); 
         System.out.println(b); 
         IOUtils.write(b, new FileOutputStream("D:/"+UUID.randomUUID().toString()+".conf"));
//       storageClient.delete_file("group1", remote_filename);
	}
}
