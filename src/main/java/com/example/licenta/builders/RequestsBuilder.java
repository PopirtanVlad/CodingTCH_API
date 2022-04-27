package com.example.licenta.builders;

import com.example.licenta.dtos.SignUpRequest;
import com.example.licenta.security.oauth.user.OAuth2UserInfo;
import com.example.licenta.utils.GeneralUtils;

public class RequestsBuilder {

    public static SignUpRequest generateSignUpReqest(String registrationId, OAuth2UserInfo oAuth2UserInfo){
        return SignUpRequest.builder()
                .providerUserId(oAuth2UserInfo.getId())
                .displayName(oAuth2UserInfo.getName())
                .email(oAuth2UserInfo.getEmail())
                .socialProvider(GeneralUtils.toSocialProvider(registrationId))
                .password("changeit")
                .build();
    }

}
