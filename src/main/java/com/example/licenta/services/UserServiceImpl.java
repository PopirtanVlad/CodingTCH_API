package com.example.licenta.services;

import com.example.licenta.builders.RequestsBuilder;
import com.example.licenta.builders.UserBuilder;
import com.example.licenta.dtos.user.security.LocalUser;
import com.example.licenta.dtos.SignUpRequest;
import com.example.licenta.dtos.user.details.SocialProvider;
import com.example.licenta.entities.Role;
import com.example.licenta.entities.User;
import com.example.licenta.exceptions.user.OAuth2AuthenticationProcessingException;
import com.example.licenta.exceptions.user.UserAlreadyExistAuthenticationException;
import com.example.licenta.security.oauth.user.OAuth2UserInfo;
import com.example.licenta.security.oauth.user.OAuth2UserInfoFactory;
import com.example.licenta.services.interfaces.IUserService;
import com.example.licenta.services.repositories.RoleRepository;
import com.example.licenta.services.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserBuilder userBuilder;


    @Override
    @Transactional(value = "transactionManager")
    public User registerNewUser(SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException {
        if (userRepository.existsByEmail(signUpRequest.getEmail())){
            throw new UserAlreadyExistAuthenticationException("User with email " + signUpRequest.getEmail() + " already exists");
        }

        User user = userBuilder.generateUser(signUpRequest);
        final HashSet<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(Role.ROLE_USER));
        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }

    @Override
    public User findUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, attributes);
        if (oAuth2UserInfo.getName().isEmpty()) {
            throw new OAuth2AuthenticationProcessingException("Name not found from OAuth2 provider");
        } else if (oAuth2UserInfo.getEmail().isEmpty()) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }
        SignUpRequest userDetails = toUserRegistrationObject(registrationId, oAuth2UserInfo);
        User user = findUserByEmail(oAuth2UserInfo.getEmail());
        if (user != null) {
            if (!user.getProvider().equals(registrationId) && !user.getProvider().equals(SocialProvider.LOCAL.getProviderType())) {
                throw new OAuth2AuthenticationProcessingException(
                        "Looks like you're signed up with " + user.getProvider() + " account. Please use your " + user.getProvider() + " account to login.");
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(userDetails);
        }

        return LocalUser.create(user, attributes, idToken, userInfo);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setDisplayName(oAuth2UserInfo.getName());
        return userRepository.save(existingUser);
    }


    private SignUpRequest toUserRegistrationObject(String registrationId, OAuth2UserInfo oAuth2UserInfo) {
        return RequestsBuilder.generateSignUpReqest(registrationId, oAuth2UserInfo);
    }


}
