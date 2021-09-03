package com.miu.pasteit.security;

/**
 *
 * @Author Samson Hailu
 */

public class SecurityConstants {
    public static final String SECRET = "SECRET_KEY";
    public static final long EXPIRATION_TIME = 900000L;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/v1/api/user/create";
    public static final String LOGIN_URL = "/v1/api/user/login";
    public static final String HOME_URL = "/v1/home";

    public SecurityConstants() {
    }
}
