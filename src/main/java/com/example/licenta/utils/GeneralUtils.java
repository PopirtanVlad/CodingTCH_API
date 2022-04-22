package com.example.licenta.utils;

import com.example.licenta.dtos.LocalUser;
import com.example.licenta.dtos.SocialProvider;
import com.example.licenta.dtos.UserInfo;
import com.example.licenta.entities.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.example.licenta.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GeneralUtils {

    public static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(final Set<Role> roles){
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(Role r: roles){
            authorities.add(new SimpleGrantedAuthority(r.getName()));
        }
        return authorities;
    }

    public static SocialProvider toSocialProvider(String provider){
        for (SocialProvider s: SocialProvider.values()){
            if(s.getProviderType().equals(provider)){
                return s;
            }
        }
        return SocialProvider.LOCAL;
    }

    public static UserInfo buildUserInfo(LocalUser localUser) {
        List<String> roles = localUser.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        User user = localUser.getUser();
        return new UserInfo(user.getUserId(), user.getDisplayName(), user.getEmail(), roles);
    }

}
