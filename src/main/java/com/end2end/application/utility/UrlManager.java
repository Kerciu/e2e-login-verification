package com.end2end.application.utility;

import jakarta.servlet.http.HttpServletRequest;

public class UrlManager {
    public static String getApplicationUrl(HttpServletRequest request) {
        String appUrl = request.getRequestURL().toString();
        return appUrl.replace(request.getServletPath(), "");
    }
}
