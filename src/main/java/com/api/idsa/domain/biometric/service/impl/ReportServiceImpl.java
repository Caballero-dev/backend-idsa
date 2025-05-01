package com.api.idsa.domain.biometric.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.idsa.domain.biometric.dto.response.ReportResponse;
import com.api.idsa.domain.biometric.mapper.IReportMapper;
import com.api.idsa.domain.biometric.repository.IReportRepository;
import com.api.idsa.domain.biometric.service.IReportService;
import com.api.idsa.infrastructure.fileStorage.service.IFileStorageService;

import java.util.List;

@Service
public class ReportServiceImpl implements IReportService {

    @Autowired
    private IReportRepository reportRepository;

    @Autowired
    private IReportMapper reportMapper;

    @Autowired
    IFileStorageService fileStorageService;

    @Override
    public List<ReportResponse> findAll() {
        List<ReportResponse> reports = reportMapper.toResponseList(reportRepository.findAll()).stream()
                .map(r -> {
                    r.setImages(generateImageUrl(r.getImages()));
                    return r;
                })
                .toList();
        return reports;
    }

    @Override
    public List<ReportResponse> getReportsByStudentId(Long studentId) {
        List<ReportResponse> reports = reportMapper.toResponseList(reportRepository.findByStudentStudentId(studentId)).stream()
                .map(r -> {
                    r.setImages(generateImageUrl(r.getImages()));
                    return r;
                })
                .toList();
        return reports;
    }

    private List<String> generateImageUrl(List<String> fileNames) {
        return fileNames.stream()
                .map(fileName -> fileStorageService.generateImageUrl(fileName))
                .toList();
    }

}
