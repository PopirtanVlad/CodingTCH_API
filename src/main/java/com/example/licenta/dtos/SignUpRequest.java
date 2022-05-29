package com.example.licenta.dtos;

import com.example.licenta.dtos.user.details.SocialProvider;
import com.example.licenta.validators.PasswordMatches;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@PasswordMatches
@Builder
@Getter
@Setter
public class SignUpRequest {

    private String providerUserId;

    @NotNull
    private String displayName;

    @NotNull
    private String email;

    private SocialProvider socialProvider;

    @Size(min = 8, max = 32, message = "{Size.userDto.password}")
    private String password;

    @NotNull
    private String matchingPassword;

}
