package fastdfs.lzh.cn.touna.proto.storage.internal;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import fastdfs.lzh.cn.touna.proto.FdfsResponse;
import fastdfs.lzh.cn.touna.proto.storage.DownloadCallback;
import fastdfs.lzh.cn.touna.proto.storage.FdfsInputStream;

/**
 * 文件下载结果
 * 
 * @author tobato
 * @param <T>
 *
 */
public class StorageDownloadResponse<T> extends FdfsResponse<T> {

    private DownloadCallback<T> callback;

    public StorageDownloadResponse(DownloadCallback<T> callback) {
        super();
        this.callback = callback;
    }

    /**
     * 解析反馈内容
     */
    @Override
    public T decodeContent(InputStream in, Charset charset) throws IOException {
        // 解析报文内容
        FdfsInputStream input = new FdfsInputStream(in, getContentLength());
        return callback.recv(input);
    }

}
