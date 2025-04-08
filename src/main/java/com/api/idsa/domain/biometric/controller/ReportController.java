package com.api.idsa.domain.biometric.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.idsa.domain.biometric.dto.response.ReportResponse;
import com.api.idsa.domain.biometric.service.IReportService;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    IReportService reportService;

    @GetMapping
    public ResponseEntity<List<ReportResponse>> getAllReports() {
        return ResponseEntity.ok(reportService.findAll());
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ReportResponse>> getReportsByStudentId(@PathVariable Long studentId) {
        return ResponseEntity.ok(reportService.getReportsByStudentId(studentId));
    }

}
