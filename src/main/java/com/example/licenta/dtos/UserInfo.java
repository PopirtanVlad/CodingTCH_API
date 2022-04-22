package com.example.licenta.dtos;

import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
public class UserInfo {
    UUID id;
    String displayName;
    String email;
    List<String> roles;
}
