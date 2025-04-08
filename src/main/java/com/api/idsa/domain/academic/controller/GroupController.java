package com.api.idsa.domain.academic.controller;

import com.api.idsa.common.exception.DuplicateResourceException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.academic.dto.request.GroupRequest;
import com.api.idsa.domain.academic.dto.response.GroupResponse;
import com.api.idsa.domain.academic.service.IGroupService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private IGroupService groupService;

    @GetMapping
    public ResponseEntity<List<GroupResponse>> getAllGroups() {
        return ResponseEntity.ok(groupService.findAll());
    }

    @PostMapping
    public ResponseEntity<GroupResponse> createGroup(@Valid @RequestBody GroupRequest groupRequest) throws DuplicateResourceException {
        return ResponseEntity.ok(groupService.createGroup(groupRequest));
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<GroupResponse> updateGroup(@PathVariable Long groupId, @Valid @RequestBody GroupRequest groupRequest) throws ResourceNotFoundException, DuplicateResourceException {
        return ResponseEntity.ok(groupService.updateGroup(groupId, groupRequest));
    }

    @DeleteMapping("/{groupId}")
    public void deleteGroup(@PathVariable Long groupId) throws ResourceNotFoundException {
        groupService.deleteGroup(groupId);
    }

}
