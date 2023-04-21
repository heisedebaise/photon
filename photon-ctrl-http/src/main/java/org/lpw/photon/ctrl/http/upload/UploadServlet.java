package org.lpw.photon.ctrl.http.upload;

import org.lpw.photon.bean.BeanFactory;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;

@MultipartConfig
@WebServlet(name = "UploadServlet", urlPatterns = {UploadService.UPLOAD})
public class UploadServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 2035601763794785803L;

    private UploadService uploadService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        uploadService = BeanFactory.getBean(UploadService.class);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        uploadService.upload(request, response, UploadService.UPLOAD);
    }
}
