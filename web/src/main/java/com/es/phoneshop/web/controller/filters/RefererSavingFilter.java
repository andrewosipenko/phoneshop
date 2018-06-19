package com.es.phoneshop.web.controller.filters;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RefererSavingFilter extends GenericFilterBean {

    public final static String REFERER_URL_KEY = "RefererSavingFilter.referer";

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        if(requestCache.getRequest(req, res) == null){
            saveReferer(req);
        }
        filterChain.doFilter(req, res);
    }

    private void saveReferer(HttpServletRequest req){
        String referer = req.getHeader("Referer");
        if(referer != null){
            HttpSession session = req.getSession();
            if(session != null){
                session.setAttribute(REFERER_URL_KEY, referer);
            }
        }
    }
}
