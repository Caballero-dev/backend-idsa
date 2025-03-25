package com.api.idsa.controller;

import com.api.idsa.dto.request.UpdatePasswordRequest;
import com.api.idsa.dto.response.UserProfileResponse;
import com.api.idsa.exception.ResourceNotFoundException;
import com.api.idsa.service.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class UserProfileController {

    @Autowired
    UserProfileService userProfileService;

    @GetMapping("/{email}")
    public ResponseEntity<UserProfileResponse> getUserProfile(@PathVariable String email) throws ResourceNotFoundException {
        return ResponseEntity.ok(userProfileService.getUserProfileByEmail(email));
    }

    @PutMapping("/password")
    public void updatePassword(@Valid @RequestBody UpdatePasswordRequest request) throws ResourceNotFoundException {
        userProfileService.updatePassword(request);
    }
}