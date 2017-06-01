package fastdfs.lzh.cn.touna;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

public class FileUpload2FastDfs {
	public static void main(String[] args) throws IOException {
		
		URL resource = FileUpload2FastDfs.class.getClassLoader().getResource("fdfs_client.conf");
		
		String canonicalPath = new File(resource.getFile()).getCanonicalPath();
		
		//加载配置文件的方式
        try {
            ClientGlobal.init(canonicalPath);
        }catch(Exception e){
            e.printStackTrace();
        }
        File file = new File("d:/fastdfs-install.ppt");
        String[] files =  uploadFile(file, "fastdfs-install.ppt", file.length());
        System.out.println(Arrays.asList(files));
	}
	 /**
     * 上传文件
     */
    public static String[] uploadFile(File file, String uploadFileName, long fileLength) throws IOException {
        byte[] fileBuff = getFileBuffer(new FileInputStream(file), fileLength);
        String[] files = null;
        String fileExtName = "";
        if (uploadFileName.contains(".")) {
            fileExtName = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);
        } else {
            System.out.println("Fail to upload file, because the format of filename is illegal.");
            return null;
        }

        // 建立连接
        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;
        StorageClient client = new StorageClient(trackerServer, storageServer);

        // 设置元信息
        NameValuePair[] metaList = new NameValuePair[3];
        metaList[0] = new NameValuePair("fileName", uploadFileName);
        metaList[1] = new NameValuePair("fileExtName", fileExtName);
        metaList[2] = new NameValuePair("fileLength", String.valueOf(fileLength));

        // 上传文件
        try {
            files = client.upload_file("group1",fileBuff, fileExtName, metaList);
        } catch (Exception e) {
            System.out.println("Upload file \"" + uploadFileName + "\"fails");
        }finally{
        	if(null != trackerServer){
        		trackerServer.close();
        	}
        }
        return files;
    }
    private static byte[] getFileBuffer(InputStream inStream, long fileLength) throws IOException {

        byte[] buffer = new byte[256 * 1024];
        byte[] fileBuffer = new byte[(int) fileLength];

        int count = 0;
        int length = 0;

        while ((length = inStream.read(buffer)) != -1) {
            for (int i = 0; i < length; ++i) {
                fileBuffer[count + i] = buffer[i];
            }
            count += length;
        }
        return fileBuffer;
    }
}
