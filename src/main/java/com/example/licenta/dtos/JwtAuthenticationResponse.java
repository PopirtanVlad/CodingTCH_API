package com.example.licenta.dtos;

import lombok.Value;

@Value
public class JwtAuthenticationResponse {

    String accessToken;
    UserInfo userInfo;
}
