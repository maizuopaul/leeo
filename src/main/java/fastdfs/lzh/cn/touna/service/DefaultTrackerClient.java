package fastdfs.lzh.cn.touna.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import fastdfs.lzh.cn.touna.conn.TrackerConnectionManager;
import fastdfs.lzh.cn.touna.domain.GroupState;
import fastdfs.lzh.cn.touna.domain.StorageNode;
import fastdfs.lzh.cn.touna.domain.StorageNodeInfo;
import fastdfs.lzh.cn.touna.domain.StorageState;
import fastdfs.lzh.cn.touna.proto.tracker.TrackerDeleteStorageCommand;
import fastdfs.lzh.cn.touna.proto.tracker.TrackerGetFetchStorageCommand;
import fastdfs.lzh.cn.touna.proto.tracker.TrackerGetStoreStorageCommand;
import fastdfs.lzh.cn.touna.proto.tracker.TrackerListGroupsCommand;
import fastdfs.lzh.cn.touna.proto.tracker.TrackerListStoragesCommand;

/**
 * 目录服务客户端默认实现
 * 
 * @author tobato
 *
 */
public class DefaultTrackerClient implements TrackerClient {

    private TrackerConnectionManager trackerConnectionManager;

    public DefaultTrackerClient(TrackerConnectionManager trackerConnectionManager) {
      super();
      this.trackerConnectionManager = trackerConnectionManager;
    }

    /**
     * 获取存储节点
     */
    @Override
    public StorageNode getStoreStorage() {
        TrackerGetStoreStorageCommand command = new TrackerGetStoreStorageCommand();
        return trackerConnectionManager.executeFdfsTrackerCmd(command);
    }

    /**
     * 按组获取存储节点
     */
    @Override
    public StorageNode getStoreStorage(String groupName) {
        TrackerGetStoreStorageCommand command;
        if (StringUtils.isBlank(groupName)) {
            command = new TrackerGetStoreStorageCommand();
        } else {
            command = new TrackerGetStoreStorageCommand(groupName);
        }

        return trackerConnectionManager.executeFdfsTrackerCmd(command);
    }

    /**
     * 获取源服务器
     */
    @Override
    public StorageNodeInfo getFetchStorage(String groupName, String filename) {
        TrackerGetFetchStorageCommand command = new TrackerGetFetchStorageCommand(groupName, filename, false);
        return trackerConnectionManager.executeFdfsTrackerCmd(command);
    }

    /**
     * 获取更新服务器
     */
    @Override
    public StorageNodeInfo getUpdateStorage(String groupName, String filename) {
        TrackerGetFetchStorageCommand command = new TrackerGetFetchStorageCommand(groupName, filename, true);
        return trackerConnectionManager.executeFdfsTrackerCmd(command);
    }

    /**
     * 列出组
     */
    @Override
    public List<GroupState> listGroups() {
        TrackerListGroupsCommand command = new TrackerListGroupsCommand();
        return trackerConnectionManager.executeFdfsTrackerCmd(command);
    }

    /**
     * 按组列出存储状态
     */
    @Override
    public List<StorageState> listStorages(String groupName) {
        TrackerListStoragesCommand command = new TrackerListStoragesCommand(groupName);
        return trackerConnectionManager.executeFdfsTrackerCmd(command);
    }

    /**
     * 按ip列出存储状态
     */
    @Override
    public List<StorageState> listStorages(String groupName, String storageIpAddr) {
        TrackerListStoragesCommand command = new TrackerListStoragesCommand(groupName, storageIpAddr);
        return trackerConnectionManager.executeFdfsTrackerCmd(command);
    }

    /**
     * 删除存储节点
     */
    @Override
    public void deleteStorage(String groupName, String storageIpAddr) {
        TrackerDeleteStorageCommand command = new TrackerDeleteStorageCommand(groupName, storageIpAddr);
        trackerConnectionManager.executeFdfsTrackerCmd(command);
    }

}
