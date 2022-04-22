package com.example.licenta.dtos;

import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class LoginRequest {

    @NotNull
    String email;

    @NotNull
    String password;

}
