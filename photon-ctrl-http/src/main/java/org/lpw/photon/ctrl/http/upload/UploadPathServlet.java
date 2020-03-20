package org.lpw.photon.ctrl.http.upload;

import org.lpw.photon.bean.BeanFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lpw
 */
@MultipartConfig
@WebServlet(name = "UploadPathServlet", urlPatterns = {UploadHelper.UPLOAD_PATH})
public class UploadPathServlet extends HttpServlet {
    private static final long serialVersionUID = -3109465051063104929L;

    private UploadHelper uploadHelper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        uploadHelper = BeanFactory.getBean(UploadHelper.class);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        uploadHelper.upload(request, response, UploadHelper.UPLOAD_PATH);
    }
}
