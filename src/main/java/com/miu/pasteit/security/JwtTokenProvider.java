package com.miu.pasteit.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.miu.pasteit.service.user.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

import static com.miu.pasteit.security.SecurityUtils.getUserNamePasswordAuthenticationToken;

/**
 * @author Samson Hailu
 * @author Rimon Mostafiz
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final UserDetailsServiceImpl userDetailsService;
    private String secretKey;
    @Value("${jwt.secret.key}")
    private String jwtSecretKey;
    @Value("${jwt.expire.duration}")
    private Long jwtExpireDuration;

    @PostConstruct
    protected void init() {
        this.secretKey = Base64.getEncoder().encodeToString(jwtSecretKey.getBytes());
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(this.secretKey);
    }

    public String createToken(String username) {
        try {
            Date dateNow = new Date();
            Date expiryTime = new Date(new Date().getTime() + jwtExpireDuration);
            return JWT.create()
                    .withIssuer(SecurityUtils.ISSUER)
                    .withSubject(username)
                    .withIssuedAt(dateNow)
                    .withExpiresAt(expiryTime)
                    .sign(this.getAlgorithm());
        } catch (JWTCreationException ex) {
            log.error("Error while creating jwtToken", ex);
            return null;
        }
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(this.getUsername(token));
        return getUserNamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        try {
            JWTVerifier verifier = JWT.require(this.getAlgorithm())
                    .withIssuer(SecurityUtils.ISSUER)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getSubject();
        } catch (JWTVerificationException ex) {
            log.debug("Error while verifying jwt token", ex);
            return null;
        }
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(SecurityUtils.AUTHORIZATION_HEADER);
        return bearerToken != null
                && bearerToken.startsWith(SecurityUtils.TOKEN_PREFIX)
                ? bearerToken.substring(7)
                : null;
    }

    public boolean validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(this.getAlgorithm())
                    .withIssuer(SecurityUtils.ISSUER)
                    .build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException ex) {
            log.debug("Error while validating jwt token", ex);
            return false;
        }
    }
}

