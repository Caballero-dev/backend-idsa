package com.api.idsa.controller;

import com.api.idsa.dto.request.UpdateUserRequest;
import com.api.idsa.dto.request.UserRequest;
import com.api.idsa.dto.response.UserResponse;
import com.api.idsa.exception.DuplicateResourceException;
import com.api.idsa.exception.ResourceNotFoundException;
import com.api.idsa.exception.UserRoleCreationDeniedException;
import com.api.idsa.service.IUserService;
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
        return ResponseEntity.ok(userService.findAllActiveExceptAdmin());
    }

    @GetMapping("/inactive")
    public ResponseEntity<List<UserResponse>> getInactiveUsers() {
        return ResponseEntity.ok(userService.findAllInactiveExceptAdmin());
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) throws UserRoleCreationDeniedException, DuplicateResourceException, ResourceNotFoundException {
        return ResponseEntity.ok(userService.createUser(userRequest));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long userId, @RequestParam boolean isUpdatePassword, @Valid @RequestBody UpdateUserRequest updateUserRequest) throws ResourceNotFoundException, DuplicateResourceException, UserRoleCreationDeniedException {
        return ResponseEntity.ok(userService.updateUser(userId, isUpdatePassword, updateUserRequest));
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) throws ResourceNotFoundException {
        userService.deleteUser(userId);
    }

}
