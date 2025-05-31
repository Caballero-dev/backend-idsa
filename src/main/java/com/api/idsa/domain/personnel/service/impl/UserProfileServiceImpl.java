package com.api.idsa.domain.personnel.service.impl;

import com.api.idsa.common.exception.IncorrectPasswordException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.personnel.dto.request.UpdatePasswordRequest;
import com.api.idsa.domain.personnel.dto.response.UserProfileResponse;
import com.api.idsa.domain.personnel.mapper.IUserProfileMapper;
import com.api.idsa.domain.personnel.model.UserEntity;
import com.api.idsa.domain.personnel.repository.IUserRepository;
import com.api.idsa.domain.personnel.service.IUserProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserProfileServiceImpl implements IUserProfileService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IUserProfileMapper userProfileMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserProfileResponse getUserProfileByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        return userProfileMapper.toUserProfileResponse(user);
    }

    @Override
    @Transactional
    public void updatePassword(UpdatePasswordRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", request.getEmail()));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IncorrectPasswordException("The current password is incorrect <<current_password_incorrect>>");
        }

        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new IncorrectPasswordException("The new password cannot be equal to the current password <<new_password_equal_to_current>>");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

}
