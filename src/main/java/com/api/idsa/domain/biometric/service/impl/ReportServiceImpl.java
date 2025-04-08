package com.api.idsa.domain.biometric.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.idsa.domain.biometric.dto.response.ReportResponse;
import com.api.idsa.domain.biometric.mapper.IReportMapper;
import com.api.idsa.domain.biometric.model.ReportEntity;
import com.api.idsa.domain.biometric.repository.IReportRepository;
import com.api.idsa.domain.biometric.service.IReportService;

import java.util.List;

@Service
public class ReportServiceImpl implements IReportService {

    @Autowired
    private IReportRepository reportRepository;

    @Autowired
    private IReportMapper reportMapper;

    @Override
    public List<ReportResponse> findAll() {
        List<ReportEntity> reports = reportRepository.findAll();
        return reportMapper.toResponseList(reports);
    }

    @Override
    public List<ReportResponse> getReportsByStudentId(Long studentId) {
        List<ReportEntity> reports = reportRepository.findByStudentStudentId(studentId);
        return reportMapper.toResponseList(reports);
    }

}
