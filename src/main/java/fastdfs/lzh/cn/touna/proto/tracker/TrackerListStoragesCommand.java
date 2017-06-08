package fastdfs.lzh.cn.touna.proto.tracker;

import java.util.List;

import fastdfs.lzh.cn.touna.domain.StorageState;
import fastdfs.lzh.cn.touna.proto.AbstractFdfsCommand;
import fastdfs.lzh.cn.touna.proto.tracker.internal.TrackerListStoragesRequest;
import fastdfs.lzh.cn.touna.proto.tracker.internal.TrackerListStoragesResponse;

/**
 * 列出组命令
 * 
 * @author tobato
 *
 */
public class TrackerListStoragesCommand extends AbstractFdfsCommand<List<StorageState>> {

    public TrackerListStoragesCommand(String groupName, String storageIpAddr) {
        super.request = new TrackerListStoragesRequest(groupName, storageIpAddr);
        super.response = new TrackerListStoragesResponse();
    }

    public TrackerListStoragesCommand(String groupName) {
        super.request = new TrackerListStoragesRequest(groupName);
        super.response = new TrackerListStoragesResponse();
    }

}
