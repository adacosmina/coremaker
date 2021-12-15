package com.coremaker.exception;


import org.springframework.security.core.AuthenticationException;

public class AuthorizationHeaderMissingException extends AuthenticationException {
    public AuthorizationHeaderMissingException() {
        super("Authorization header is missing.");
    }
}
