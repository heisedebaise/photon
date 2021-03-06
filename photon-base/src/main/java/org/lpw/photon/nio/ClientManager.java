package org.lpw.photon.nio;

/**
 * 客户端管理器。
 */
public interface ClientManager {
    /**
     * 获取一个新的客户端连接实例。
     *
     * @return 新的客户端连接实例。
     */
    Client get();

    /**
     * 关闭客户端连接。
     */
    void close();
}
