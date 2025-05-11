package com.api.idsa.domain.academic.controller;

import com.api.idsa.common.response.ApiResponse;
import com.api.idsa.common.response.PageInfo;
import com.api.idsa.domain.academic.dto.request.GroupRequest;
import com.api.idsa.domain.academic.dto.response.GroupResponse;
import com.api.idsa.domain.academic.service.IGroupService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/groups")
public class GroupController {

    @Autowired
    IGroupService groupService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<GroupResponse>>> getAllGroups(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "100") int size
    ) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<GroupResponse> groupPage = groupService.getAllGroup(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse<List<GroupResponse>>(
                HttpStatus.OK,
                "Groups retrieved successfully",
                groupPage.getContent(),
                PageInfo.fromPage(groupPage)
            )
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<GroupResponse>> createGroup(@Valid @RequestBody GroupRequest groupRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            new ApiResponse<GroupResponse>(
                HttpStatus.CREATED,
                "Group created successfully",
                groupService.createGroup(groupRequest)
            )
        );
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<ApiResponse<GroupResponse>> updateGroup(@PathVariable Long groupId, @Valid @RequestBody GroupRequest groupRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse<GroupResponse>(
                HttpStatus.OK,
                "Group updated successfully",
                groupService.updateGroup(groupId, groupRequest)
            )
        );
    }

    @DeleteMapping("/{groupId}")
    public void deleteGroup(@PathVariable Long groupId) {
        groupService.deleteGroup(groupId);
    }

}
