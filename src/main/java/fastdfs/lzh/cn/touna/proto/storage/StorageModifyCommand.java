package fastdfs.lzh.cn.touna.proto.storage;

import java.io.InputStream;

import fastdfs.lzh.cn.touna.proto.AbstractFdfsCommand;
import fastdfs.lzh.cn.touna.proto.FdfsResponse;
import fastdfs.lzh.cn.touna.proto.storage.internal.StorageModifyRequest;

/**
 * 文件修改命令
 * 
 * @author tobato
 *
 */
public class StorageModifyCommand extends AbstractFdfsCommand<Void> {

    /**
     * 文件修改命令
     * 
     * @param path
     * @param inputStream
     * @param fileSize
     * @param fileOffset
     */
    public StorageModifyCommand(String path, InputStream inputStream, long fileSize, long fileOffset) {
        super();
        this.request = new StorageModifyRequest(inputStream, fileSize, path, fileOffset);
        // 输出响应
        this.response = new FdfsResponse<Void>() {
            // default response
        };
    }

}
