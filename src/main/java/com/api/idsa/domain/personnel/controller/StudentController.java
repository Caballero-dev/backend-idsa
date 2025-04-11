package com.api.idsa.domain.personnel.controller;

import com.api.idsa.domain.personnel.dto.request.StudentRequest;
import com.api.idsa.domain.personnel.dto.response.StudentResponse;
import com.api.idsa.domain.personnel.service.IStudentService;

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
        return ResponseEntity.ok(studentService.getAllStudent());
    }

    @GetMapping("/by-group/{groupConfigurationId}")
    public ResponseEntity<List<StudentResponse>> getStudentsByGroupConfigurationId(@PathVariable Long groupConfigurationId) {
        return ResponseEntity.ok(studentService.getStudentsByGroupConfigurationId(groupConfigurationId));
    }

    @PostMapping("/by-group/{groupConfigurationId}")
    public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody StudentRequest studentRequest, @PathVariable Long groupConfigurationId) {
        return ResponseEntity.ok(studentService.createStudent(studentRequest, groupConfigurationId));
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<StudentResponse> updateStudent(@PathVariable Long studentId, @Valid @RequestBody StudentRequest studentRequest) {
        return ResponseEntity.ok(studentService.updateStudent(studentId, studentRequest));
    }

    @DeleteMapping("/{studentId}")
    public void deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
    }

}
