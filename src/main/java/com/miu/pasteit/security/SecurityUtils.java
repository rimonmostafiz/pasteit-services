package com.miu.pasteit.security;

import com.miu.pasteit.model.entity.db.sql.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Samson Hailu
 * @author Rimon Mostafiz
 */
public class SecurityUtils {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String SIGN_UP_URL = "/v1/user";
    public static final String GET_PASTE_URL = "/paste/{url:^[a-zA-Z0-9]+$}";
    public static final String LOGIN_URL = "/v1/api/user/login";
    public static final String LOGIN_PRECESSING_URL = "/login";
    public static final String HOME_URL = "/v1/home";
    public static final String ISSUER = "auth0";
    public static final String SECRET_KEY = "SECRET_KEY";
    public static final Long EXPIRE_DURATION = 900000L;
    public static final String corsOriginAllowedUrl = "http://localhost:3000";
    public static final String contextPath = "/**";
    public static final String ROLE = "role";
    public static final String LOGOUT_URL = "/logout";

    public static UsernamePasswordAuthenticationToken getUserNamePasswordAuthenticationToken(User user) {
        return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), Collections.emptyList());
    }

    public static UsernamePasswordAuthenticationToken getUserNamePasswordAuthenticationToken(String user) {
        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }

    public static UsernamePasswordAuthenticationToken getUserNamePasswordAuthenticationToken(String user, String credential) {
        return new UsernamePasswordAuthenticationToken(user, credential, Collections.emptyList());
    }

    public static Authentication getUserNamePasswordAuthenticationToken(UserDetails userDetails, String credential,
                                                                        Collection<? extends GrantedAuthority> authorities) {
        return new UsernamePasswordAuthenticationToken(userDetails, credential, Collections.emptyList());
    }
}
