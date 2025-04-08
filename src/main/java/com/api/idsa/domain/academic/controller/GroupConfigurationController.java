package com.api.idsa.domain.academic.controller;

import com.api.idsa.common.exception.DuplicateResourceException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.academic.dto.request.GroupConfigurationRequest;
import com.api.idsa.domain.academic.dto.response.GroupConfigurationResponse;
import com.api.idsa.domain.academic.service.IGroupConfigurationService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group-configuration")
public class GroupConfigurationController {

    @Autowired
    IGroupConfigurationService groupConfigurationService;

    @GetMapping
    public ResponseEntity<List<GroupConfigurationResponse>> getAllGroupConfigurations() {
        return ResponseEntity.ok(groupConfigurationService.findAll());
    }

    @PostMapping
    public ResponseEntity<GroupConfigurationResponse> createGroupConfiguration(@Valid @RequestBody GroupConfigurationRequest groupConfigurationRequest) throws DuplicateResourceException {
        return ResponseEntity.ok(groupConfigurationService.createGroupConfiguration(groupConfigurationRequest));
    }

    @PutMapping("/{groupConfigurationId}")
    public ResponseEntity<GroupConfigurationResponse> updateGroupConfiguration(
            @PathVariable Long groupConfigurationId,
            @Valid @RequestBody GroupConfigurationRequest groupConfigurationRequest) throws ResourceNotFoundException, DuplicateResourceException {
        return ResponseEntity.ok(groupConfigurationService.updateGroupConfiguration(groupConfigurationId, groupConfigurationRequest));
    }

    @DeleteMapping("/{groupConfigurationId}")
    public void deleteGroupConfiguration(@PathVariable Long groupConfigurationId) throws ResourceNotFoundException {
        groupConfigurationService.deleteGroupConfiguration(groupConfigurationId);
    }

}
