package com.miu.pasteit.component.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Rimon Mostafiz
 */
public class InvalidJwtAuthenticationException extends AuthenticationException {
    public InvalidJwtAuthenticationException(String msg, Exception ex) {
        super(msg, ex);
    }
}
