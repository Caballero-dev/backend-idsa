package com.api.idsa.domain.personnel.controller;

import com.api.idsa.domain.personnel.dto.request.UpdatePasswordRequest;
import com.api.idsa.domain.personnel.dto.response.UserProfileResponse;
import com.api.idsa.domain.personnel.service.IUserProfileService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class UserProfileController {

    @Autowired
    IUserProfileService userProfileService;

    @GetMapping("/{email}")
    public ResponseEntity<UserProfileResponse> getUserProfileByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userProfileService.getUserProfileByEmail(email));
    }

    @PutMapping("/password")
    public void updatePassword(@Valid @RequestBody UpdatePasswordRequest request) {
        userProfileService.updatePassword(request);
    }
}