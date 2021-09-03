package com.miu.pasteit.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miu.pasteit.model.entity.db.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @Author Samson Hailu
 */

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.setFilterProcessesUrl("/api/services/controller/user/login");
    }

    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            User creds = (User)(new ObjectMapper()).readValue(req.getInputStream(), User.class);
            return this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword(), new ArrayList()));
        } catch (IOException var4) {
            throw new RuntimeException(var4);
        }
    }

    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException {
        String token = JWT.create().withSubject(((User)auth.getPrincipal()).getUsername()).withExpiresAt(new Date(System.currentTimeMillis() + 900000L)).sign(Algorithm.HMAC512("SECRET_KEY".getBytes()));
        String var10000 = ((User)auth.getPrincipal()).getUsername();
        String body = var10000 + " " + token;
        res.getWriter().write(body);
        res.getWriter().flush();
    }
}
