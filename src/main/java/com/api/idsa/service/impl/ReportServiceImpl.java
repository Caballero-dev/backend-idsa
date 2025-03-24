package com.api.idsa.service.impl;

import com.api.idsa.dto.response.ReportResponse;
import com.api.idsa.mapper.IReportMapper;
import com.api.idsa.model.ReportEntity;
import com.api.idsa.repository.IReportRepository;
import com.api.idsa.service.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
