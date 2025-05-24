package com.api.idsa.security.service.impl;


import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.api.idsa.common.exception.UnverifiedUserException;
import com.api.idsa.domain.personnel.model.UserEntity;
import com.api.idsa.domain.personnel.repository.IUserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        if (!userEntity.getIsVerifiedEmail()) throw new LockedException("User account is not verified");
        if (!userEntity.getIsActive()) throw new DisabledException("User account is disabled");
        if (userEntity.getPassword() == null || userEntity.getPassword().isEmpty()) throw new UnverifiedUserException("The registration process is not complete", "registration_incomplete");
        
        return new User(
            userEntity.getEmail(),
            userEntity.getPassword(),
            userEntity.getIsActive(),
            true,
            true,
            userEntity.getIsVerifiedEmail(),
            Collections.singleton(new SimpleGrantedAuthority(userEntity.getRole().getRoleName()))
        );

    }

}
