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

/**
 *
 * Based on {@link org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler}
 *
 **/
public class RedirectAwareAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        String refererUrl = popRefererFromSession(request);

        if(savedRequest == null){
            if(refererUrl == null){
                super.onAuthenticationSuccess(request, response, authentication);
            } else {
                getRedirectStrategy().sendRedirect(request, response, refererUrl);
            }

            return;
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
    }

    private String popRefererFromSession(HttpServletRequest req){
        HttpSession session = req.getSession();
        if(session != null){
            String refererUrl = (String)session.getAttribute(LoginPageRefererSavingFilter.REFERER_URL_KEY);
            session.removeAttribute(LoginPageRefererSavingFilter.REFERER_URL_KEY);
            return refererUrl;
        }
        return null;
    }
}
