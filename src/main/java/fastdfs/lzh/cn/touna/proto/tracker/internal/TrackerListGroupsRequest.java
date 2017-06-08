package fastdfs.lzh.cn.touna.proto.tracker.internal;

import fastdfs.lzh.cn.touna.proto.CmdConstants;
import fastdfs.lzh.cn.touna.proto.FdfsRequest;
import fastdfs.lzh.cn.touna.proto.ProtoHead;

/**
 * 列出分组命令
 * 
 * @author tobato
 *
 */
public class TrackerListGroupsRequest extends FdfsRequest {

    public TrackerListGroupsRequest() {
        head = new ProtoHead(CmdConstants.TRACKER_PROTO_CMD_SERVER_LIST_GROUP);
    }
}
