package org.lpw.photon.ctrl;

public interface CtrlHelper {
    /**
     * 设置对外服务根，仅当前线程有效。
     *
     * @param root 对外服务根。
     */
    void setRoot(String root);

    /**
     * 获取对外服务URL。
     *
     * @param uri 服务URI。
     * @return 对外服务URL，如果未配置根服务则返回null。
     */
    String url(String uri);
}
