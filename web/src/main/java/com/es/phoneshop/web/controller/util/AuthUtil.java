package com.es.phoneshop.web.controller.util;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {

    private AuthUtil() {
    }

    public static boolean isAuthenticated() {
        return !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
    }
}
