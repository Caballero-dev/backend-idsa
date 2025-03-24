package com.api.idsa.controller;

import com.api.idsa.dto.request.StudentRequest;
import com.api.idsa.dto.response.StudentResponse;
import com.api.idsa.exception.DuplicateResourceException;
import com.api.idsa.exception.ResourceNotFoundException;
import com.api.idsa.service.IStudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    IStudentService studentService;

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        return ResponseEntity.ok(studentService.findAll());
    }

    @GetMapping("/by-group/{groupConfigurationId}")
    public ResponseEntity<List<StudentResponse>> getStudentsByGroupConfigurationId(@PathVariable Long groupConfigurationId) {
        return ResponseEntity.ok(studentService.findByGroupConfigurationId(groupConfigurationId));
    }

    @PostMapping("/by-group/{groupConfigurationId}")
    public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody StudentRequest studentRequest, @PathVariable Long groupConfigurationId) throws DuplicateResourceException, ResourceNotFoundException {
        return ResponseEntity.ok(studentService.createStudent(studentRequest, groupConfigurationId));
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<StudentResponse> updateStudent(@PathVariable Long studentId, @Valid @RequestBody StudentRequest studentRequest) throws DuplicateResourceException, ResourceNotFoundException {
        return ResponseEntity.ok(studentService.updateStudent(studentId, studentRequest));
    }

    @DeleteMapping("/{studentId}")
    public void deleteStudent(@PathVariable Long studentId) throws ResourceNotFoundException {
        studentService.deleteStudent(studentId);
    }

}
