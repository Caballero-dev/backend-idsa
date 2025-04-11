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
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements IUserProfileService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IUserProfileMapper userProfileMapper;

    @Override
    public UserProfileResponse getUserProfileByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Get", "User", "email", email));

        return userProfileMapper.toUserProfileResponse(user);
    }

    // FIXME: implementar bien la actualización de la contraseña
    @Override
    public void updatePassword(UpdatePasswordRequest request) {

        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Update", "User", "email", request.getEmail()));

        if (!user.getPassword().equals(request.getCurrentPassword())) {
            throw new IncorrectPasswordException("Current password is incorrect");
        }

        user.setPassword(request.getNewPassword());
        userRepository.save(user);
    }
}
