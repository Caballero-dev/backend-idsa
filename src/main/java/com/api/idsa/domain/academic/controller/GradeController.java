package com.api.idsa.domain.academic.controller;

import com.api.idsa.domain.academic.dto.request.GradeRequest;
import com.api.idsa.domain.academic.dto.response.GradeResponse;
import com.api.idsa.domain.academic.service.IGradeService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grades")
public class GradeController {

    @Autowired
    IGradeService gradeService;

    @GetMapping
    public ResponseEntity<List<GradeResponse>> getAllGrades() {
        return ResponseEntity.ok(gradeService.getAllGrade());
    }

    @PostMapping
    public ResponseEntity<GradeResponse> createGrade(@Valid @RequestBody GradeRequest gradeRequest) {
        return ResponseEntity.ok(gradeService.createGrade(gradeRequest));
    }

    @PutMapping("/{gradeId}")
    public ResponseEntity<GradeResponse> updateGrade(@PathVariable Long gradeId, @Valid @RequestBody GradeRequest gradeRequest) {
        return ResponseEntity.ok(gradeService.updateGrade(gradeId, gradeRequest));
    }

    @DeleteMapping("/{gradeId}")
    public void deleteGrade(@PathVariable Long gradeId) {
        gradeService.deleteGrade(gradeId);
    }

}
