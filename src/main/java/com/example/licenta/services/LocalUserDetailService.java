package com.example.licenta.services;

import com.example.licenta.dtos.user.security.LocalUser;
import com.example.licenta.exceptions.ResourceNotFoundException;
import com.example.licenta.services.interfaces.IUserService;
import com.example.licenta.utils.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.licenta.entities.User;

@Service
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

    @Transactional
    public LocalUser loadUserById(Long id) {
        User user = userService.findUserById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return createLocalUser(user);
    }


    private LocalUser createLocalUser(User user){
        return new LocalUser(user.getId(), user.getPassword(), user.getEnabled(), true, true, true, GeneralUtils.buildSimpleGrantedAuthorities(user.getRoles()), user);
    }
}
