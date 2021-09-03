package com.miu.pasteit.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @author Samson Hailu
 * @author Rimon Mostafiz
 */

@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    @Value("${jwt.secret.key}")
    private String jwtSecretKey;

    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(SecurityUtils.AUTHORIZATION_HEADER);
        if (header != null && header.startsWith(SecurityUtils.TOKEN_PREFIX)) {
            UsernamePasswordAuthenticationToken authentication = this.getAuthentication(req);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityUtils.AUTHORIZATION_HEADER);
        return Optional.ofNullable(token)
                .map(this::validateAndExtractSubjectFromToken)
                .map(user -> SecurityUtils.getUserNamePasswordAuthenticationToken(user, null))
                .orElse(null);
    }

    private String validateAndExtractSubjectFromToken(String token) {
        return JWT.require(Algorithm.HMAC512(jwtSecretKey.getBytes()))
                .build()
                .verify(token.replace(SecurityUtils.TOKEN_PREFIX, ""))
                .getSubject();
    }
}
