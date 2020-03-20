package org.lpw.photon.ctrl;

/**
 * 权限验证器。
 *
 * @author lpw
 */
public interface Permit {
    /**
     * 验证权限。
     *
     * @return 如果允许访问则返回true；否则返回false。
     */
    boolean allow();
}
