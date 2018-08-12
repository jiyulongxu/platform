package com.github.platform.back.configure;


import com.githup.platform.common.constant.GlobalConstant;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 */
public class AccessFilter implements Filter {

    private static final String LOGIN_PAGE = "http://localhost:9100/platform/login.html";

    private static String[] filterUri = {"login.html", "asyncLogin"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        if (!StringUtils.isEmpty(uri)) {
            String s = uri.substring(uri.lastIndexOf("/")+1);
            if (!Arrays.asList(filterUri).contains(s)) {
                HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
                HttpSession session = httpServletRequest.getSession();
                Object sessionUser = session.getAttribute(GlobalConstant.SESSION_AUTH_LOGIN_USERNAME);


                if (sessionUser == null) {
                    resp.sendRedirect(LOGIN_PAGE);
                    return;
                }
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
