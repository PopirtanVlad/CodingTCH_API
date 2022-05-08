package com.example.licenta.services;
import com.example.licenta.dtos.user.security.LocalUser;
import com.example.licenta.dtos.SignUpRequest;
import com.example.licenta.entities.User;

import java.util.Map;
import java.util.Optional;

import com.example.licenta.exceptions.user.UserAlreadyExistAuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

public interface IUserService {
    User registerNewUser(SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException;

    User findUserByEmail(String email);

    Optional<User> findUserById(Long id);

    LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo);
}
