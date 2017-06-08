package fastdfs.lzh.cn.touna.proto.storage;

import java.util.Set;

import fastdfs.lzh.cn.touna.domain.MetaData;
import fastdfs.lzh.cn.touna.proto.AbstractFdfsCommand;
import fastdfs.lzh.cn.touna.proto.storage.internal.StorageGetMetadataRequest;
import fastdfs.lzh.cn.touna.proto.storage.internal.StorageGetMetadataResponse;

/**
 * 设置文件标签
 * 
 * @author tobato
 *
 */
public class StorageGetMetadataCommand extends AbstractFdfsCommand<Set<MetaData>> {

    /**
     * 设置文件标签(元数据)
     * 
     * @param groupName
     * @param path
     * @param metaDataSet
     * @param type
     */
    public StorageGetMetadataCommand(String groupName, String path) {
        this.request = new StorageGetMetadataRequest(groupName, path);
        // 输出响应
        this.response = new StorageGetMetadataResponse();
    }

}
