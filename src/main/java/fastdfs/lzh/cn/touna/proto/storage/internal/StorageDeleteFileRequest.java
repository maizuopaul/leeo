package fastdfs.lzh.cn.touna.proto.storage.internal;

import fastdfs.lzh.cn.touna.proto.CmdConstants;
import fastdfs.lzh.cn.touna.proto.FdfsRequest;
import fastdfs.lzh.cn.touna.proto.OtherConstants;
import fastdfs.lzh.cn.touna.proto.ProtoHead;
import fastdfs.lzh.cn.touna.proto.mapper.DynamicFieldType;
import fastdfs.lzh.cn.touna.proto.mapper.FdfsColumn;

/**
 * 文件上传命令
 * 
 * @author tobato
 *
 */
public class StorageDeleteFileRequest extends FdfsRequest {

    /** 组名 */
    @FdfsColumn(index = 0, max = OtherConstants.FDFS_GROUP_NAME_MAX_LEN)
    private String groupName;
    /** 路径名 */
    @FdfsColumn(index = 1, dynamicField = DynamicFieldType.allRestByte)
    private String path;

    /**
     * 删除文件命令
     * 
     * @param groupName
     * @param path
     */
    public StorageDeleteFileRequest(String groupName, String path) {
        super();
        this.groupName = groupName;
        this.path = path;
        this.head = new ProtoHead(CmdConstants.STORAGE_PROTO_CMD_DELETE_FILE);
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
