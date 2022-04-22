package com.example.licenta.services;

import com.example.licenta.dtos.LocalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.example.licenta.entities.User;

public class LocalUserDetailService implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Override
    @Transactional
    public LocalUser loadUserByUsername(final String email) throws UsernameNotFoundException {
        User user = userService.findUserByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("User " + email + " was not found in the database");
        }
        return createLocalUser(user);
    }


    private LocalUser createLocalUser(User user){
        return new LocalUser(user.getEmail(), user.getPassword(), )
    }
}
