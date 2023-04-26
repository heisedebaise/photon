package org.lpw.photon.util;

import java.util.Map;

public class HttpBuilder {
    String url;
    Map<String, String> header;
    Map<String, String> parameter;
    String content;
    String charset;
    String host;
    int port;

    /**
     * 设置URL。
     *
     * @param url URL。
     * @return 当前实例。
     */
    public HttpBuilder url(String url) {
        this.url = url;

        return this;
    }

    /**
     * 设置请求头。
     *
     * @param header 请求头。
     * @return 当前实例。
     */
    public HttpBuilder header(Map<String, String> header) {
        this.header = header;

        return this;
    }

    /**
     * 设置请求参数。
     *
     * @param parameter 请求参数。
     * @return 当前实例。
     */
    public HttpBuilder parameter(Map<String, String> parameter) {
        this.parameter = parameter;

        return this;
    }

    /**
     * 设置请求参数体。
     *
     * @param content 请求参数体。
     * @return 当前实例。
     */
    public HttpBuilder content(String content) {
        this.content = content;

        return this;
    }

    /**
     * 设置请求编码。
     *
     * @param charset 请求编码。
     * @return 当前实例。
     */
    public HttpBuilder charset(String charset) {
        this.charset = charset;

        return this;
    }

    /**
     * 设置代理。
     *
     * @param host 代理服务器地址。
     * @param port 代理服务器端口号。
     * @return 当前实例。
     */
    public HttpBuilder proxy(String host, int port) {
        this.host = host;
        this.port = port;

        return this;
    }
}
