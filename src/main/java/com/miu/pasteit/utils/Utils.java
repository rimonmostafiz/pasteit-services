package com.miu.pasteit.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rimon Mostafiz
 * @author Samson Hailu
 */
public class Utils {
    public static UserDetails extractUserDetails(HttpServletRequest request) {
        return (UserDetails) request.getSession().getAttribute(SessionKey.USER_DETAILS);
    }

    public static String getUserNameFromRequest(HttpServletRequest request) {

        return extractUserDetails(request).getUsername();
    }

    public static String getRequestOwner() {

        String principalUser = SecurityContextHolder.getContext().getAuthentication().getName();

        return principalUser;
    }
}
