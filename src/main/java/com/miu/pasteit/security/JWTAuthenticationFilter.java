package com.miu.pasteit.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miu.pasteit.model.common.RestResponse;
import com.miu.pasteit.model.entity.db.sql.User;
import com.miu.pasteit.model.response.AuthResponse;
import com.miu.pasteit.service.user.SessionService;
import com.miu.pasteit.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import static com.miu.pasteit.security.SecurityUtils.getUserNamePasswordAuthenticationToken;

/**
 * @author Samson Hailu
 * @author Rimon Mostafiz
 * @author Abdi Wako Jilo
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
                .withClaim(SecurityUtils.ROLE, getAuthority((org.springframework.security.core.userdetails.User) auth.getPrincipal()))
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityUtils.EXPIRE_DURATION))
                .sign(Algorithm.HMAC512(SecurityUtils.SECRET_KEY.getBytes()));

        AuthResponse authResponse = new AuthResponse(auth.getName(), token);
        RestResponse<AuthResponse> restResponse = ResponseUtils.buildSuccessRestResponse(HttpStatus.OK, authResponse);
        ResponseUtils.createCustomResponse(res, restResponse);
        res.addHeader(SecurityUtils.AUTHORIZATION_HEADER, SecurityUtils.TOKEN_PREFIX + token);
        sessionService.addLoggedUser(auth.getName());
    }

    private String getAuthority(org.springframework.security.core.userdetails.User principal) {
        Optional<GrantedAuthority> any = principal.getAuthorities().stream().findAny();
        if (any.isPresent()) {
            return any.get().getAuthority();
        }
        return "";
    }
}
