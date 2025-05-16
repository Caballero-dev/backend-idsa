package com.api.idsa.domain.personnel.controller;

import com.api.idsa.common.response.ApiResponse;
import com.api.idsa.domain.personnel.dto.request.UpdatePasswordRequest;
import com.api.idsa.domain.personnel.dto.response.UserProfileResponse;
import com.api.idsa.domain.personnel.service.IUserProfileService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/common/profile")
public class UserProfileController {

    @Autowired
    IUserProfileService userProfileService;

    @GetMapping("/{email}")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getUserProfileByEmail(@PathVariable String email) {
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse<UserProfileResponse>(
                HttpStatus.OK,
                "User profile retrieved successfully",
                userProfileService.getUserProfileByEmail(email)
            )
        );
    }

    @PutMapping("/update-password")
    public void updatePassword(@Valid @RequestBody UpdatePasswordRequest request) {
        userProfileService.updatePassword(request);
    }
}