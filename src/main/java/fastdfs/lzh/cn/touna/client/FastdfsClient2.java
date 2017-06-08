package fastdfs.lzh.cn.touna.client;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.FdfsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.lzh.cn.touna.ConfUtil;
import fastdfs.lzh.cn.touna.conn.ConnectionManager;
import fastdfs.lzh.cn.touna.conn.FdfsConnectionPool;
import fastdfs.lzh.cn.touna.conn.TrackerConnectionManager;
import fastdfs.lzh.cn.touna.domain.MetaData;
import fastdfs.lzh.cn.touna.domain.StorePath;
import fastdfs.lzh.cn.touna.service.DefaultFastFileStorageClient;
import fastdfs.lzh.cn.touna.service.DefaultTrackerClient;
import fastdfs.lzh.cn.touna.service.FastFileStorageClient;
import fastdfs.lzh.cn.touna.service.TrackerClient;

/**
 * 该工具类采用fastdfs连接池进行操作
 * @author leeo
 *
 */
public class FastdfsClient2 {
	private final static String FDFS_CLIENT_CONF = "resource/fdfs_client.properties";
	
	private final static Logger	logger	= LoggerFactory.getLogger(FastdfsClient2.class.getSimpleName());	
	private final static FastdfsClient2 instance = new FastdfsClient2();
	private FastFileStorageClient storageClient;
	private FastdfsClient2(){
		initConfig();
	}
	public static FastdfsClient2 getInstance(){
		return instance;
	}
	private void initConfig() {
		try {
			logger.debug("begin to init FastdfsClient configuration");
			List<Object> trackServerListStr = ConfUtil.getList(FDFS_CLIENT_CONF, "tracker_server_list");
			if( null ==  trackServerListStr || trackServerListStr.size()<=0){
				throw new RuntimeException("fdfs_client.conf 配置文件中缺少 tracker_server_list配置项");
			}else{
				List<String> trackers = new ArrayList<>();
				for (Object tracker : trackServerListStr) {
					trackers.add((String)tracker);
				}
				FdfsConnectionPool pool = new FdfsConnectionPool();
				TrackerConnectionManager connMgr = new TrackerConnectionManager(pool, trackers);
				TrackerClient trackerClient = new DefaultTrackerClient(connMgr);
				ConnectionManager cm = new ConnectionManager(pool);
				storageClient = new DefaultFastFileStorageClient(trackerClient, cm);
			}
		}catch(Exception ex){
			logger.error("初始化FastdfsClient 出错!",ex);
		}
		logger.debug("finished to init Fastdfs configuration");
	}
	/**
	 * 从fastdfs中下载文件
	 * @param group group名字
	 * @param remotename	fastdfs系统中的file ID
	 * @param filepthname	保存在本地的完整文件名,包含完整的路径和文件名后缀
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws FdfsException
	 */
	public void download(String group,String remotename,String filepthname) throws FileNotFoundException, IOException{
		
		if(StringUtils.isBlank(group)){
			logger.warn("download failed! fastdfs group is blank!");
			return;
		}
		if(StringUtils.isBlank(remotename)){
			logger.warn("download failed! fastdfs file id is blank!");
			return;
		}
		if(StringUtils.isBlank(filepthname)){
			logger.warn("download failed! to save path is blank!");
			return;
		}
		
		byte[] b = storageClient.downloadFile(group, remotename);
		
		if(null == b || b.length <=0){
			logger.warn("download failed! the data query from fastdfs is null!");
	        return;
	    }
	    IOUtils.write(b, new FileOutputStream(filepthname));
	}
	
