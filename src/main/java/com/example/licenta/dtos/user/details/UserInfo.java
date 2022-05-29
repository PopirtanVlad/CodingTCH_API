package com.example.licenta.dtos.user.details;

import lombok.Value;

import java.util.List;

@Value
public class UserInfo {
    Long id;
    String displayName;
    String email;
    List<String> roles;
}
