package com.api.idsa.domain.personnel.controller;

import com.api.idsa.common.response.ApiResponse;
import com.api.idsa.common.response.PageInfo;
import com.api.idsa.domain.personnel.dto.request.StudentRequest;
import com.api.idsa.domain.personnel.dto.response.StudentResponse;
import com.api.idsa.domain.personnel.service.IStudentService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/common/students")
public class StudentController {

    @Autowired
    IStudentService studentService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentResponse>>> getAllStudents(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "100") int size
    ) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<StudentResponse> studentPage = studentService.getAllStudent(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse<List<StudentResponse>>(
                HttpStatus.OK,
                "Students retrieved successfully",
                studentPage.getContent(),
                PageInfo.fromPage(studentPage)
            )
        );
    }

    @GetMapping("/by-group/{groupConfigurationId}")
    public ResponseEntity<ApiResponse<List<StudentResponse>>> getStudentsByGroupConfigurationId(
        @PathVariable Long groupConfigurationId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "100") int size
    ) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<StudentResponse> studentPage = studentService.getStudentsByGroupConfigurationId(groupConfigurationId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse<List<StudentResponse>>(
                HttpStatus.OK,
                "Students for group configuration ID " + groupConfigurationId + " retrieved successfully",
                studentPage.getContent(),
                PageInfo.fromPage(studentPage)
            )
        );
    }

    @PostMapping("/by-group/{groupConfigurationId}")
    public ResponseEntity<ApiResponse<StudentResponse>> createStudent(@Valid @RequestBody StudentRequest studentRequest, @PathVariable Long groupConfigurationId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            new ApiResponse<StudentResponse>(
                HttpStatus.CREATED,
                "Student created successfully",
                studentService.createStudent(studentRequest, groupConfigurationId)
            )
        );
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<ApiResponse<StudentResponse>> updateStudent(@PathVariable Long studentId, @Valid @RequestBody StudentRequest studentRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse<StudentResponse>(
                HttpStatus.OK,
                "Student updated successfully",
                studentService.updateStudent(studentId, studentRequest)
            )
        );
    }

    @DeleteMapping("/{studentId}")
    public void deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
    }

}