	/**
	 * 从fastdfs中下载文件
	 * @param group group名字
	 * @param remotename	fastdfs系统中的file ID
	 * @param saveFileName	待保存的文件名,包含文件名后缀
	 * @param savePath	待保存的目标路径
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws FdfsException
	 */
	public void download(String group,String remotename,String saveFileName,String savePath) throws FileNotFoundException, IOException, FdfsException{
		
		if(StringUtils.isBlank(group)){
			logger.warn("download failed! fastdfs group is blank!");
			return;
		}
		if(StringUtils.isBlank(remotename)){
			logger.warn("download failed! fastdfs file id is blank!");
			return;
		}
		if(StringUtils.isBlank(saveFileName)){
			logger.warn("download failed! to save filename is blank!");
			return;
		}
		if(StringUtils.isBlank(savePath)){
			logger.warn("download failed! to save path is blank!");
			return;
		}
	
		byte[] b = storageClient.downloadFile(group, remotename);
		if(null == b || b.length <=0){
	      	logger.warn("download failed! the data query from fastdfs is null!");
	      	return;
	    }
		if(File.separator.equals(savePath.substring(savePath.length()-1, savePath.length()))){
			savePath=savePath+saveFileName;
		}else{
			savePath=savePath+File.separator+saveFileName;
		}
		IOUtils.write(b, new FileOutputStream(savePath));
	}
	
	
	/**
	 * 从fastdfs存储系统中获取文件字节流
	 * @param group group名字
	 * @param remotename	fastdfs系统中的file ID
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws FdfsException
	 * @throws ConfigurationException 
	 */
	public byte[] getFileByteStream(String group,String remotename){
		if(StringUtils.isBlank(group)){
			logger.warn("download failed! fastdfs group is blank!");
			throw new RuntimeException("download failed! fastdfs group name is blank!");
		}
		if(StringUtils.isBlank(remotename)){
			logger.warn("download failed! fastdfs file id is blank!");
			throw new RuntimeException("download failed! fastdfs file id is blank!");
		}
	    byte[] downloadFile = storageClient.downloadFile(group, remotename);
	    return downloadFile;
	}
	
	
	/**
     * 上传文件
     */
    public String[] uploadFile(byte[] fileDataBytes, String uploadFileName) throws Exception {
        if(null == fileDataBytes || fileDataBytes.length <= 0){
        	logger.warn("Fail to upload file,because of the upload data is empty!");
        	 throw new RuntimeException("upload file byte data is empty");
        }
        if(StringUtils.isEmpty(uploadFileName)){
        	logger.warn("Fail to upload file,because of the fileName is empty!");
        	 throw new RuntimeException("upload filename is empty");
        }
        String[] files = new String[2];
        String fileExtName = "";
        if (uploadFileName.contains(".")) {
            fileExtName = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);
        } else {
           logger.warn("Fail to upload file, because the format of filename is illegal.");
           throw new RuntimeException("upload filename not contains of file type");
        }

        // 上传文件
        try {
        	Set<MetaData> metaDataSet = new HashSet<MetaData>();
        	MetaData meta = new MetaData();
        	meta.setName("fileName");
        	meta.setValue(uploadFileName);
        	
        	MetaData meta2 = new MetaData();
        	meta2.setName("fileExtName");
        	meta2.setValue(fileExtName);
        	
        	MetaData meta3 = new MetaData();
        	meta3.setName("fileLength");
        	meta3.setValue( String.valueOf(fileDataBytes.length));
        	
        	metaDataSet.add(meta);
        	metaDataSet.add(meta2);
        	metaDataSet.add(meta3);
        	
        	
            ByteArrayInputStream buff = new ByteArrayInputStream(fileDataBytes);
            StorePath uploadFile = storageClient.uploadFile(buff, fileDataBytes.length, fileExtName, metaDataSet);
            files[0] = uploadFile.getGroup();
            files[1] = uploadFile.getPath();
        } catch (Exception e) {
        	logger.error("Upload file \""+uploadFileName + "\"failed!",e);
        	throw new RuntimeException(e);
        }
        return files;
    }
    /**
     * 删除文件
     * @param groupName
     * @param remoteFileName
     * @return true success,false for fail (error code)
     * @throws Exception
     */
    public boolean deleteFile(String groupName,String remoteFileName)  throws Exception {
    	if(StringUtils.isBlank(groupName)){
			logger.warn("download failed! fastdfs group is blank!");
			throw new RuntimeException("delete failed! fastdfs group name is blank!");
		}
		if(StringUtils.isBlank(remoteFileName)){
			logger.warn("delete failed! fastdfs file id is blank!");
			throw new RuntimeException("download failed! fastdfs file id is blank!");
		}
    	
        // 上传文件
        try {
        	  storageClient.deleteFile(groupName, remoteFileName);
        	  return true;
        } catch (Exception e) {
        	logger.error("delete file \""+remoteFileName + "\"failed!",e);
        	throw new RuntimeException(e);
        }
    }
}
