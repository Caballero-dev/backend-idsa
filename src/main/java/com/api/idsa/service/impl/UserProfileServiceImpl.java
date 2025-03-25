package com.api.idsa.service.impl;

import com.api.idsa.dto.request.UpdatePasswordRequest;
import com.api.idsa.dto.response.UserProfileResponse;
import com.api.idsa.exception.IncorrectPasswordException;
import com.api.idsa.exception.ResourceNotFoundException;
import com.api.idsa.mapper.IUserProfileMapper;
import com.api.idsa.model.UserEntity;
import com.api.idsa.repository.IUserRepository;
import com.api.idsa.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IUserProfileMapper userProfileMapper;

    @Override
    public UserProfileResponse getUserProfileByEmail(String email) throws ResourceNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        return userProfileMapper.toUserProfileResponse(user);
    }

    @Override
    public void updatePassword(UpdatePasswordRequest request) throws ResourceNotFoundException {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + request.getEmail()));

        if (!user.getPassword().equals(request.getCurrentPassword())) {
            throw new IncorrectPasswordException("Current password is incorrect");
        }

        user.setPassword(request.getNewPassword());
        userRepository.save(user);
    }
}
