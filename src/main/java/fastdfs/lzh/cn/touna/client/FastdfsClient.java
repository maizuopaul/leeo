package fastdfs.lzh.cn.touna.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URL;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.FdfsException;
import org.csource.common.IniFileReader;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerGroup;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FastdfsClient {

	private final static String FDFS_CLIENT_CONF = "fdfs_client.conf";
	private final static Logger	logger	= LoggerFactory.getLogger(FastdfsClient.class.getSimpleName());
	public static final int DEFAULT_CONNECT_TIMEOUT = 5;  //second
	public static final int DEFAULT_NETWORK_TIMEOUT = 30; //second
	
	private final static FastdfsClient instance = new FastdfsClient();
	
	private FastdfsClient(){
		initConfig();
	}
	/**
	 * 初始化配置
	 */
	private void initConfig(){
		PropertiesConfiguration config;
		try {
			logger.debug("begin to init Fastdfs configuration");
			config = new PropertiesConfiguration(FDFS_CLIENT_CONF);
			URL resource = config.getURL();
			String canonicalPath = new File(resource.getFile()).getCanonicalPath();
			
			IniFileReader iniReader;
	  		String[] szTrackerServers;
	  		String[] parts;
	  		
	  		iniReader = new IniFileReader(canonicalPath);

			int g_connect_timeout = iniReader.getIntValue("connect_timeout", DEFAULT_CONNECT_TIMEOUT);
	  		if (g_connect_timeout < 0){
	  			g_connect_timeout = DEFAULT_CONNECT_TIMEOUT;
	  		}
	  		g_connect_timeout *= 1000; //millisecond
	  		
	  		
	  		
	  		int g_network_timeout = iniReader.getIntValue("network_timeout", DEFAULT_NETWORK_TIMEOUT);
	  		if (g_network_timeout < 0){
	  			g_network_timeout = DEFAULT_NETWORK_TIMEOUT;
	  		}
	  		g_network_timeout *= 1000; //millisecond
	  		
	  		
	  		
	  		String g_charset = iniReader.getStrValue("charset");
	  		if (g_charset == null || g_charset.length() == 0){
	  			g_charset = "ISO8859-1";
	  		}
	  		
	  		
	  		
	  		szTrackerServers = iniReader.getStrValue("tracker_server_list").split(",");
	  		if (szTrackerServers == null){
	  			throw new FdfsException("item \"tracker_server\" in " + canonicalPath + " not found");
	  		}
	  		InetSocketAddress[] tracker_servers = new InetSocketAddress[szTrackerServers.length];
	  		
	  		for (int i=0; i<szTrackerServers.length; i++){
	  			parts = szTrackerServers[i].split("\\:", 2);
	  			if (parts.length != 2){
	  				throw new FdfsException("the value of item \"tracker_server\" is invalid, the correct format is host:port");
	  			}
	  			
	  			tracker_servers[i] = new InetSocketAddress(parts[0].trim(), Integer.parseInt(parts[1].trim()));
	  		}
	  		TrackerGroup g_tracker_group = new TrackerGroup(tracker_servers);
	  		
	  		int g_tracker_http_port = iniReader.getIntValue("http.tracker_http_port", 80);
	  		
	  		boolean g_anti_steal_token = iniReader.getBoolValue("http.anti_steal_token", false);
	  		
	  		ClientGlobal.setG_connect_timeout(g_connect_timeout);
	  		ClientGlobal.setG_network_timeout(g_network_timeout);
	  		ClientGlobal.setG_charset(g_charset);
	  		ClientGlobal.setG_tracker_group(g_tracker_group);
	  		ClientGlobal.setG_tracker_http_port(g_tracker_http_port);
	  		ClientGlobal.setG_anti_steal_token(g_anti_steal_token);
	  		if (g_anti_steal_token){
	  			String g_secret_key = iniReader.getStrValue("http.secret_key");
	  			ClientGlobal.setG_secret_key(g_secret_key);
	  		}
	  		logger.debug("finished to init Fastdfs configuration");
		} catch (ConfigurationException e) {
			logger.error("初始化fastdfs_conf配置出错!",e);
		} catch (IOException e) {
			logger.error("初始化fastdfs_conf配置出错!",e);
		} catch (FdfsException e) {
			logger.error("初始化fastdfs_conf配置出错!",e);
		}
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
	public void download(String group,String remotename,String filepthname) throws FileNotFoundException, IOException, FdfsException{
		
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
		TrackerClient tracker = new TrackerClient(); 
		TrackerServer trackerServer = tracker.getConnection(); 
        StorageServer storageServer = null;
        StorageClient storageClient = new StorageClient(trackerServer, storageServer); 
        byte[] b = storageClient.download_file(group, remotename);
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
		TrackerClient tracker = new TrackerClient(); 
		TrackerServer trackerServer = tracker.getConnection(); 
		StorageServer storageServer = null;
		StorageClient storageClient = new StorageClient(trackerServer, storageServer); 
		byte[] b = storageClient.download_file(group, remotename);
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
	public byte[] getFileByteStream(String group,String remotename) throws IOException, FdfsException{
		if(StringUtils.isBlank(group)){
			logger.warn("download failed! fastdfs group is blank!");
			throw new RuntimeException("download failed! fastdfs group name is blank!");
		}
		if(StringUtils.isBlank(remotename)){
			logger.warn("download failed! fastdfs file id is blank!");
			throw new RuntimeException("download failed! fastdfs file id is blank!");
		}
		TrackerClient tracker = new TrackerClient();
		TrackerServer trackerServer = tracker.getConnection(); 
		StorageServer storageServer = null;
		StorageClient storageClient = new StorageClient(trackerServer, storageServer); 
		byte[] b = storageClient.download_file(group, remotename);
		return b;
	}
	/**
	 * 上传文件
	 * @param fileDataBytes	文件字节数组
	 * @param uploadFileName	文件名
	 * @return
	 * @throws Exception
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
        String[] files = null;
        String fileExtName = "";
        if (uploadFileName.contains(".")) {
            fileExtName = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);
        } else {
           logger.warn("Fail to upload file, because the format of filename is illegal.");
           throw new RuntimeException("upload filename not contains of file type");
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
        metaList[2] = new NameValuePair("fileLength", String.valueOf(fileDataBytes.length));

        // 上传文件
        try {
            files = client.upload_file(fileDataBytes, fileExtName, metaList);
        } catch (Exception e) {
        	logger.error("Upload file \""+uploadFileName + "\"failed!",e);
        	throw new RuntimeException(e);
        }finally{
        	if(null != trackerServer){
        		trackerServer.close();
        	}
        }
        return files;
    }
    
    /**
     * 删除文件
     * @param groupName
     * @param remoteFileName
     * @return 0 for success, none zero for fail (error code)
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
    	
    	// 建立连接
        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;
        StorageClient client = new StorageClient(trackerServer, storageServer);
        // 上传文件
        try {
        	  return (client.delete_file(groupName, remoteFileName) == 0);
        } catch (Exception e) {
        	logger.error("delete file \""+remoteFileName + "\"failed!",e);
        	throw new RuntimeException(e);
        }finally{
        	if(null != trackerServer){
        		trackerServer.close();
        	}
        }
    }
    
    
	public static FastdfsClient getInstance(){
		return instance;
	}
}
