package com.api.idsa.domain.academic.controller;

import com.api.idsa.common.response.ApiResponse;
import com.api.idsa.common.response.PageInfo;
import com.api.idsa.domain.academic.dto.request.GroupConfigurationRequest;
import com.api.idsa.domain.academic.dto.response.GroupConfigurationResponse;
import com.api.idsa.domain.academic.service.IGroupConfigurationService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/group-configurations")
public class GroupConfigurationController {

    @Autowired
    IGroupConfigurationService groupConfigurationService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<GroupConfigurationResponse>>> getAllGroupConfigurations(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "100") int size
    ) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<GroupConfigurationResponse> groupConfigPage = groupConfigurationService.getAllGroupConfiguration(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse<List<GroupConfigurationResponse>>(
                HttpStatus.OK,
                "Group configurations retrieved successfully",
                groupConfigPage.getContent(),
                PageInfo.fromPage(groupConfigPage)
            )
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<GroupConfigurationResponse>> createGroupConfiguration(@Valid @RequestBody GroupConfigurationRequest groupConfigurationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            new ApiResponse<GroupConfigurationResponse>(
                HttpStatus.CREATED,
                "Group configuration created successfully",
                groupConfigurationService.createGroupConfiguration(groupConfigurationRequest)
            )
        );
    }

    @PutMapping("/{groupConfigurationId}")
    public ResponseEntity<ApiResponse<GroupConfigurationResponse>> updateGroupConfiguration(
            @PathVariable Long groupConfigurationId,
            @Valid @RequestBody GroupConfigurationRequest groupConfigurationRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse<GroupConfigurationResponse>(
                HttpStatus.OK,
                "Group configuration updated successfully",
                groupConfigurationService.updateGroupConfiguration(groupConfigurationId, groupConfigurationRequest)
            )
        );
    }

    @DeleteMapping("/{groupConfigurationId}")
    public void deleteGroupConfiguration(@PathVariable Long groupConfigurationId) {
        groupConfigurationService.deleteGroupConfiguration(groupConfigurationId);
    }

}
