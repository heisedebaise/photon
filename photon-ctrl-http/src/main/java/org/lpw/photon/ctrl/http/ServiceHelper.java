package org.lpw.photon.ctrl.http;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public interface ServiceHelper {
    /**
     * Session ID key。
     */
    String SESSION_ID = "photon-session-id";

    /**
     * 设置部署路径。
     *
     * @param real    部署绝对根路径。
     * @param context 部署相对根路径。
     */
    void setPath(String real, String context);

    /**
     * 处理请求。
     *
     * @param request  请求HttpServletRequest信息。
     * @param response 输出HttpServletResponse信息。
     * @return 处理结果。如果处理成功则返回true；否则返回false。
     * @throws IOException IO异常。
     */
    boolean service(HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 设置请求环境。
     *
     * @param request  请求HttpServletRequest信息。
     * @param response 输出HttpServletResponse信息。
     * @param uri      URI地址。
     * @return 输出流。
     * @throws IOException 如果写入流时发生IOException异常则抛出。
     */
    OutputStream setContext(HttpServletRequest request, HttpServletResponse response, String uri) throws IOException;

    /**
     * 获取服务URL地址。
     *
     * @return 服务URL地址。
     */
    String getUrl();
}
