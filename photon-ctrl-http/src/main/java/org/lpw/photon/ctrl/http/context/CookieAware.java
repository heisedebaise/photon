package org.lpw.photon.ctrl.http.context;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Cookie织入器。
 */
public interface CookieAware {
    /**
     * 设置Cookie环境。
     *
     * @param request  请求HttpServletRequest对象。
     * @param response 输出HttpServletResponse对象。
     */
    public void set(HttpServletRequest request, HttpServletResponse response);
}
