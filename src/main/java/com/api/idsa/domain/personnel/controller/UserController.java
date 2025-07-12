package com.api.idsa.domain.personnel.controller;

import com.api.idsa.common.response.ApiResponse;
import com.api.idsa.common.response.PageInfo;
import com.api.idsa.domain.personnel.dto.request.UserRequest;
import com.api.idsa.domain.personnel.dto.response.UserResponse;
import com.api.idsa.domain.personnel.service.IUserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/users")
public class UserController {

    @Autowired
    IUserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "100") int size,
        @RequestParam(required = false) String search
    ) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<UserResponse> userPage = userService.getAllUserExceptAdmin(pageable, search);
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse<List<UserResponse>>(
                HttpStatus.OK,
                "Users retrieved successfully",
                userPage.getContent(),
                PageInfo.fromPage(userPage)
            )
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            new ApiResponse<UserResponse>(
                HttpStatus.CREATED,
                "User created successfully",
                userService.createUser(userRequest)
            )
        );
    }

    @PutMapping(value = "/{userId}", params = "isUpdatePassword=true")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserWithPassword(
        @PathVariable UUID userId, 
        @RequestParam() boolean isUpdatePassword, 
        @Validated(UserRequest.PasswordUpdate.class) @RequestBody() UserRequest updateUserRequest) {
            return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<UserResponse>(
                    HttpStatus.OK,
                    "User updated successfully",
                    userService.updateUser(userId, isUpdatePassword, updateUserRequest)
                )
            );
    }

    @PutMapping(value = "/{userId}", params = "isUpdatePassword=false")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserWithoutPassword(
        @PathVariable UUID userId, 
        @RequestParam() boolean isUpdatePassword, 
        @Validated @RequestBody() UserRequest updateUserRequest) {
            return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<UserResponse>(
                    HttpStatus.OK,
                    "User updated successfully",
                    userService.updateUser(userId, isUpdatePassword, updateUserRequest)
                )
            );
    }

    @PutMapping("/{userId}/status")
    public void updateUserStatus(@PathVariable UUID userId, @RequestParam boolean isActive) {
        userService.updateUserStatus(userId, isActive);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
    }

}
