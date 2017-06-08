package fastdfs.lzh.cn.touna.proto.tracker;

import java.util.List;

import fastdfs.lzh.cn.touna.domain.GroupState;
import fastdfs.lzh.cn.touna.proto.AbstractFdfsCommand;
import fastdfs.lzh.cn.touna.proto.tracker.internal.TrackerListGroupsRequest;
import fastdfs.lzh.cn.touna.proto.tracker.internal.TrackerListGroupsResponse;

/**
 * 列出组命令
 * 
 * @author tobato
 *
 */
public class TrackerListGroupsCommand extends AbstractFdfsCommand<List<GroupState>> {

    public TrackerListGroupsCommand() {
        super.request = new TrackerListGroupsRequest();
        super.response = new TrackerListGroupsResponse();
    }

}
