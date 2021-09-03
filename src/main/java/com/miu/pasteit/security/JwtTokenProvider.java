package com.miu.pasteit.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

/**
 *
 * @Author Samson Hailu
 */

@Component
public class JwtTokenProvider {
    private String secretKey;
    private long validityInMilliseconds;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public JwtTokenProvider() {
    }

    @PostConstruct
    protected void init() {
        this.secretKey = Base64.getEncoder().encodeToString("SECRET_KEY".getBytes());
    }

    private Algorithm getAlgorithm() {
        Algorithm algorithm = Algorithm.HMAC256(this.secretKey);
        return algorithm;
    }

    public String createToken(String username) {
        String token = null;
        Date now = new Date();
        Date expiryTime = new Date(now.getTime() + 900000L);

        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secretKey);
            token = JWT.create().withIssuer("auth0").withSubject(username).withIssuedAt(now).withExpiresAt(expiryTime).sign(this.getAlgorithm());
        } catch (JWTCreationException var6) {
        }

        return token;
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(this.getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        String username = null;

        try {
            JWTVerifier verifier = JWT.require(this.getAlgorithm()).withIssuer(new String[]{"auth0"}).build();
            DecodedJWT jwt = verifier.verify(token);
            username = jwt.getSubject();
        } catch (JWTVerificationException var5) {
        }

        return username;
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        return bearerToken != null && bearerToken.startsWith("Bearer ") ? bearerToken.substring(7) : null;
    }

    public boolean validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(this.getAlgorithm()).withIssuer(new String[]{"auth0"}).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException var4) {
            return false;
        }
    }
}

