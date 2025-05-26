package com.api.idsa.domain.academic.controller;

import com.api.idsa.common.response.ApiResponse;
import com.api.idsa.common.response.PageInfo;
import com.api.idsa.domain.academic.dto.request.GradeRequest;
import com.api.idsa.domain.academic.dto.response.GradeResponse;
import com.api.idsa.domain.academic.service.IGradeService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/grades")
public class GradeController {

    @Autowired
    IGradeService gradeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<GradeResponse>>> getAllGrades(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "100") int size
    ) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<GradeResponse> gradePage = gradeService.getAllGrade(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse<List<GradeResponse>>(
                HttpStatus.OK,
                "Grades retrieved successfully",
                gradePage.getContent(),
                PageInfo.fromPage(gradePage)
            )
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<GradeResponse>> createGrade(@Valid @RequestBody GradeRequest gradeRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            new ApiResponse<GradeResponse>(
                HttpStatus.CREATED,
                "Grade created successfully",
                gradeService.createGrade(gradeRequest)
            )
        );
    }

    @PutMapping("/{gradeId}")
    public ResponseEntity<ApiResponse<GradeResponse>> updateGrade(@PathVariable Long gradeId, @Valid @RequestBody GradeRequest gradeRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse<GradeResponse>(
                HttpStatus.OK,
                "Grade updated successfully",
                gradeService.updateGrade(gradeId, gradeRequest)
            )
        );
    }

    @DeleteMapping("/{gradeId}")
    public void deleteGrade(@PathVariable Long gradeId) {
        gradeService.deleteGrade(gradeId);
    }

}
