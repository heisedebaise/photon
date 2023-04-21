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

/**
 * 安全过滤器。
 */
public class SecurityFilter implements Filter {
    private SecurityHelper securityHelper;

    @Override
    public void init(FilterConfig config) throws ServletException {
        securityHelper = BeanFactory.getBean(SecurityHelper.class);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        filter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void filter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (securityHelper.isEnable(request))
            chain.doFilter(request, response);
        else
            response.sendError(404);
    }

    @Override
    public void destroy() {
    }
}
