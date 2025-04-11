package com.api.idsa.domain.academic.controller;

import com.api.idsa.domain.academic.dto.request.GroupConfigurationRequest;
import com.api.idsa.domain.academic.dto.response.GroupConfigurationResponse;
import com.api.idsa.domain.academic.service.IGroupConfigurationService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group-configurations")
public class GroupConfigurationController {

    @Autowired
    IGroupConfigurationService groupConfigurationService;

    @GetMapping
    public ResponseEntity<List<GroupConfigurationResponse>> getAllGroupConfigurations() {
        return ResponseEntity.ok(groupConfigurationService.getAllGroupConfiguration());
    }

    @PostMapping
    public ResponseEntity<GroupConfigurationResponse> createGroupConfiguration(@Valid @RequestBody GroupConfigurationRequest groupConfigurationRequest) {
        return ResponseEntity.ok(groupConfigurationService.createGroupConfiguration(groupConfigurationRequest));
    }

    @PutMapping("/{groupConfigurationId}")
    public ResponseEntity<GroupConfigurationResponse> updateGroupConfiguration(
            @PathVariable Long groupConfigurationId,
            @Valid @RequestBody GroupConfigurationRequest groupConfigurationRequest) {
        return ResponseEntity.ok(groupConfigurationService.updateGroupConfiguration(groupConfigurationId, groupConfigurationRequest));
    }

    @DeleteMapping("/{groupConfigurationId}")
    public void deleteGroupConfiguration(@PathVariable Long groupConfigurationId) {
        groupConfigurationService.deleteGroupConfiguration(groupConfigurationId);
    }

}
