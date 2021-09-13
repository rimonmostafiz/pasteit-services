package com.miu.pasteit.service.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.miu.pasteit.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;

@Component
public class SessionService implements LogoutHandler {

    private final HashSet<String> loggedUsers = new HashSet<>();

    public void addLoggedUser(String token) {
        loggedUsers.add(token);
    }

    public boolean IsLoggedIn(String user) {
        return loggedUsers.contains(user);
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = request.getHeader(SecurityUtils.AUTHORIZATION_HEADER);
        String user = JWT.require(Algorithm.HMAC512(SecurityUtils.SECRET_KEY.getBytes()))
                .build()
                .verify(token.replace(SecurityUtils.TOKEN_PREFIX, ""))
                .getSubject();
        loggedUsers.remove(user);
    }
}
