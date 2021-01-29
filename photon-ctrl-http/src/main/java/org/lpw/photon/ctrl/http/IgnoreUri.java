package org.lpw.photon.ctrl.http;

/**
 * 忽略URI地址。
 */
public interface IgnoreUri {
    /**
     * 获取忽略的URI地址集。
     *
     * @return URI地址集。
     */
    String[] getIgnoreUris();
}
