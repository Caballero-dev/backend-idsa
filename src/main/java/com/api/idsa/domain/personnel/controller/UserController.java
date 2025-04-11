package com.api.idsa.domain.personnel.controller;

import com.api.idsa.common.exception.DuplicateResourceException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.common.exception.UserRoleCreationDeniedException;
import com.api.idsa.domain.personnel.dto.request.UserRequest;
import com.api.idsa.domain.personnel.dto.response.UserResponse;
import com.api.idsa.domain.personnel.service.IUserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    IUserService userService;

    @GetMapping("/active")
    public ResponseEntity<List<UserResponse>> getActiveUsers() {
        return ResponseEntity.ok(userService.getAllActiveExceptAdmin());
    }

    @GetMapping("/inactive")
    public ResponseEntity<List<UserResponse>> getInactiveUsers() {
        return ResponseEntity.ok(userService.getAllInactiveExceptAdmin());
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) throws UserRoleCreationDeniedException, DuplicateResourceException, ResourceNotFoundException {
        return ResponseEntity.ok(userService.createUser(userRequest));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long userId, @RequestParam boolean isUpdatePassword, @Valid @RequestBody UserRequest updateUserRequest) throws ResourceNotFoundException, DuplicateResourceException, UserRoleCreationDeniedException {
        return ResponseEntity.ok(userService.updateUser(userId, isUpdatePassword, updateUserRequest));
    }

    @PutMapping("/{userId}/status")
    public void updateUserStatus(@PathVariable Long userId, @RequestParam boolean isActive) throws ResourceNotFoundException {
        userService.updateUserStatus(userId, isActive);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) throws ResourceNotFoundException {
        userService.deleteUser(userId);
    }

}
