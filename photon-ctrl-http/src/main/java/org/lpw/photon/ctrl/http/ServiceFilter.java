package org.lpw.photon.ctrl.http;

import org.lpw.photon.bean.BeanFactory;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServiceFilter implements Filter {
    private ServiceHelper serviceHelper;

    @Override
    public void init(FilterConfig config) throws ServletException {
        serviceHelper = BeanFactory.getBean(ServiceHelper.class);
        serviceHelper.setPath(config.getServletContext().getRealPath(""), config.getServletContext().getContextPath());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!serviceHelper.service((HttpServletRequest) request, (HttpServletResponse) response))
            chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
