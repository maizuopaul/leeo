package fastdfs.lzh.cn.touna.proto.tracker;

import fastdfs.lzh.cn.touna.proto.AbstractFdfsCommand;
import fastdfs.lzh.cn.touna.proto.FdfsResponse;
import fastdfs.lzh.cn.touna.proto.tracker.internal.TrackerDeleteStorageRequest;

/**
 * 移除存储服务器命令
 * 
 * @author tobato
 *
 */
public class TrackerDeleteStorageCommand extends AbstractFdfsCommand<Void> {

    public TrackerDeleteStorageCommand(String groupName, String storageIpAddr) {
        super.request = new TrackerDeleteStorageRequest(groupName, storageIpAddr);
        super.response = new FdfsResponse<Void>() {
            // default response
        };
    }

}
