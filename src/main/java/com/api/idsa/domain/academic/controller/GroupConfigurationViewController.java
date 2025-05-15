package com.api.idsa.domain.academic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.idsa.common.response.ApiResponse;
import com.api.idsa.common.response.PageInfo;
import com.api.idsa.domain.academic.dto.response.GroupConfigurationViewResponse;
import com.api.idsa.domain.academic.service.IGroupConfigurationViewService;

import java.util.List;

@RestController
@RequestMapping("/common/group-configurations-view")
public class GroupConfigurationViewController {

    @Autowired
    IGroupConfigurationViewService groupConfigurationViewService;

    // Endpoint para admin puede ver todas las configuraciones de grupos
    @GetMapping
    public ResponseEntity<ApiResponse<List<GroupConfigurationViewResponse>>> getAllGroupConfigurationView(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "100") int size
    ) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<GroupConfigurationViewResponse> groupConfigViewPage = groupConfigurationViewService.getAllGroupConfigurationView(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse<List<GroupConfigurationViewResponse>>(
                HttpStatus.OK,
                "Group configuration views retrieved successfully",
                groupConfigViewPage.getContent(),
                PageInfo.fromPage(groupConfigViewPage)
            )
        );
    }

    // Endpoint para tutor puede ver sus configuraciones de grupos por numero de empleado
    @GetMapping("/by-employee-code/{employeeCode}")
    public ResponseEntity<ApiResponse<List<GroupConfigurationViewResponse>>> getAllGroupConfigurationViewByTutor(
        @PathVariable String employeeCode,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "100") int size
    ) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<GroupConfigurationViewResponse> groupConfigViewPage = groupConfigurationViewService.getAllGroupConfigurationViewByTutor(employeeCode, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse<List<GroupConfigurationViewResponse>>(
                HttpStatus.OK,
                "Group configuration views for employee code " + employeeCode + " retrieved successfully",
                groupConfigViewPage.getContent(),
                PageInfo.fromPage(groupConfigViewPage)
            )
        );
    }

    @GetMapping("/by-tutor-email/{tutorEmail}")
    public ResponseEntity<ApiResponse<List<GroupConfigurationViewResponse>>> getAllGroupConfigurationViewByTutorEmail(
        @PathVariable String tutorEmail,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "100") int size
    ) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<GroupConfigurationViewResponse> groupConfigViewPage = groupConfigurationViewService.getAllGroupConfigurationViewByTutorEmail(tutorEmail, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse<List<GroupConfigurationViewResponse>>(
                HttpStatus.OK,
                "Group configuration views for tutor email " + tutorEmail + " retrieved successfully",
                groupConfigViewPage.getContent(),
                PageInfo.fromPage(groupConfigViewPage)
            )
        );
    }

}
