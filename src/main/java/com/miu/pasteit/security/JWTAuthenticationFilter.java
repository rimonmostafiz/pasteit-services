package com.miu.pasteit.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miu.pasteit.model.entity.db.sql.User;
import com.miu.pasteit.service.user.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.miu.pasteit.security.SecurityUtils.getUserNamePasswordAuthenticationToken;

/**
 * @author Samson Hailu
 * @author Rimon Mostafiz
 */
@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    private final SessionService sessionService;

    @Value("${jwt.secret.key}")
    private String jwtSecretKey;

    @Value("${jwt.expire.duration}")
    private Long jwtExpireDuration;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, SessionService sessionService) {
        this.authenticationManager = authenticationManager;
        this.sessionService = sessionService;
//        this.setFilterProcessesUrl("/api/services/controller/user/login");
    }

    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            User user = (new ObjectMapper()).readValue(req.getInputStream(), User.class);
            return authenticationManager.authenticate(getUserNamePasswordAuthenticationToken(user));
        } catch (IOException ioEx) {
            log.error("IOException while authentication", ioEx);
            throw new RuntimeException(ioEx);
        }
    }

    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException {

        String token = JWT.create()
                .withSubject(auth.getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpireDuration))
                .sign(Algorithm.HMAC512(jwtSecretKey.getBytes()));
        res.addHeader(SecurityUtils.AUTHORIZATION_HEADER, SecurityUtils.TOKEN_PREFIX + token);
        sessionService.addLoggedUser(auth.getName());
    }
}
