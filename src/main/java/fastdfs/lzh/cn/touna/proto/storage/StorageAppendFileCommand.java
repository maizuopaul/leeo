package fastdfs.lzh.cn.touna.proto.storage;

import java.io.InputStream;

import fastdfs.lzh.cn.touna.proto.AbstractFdfsCommand;
import fastdfs.lzh.cn.touna.proto.FdfsResponse;
import fastdfs.lzh.cn.touna.proto.storage.internal.StorageAppendFileRequest;

/**
 * 添加文件命令
 * 
 * @author tobato
 *
 */
public class StorageAppendFileCommand extends AbstractFdfsCommand<Void> {

    public StorageAppendFileCommand(InputStream inputStream, long fileSize, String path) {
        this.request = new StorageAppendFileRequest(inputStream, fileSize, path);
        // 输出响应
        this.response = new FdfsResponse<Void>() {
            // default response
        };
    }

}
