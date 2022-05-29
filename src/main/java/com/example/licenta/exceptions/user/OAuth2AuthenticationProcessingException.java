package com.example.licenta.exceptions.user;

import org.springframework.security.core.AuthenticationException;

public class OAuth2AuthenticationProcessingException extends AuthenticationException {

    public OAuth2AuthenticationProcessingException(String message, Throwable t) {
        super(message, t);
    }

    public OAuth2AuthenticationProcessingException(String msg) {
        super(msg);
    }
}
