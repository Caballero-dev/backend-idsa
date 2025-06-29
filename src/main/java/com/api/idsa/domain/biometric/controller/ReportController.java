package com.api.idsa.domain.biometric.controller;

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
import com.api.idsa.domain.biometric.dto.response.ReportResponse;
import com.api.idsa.domain.biometric.service.IReportService;

import java.util.List;

@RestController
@RequestMapping("/api/common/reports")
public class ReportController {

    @Autowired
    IReportService reportService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReportResponse>>> getAllReports(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "100") int size
    ) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<ReportResponse> reportPage = reportService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse<List<ReportResponse>>(
                HttpStatus.OK,
                "Reports retrieved successfully",
                reportPage.getContent(),
                PageInfo.fromPage(reportPage)
            )
        );
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<ApiResponse<List<ReportResponse>>> getReportsByStudentId(
        @PathVariable Long studentId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "100") int size
    ) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<ReportResponse> reportPage = reportService.getReportsByStudentId(studentId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse<List<ReportResponse>>(
                HttpStatus.OK,
                "Reports retrieved successfully",
                reportPage.getContent(),
                PageInfo.fromPage(reportPage)
            )
        );
    }

}
