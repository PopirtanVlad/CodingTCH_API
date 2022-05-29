package com.example.licenta.dtos.user.security;

import com.example.licenta.dtos.user.details.UserInfo;
import lombok.Value;

@Value
public class JwtAuthenticationResponse {

    String accessToken;
    UserInfo userInfo;
}
