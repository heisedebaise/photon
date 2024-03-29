package org.lpw.photon.ctrl.http;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 安全过滤支持。
 */
public interface SecurityHelper {
    /**
     * 验证是否允许执行请求。
     *
     * @param request 请求参数。
     * @return 如果允许则返回true；否则返回false。
     */
    boolean isEnable(HttpServletRequest request);
}
