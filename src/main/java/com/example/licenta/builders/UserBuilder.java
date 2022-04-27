package com.example.licenta.builders;

import com.example.licenta.dtos.SignUpRequest;
import com.example.licenta.entities.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserBuilder {


    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    public User generateUser(SignUpRequest signUpRequest){
        return User.builder()
                .displayName(signUpRequest.getDisplayName())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .provider(signUpRequest.getSocialProvider().getProviderType())
                .enabled(true)
                .providerUserId(signUpRequest.getProviderUserId())
                .build();
    }
}
