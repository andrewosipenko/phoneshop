package com.es.phoneshop.web.controller.filters;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginPageRefererSavingFilter extends GenericFilterBean {

    public final static String REFERER_URL_KEY = "LoginPageRefererSavingFilter.referer";

    private String loginUrl = "/login";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String referer = req.getHeader("Referer");
        if(referer != null && !isLoginPageUrl(referer)){
            HttpSession session = req.getSession();
                session.setAttribute(REFERER_URL_KEY, referer);
        }

        filterChain.doFilter(req, res);
    }

    private boolean isLoginPageUrl(String referer){
        return referer.split("\\?")[0].contains(getServletContext().getContextPath()+loginUrl);
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }
}
