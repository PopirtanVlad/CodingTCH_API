package com.example.licenta.builders;
import com.example.licenta.dtos.LocalUser;
import com.example.licenta.dtos.SignUpRequest;
import com.example.licenta.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import java.util.Collection;
import java.util.UUID;


public class UserBuilder {

    public static User generateUser(SignUpRequest signUpRequest){
        return User.builder()
                .userId(UUID.randomUUID())
                .displayName(signUpRequest.getDisplayName())
                .email(signUpRequest.getEmail())
                .password(signUpRequest.getPassword())
                .provider(signUpRequest.getSocialProvider().getProviderType())
                .enabled(true)
                .providerUserId(signUpRequest.getProviderUserId())
                .build();
    }
}
