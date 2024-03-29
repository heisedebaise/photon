package org.lpw.photon.ctrl.http.context;

import jakarta.servlet.http.HttpServletResponse;
import org.lpw.photon.bean.BeanFactory;
import org.lpw.photon.ctrl.context.ResponseAdapter;
import org.lpw.photon.util.Logger;

import java.io.IOException;
import java.io.OutputStream;

public class ResponseAdapterImpl implements ResponseAdapter {
    private final String servletContextPath;
    private final HttpServletResponse response;
    private final OutputStream output;

    public ResponseAdapterImpl(String servletContextPath, HttpServletResponse response, OutputStream output) {
        this.servletContextPath = servletContextPath;
        this.response = response;
        this.output = output;
    }

    @Override
    public void setContentType(String contentType) {
        response.setContentType(contentType);
    }

    @Override
    public void setHeader(String name, String value) {
        response.setHeader(name, value);
    }

    @Override
    public OutputStream getOutputStream() {
        return output;
    }

    @Override
    public void send() throws IOException {
        output.close();
    }

    @Override
    public void redirectTo(String url) {
        try {
            response.sendRedirect(url.indexOf('/') == 0 ? (servletContextPath + url) : url);
            output.close();
        } catch (IOException e) {
            BeanFactory.getBean(Logger.class).warn(e, "跳转到远程URL[{}]地址时发生异常！", url);
        }
    }

    @Override
    public void sendError(int code) {
        try {
            response.sendError(code);
        } catch (IOException e) {
            BeanFactory.getBean(Logger.class).warn(e, "发送错误码[{}]时发生异常！", code);
        }
    }
}
