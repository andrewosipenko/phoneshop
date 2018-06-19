package com.es.phoneshop.web.controller.filters;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RedirectAwareAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if(savedRequest == null){
            redirectWithReferer(request, response, authentication);
            return ;
        }

        String targetUrlParameter = getTargetUrlParameter();
        if (isAlwaysUseDefaultTargetUrl()
                || (targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
            requestCache.removeRequest(request, response);
            super.onAuthenticationSuccess(request, response, authentication);

            return;
        }

        clearAuthenticationAttributes(request);

        String targetUrl = savedRequest.getRedirectUrl();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);

        super.onAuthenticationSuccess(request, response, authentication);
    }

    private void redirectWithReferer(HttpServletRequest req, HttpServletResponse res,
                                     Authentication auth) throws IOException, ServletException{
        HttpSession session = req.getSession();
        if(session != null){
            String refererUrl = (String)session.getAttribute(RefererSavingFilter.REFERER_URL_KEY);
            if(refererUrl != null){
                session.removeAttribute(RefererSavingFilter.REFERER_URL_KEY);
                getRedirectStrategy().sendRedirect(req, res, refererUrl);
                return;
            }
        }

        super.onAuthenticationSuccess(req, res, auth);
    }
}
