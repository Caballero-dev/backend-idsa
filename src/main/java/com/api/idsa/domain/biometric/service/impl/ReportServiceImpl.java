package com.api.idsa.domain.biometric.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.idsa.domain.biometric.dto.response.ReportResponse;
import com.api.idsa.domain.biometric.mapper.IReportMapper;
import com.api.idsa.domain.biometric.model.BiometricDataEntity;
import com.api.idsa.domain.biometric.model.ReportEntity;
import com.api.idsa.domain.biometric.repository.IBiometricDataRepository;
import com.api.idsa.domain.biometric.repository.IReportRepository;
import com.api.idsa.domain.biometric.service.IReportService;
import com.api.idsa.domain.personnel.model.StudentEntity;
import com.api.idsa.infrastructure.fileStorage.service.IFileStorageService;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class ReportServiceImpl implements IReportService {

    private final int RECORDS_THRESHOLD = 10;

    @Autowired
    private IReportRepository reportRepository;

    @Autowired
    private IBiometricDataRepository biometricDataRepository;

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
    
    @Override
    @Transactional
    public void createReport(StudentEntity studentEntity, ZonedDateTime endDate) {

        // Realizar consulta par la fecha del ultimo reporte generado con ese estudiante
        ReportEntity lastReport = reportRepository.findFirstByStudent_StudentIdOrderByCreatedAtDesc(studentEntity.getStudentId()).orElse(null);
        ZonedDateTime startDate = lastReport != null ? lastReport.getCreatedAt() : null;

        System.out.println("Se consulta el ultimo reporte generado: " + startDate);

        Long recordCount = startDate != null ?
                biometricDataRepository.countByStudent_StudentIdAndCreatedAtBetween(studentEntity.getStudentId(), startDate, endDate) :
                biometricDataRepository.countByStudent_StudentId(studentEntity.getStudentId());

        System.out.println("Se consulta el conteo de registros: " + recordCount);
        
        if (recordCount >= RECORDS_THRESHOLD) {
            // Si el conteo de registros es mayor o igual al umbral, se genera el reporte

            List<BiometricDataEntity> biometricDataList = startDate != null ?
                    biometricDataRepository.findByStudent_StudentIdAndCreatedAtBetween(studentEntity.getStudentId(), startDate, endDate) :
                    biometricDataRepository.findByStudent_StudentId(studentEntity.getStudentId());

            System.out.println("Se genera el reporte: " + biometricDataList.size());

            ReportEntity reportEntity = new ReportEntity();
            reportEntity.setStudent(studentEntity);
            // TODO: Esto es una simulación de respuesta del modelo de deep learning
            reportEntity.setTemperature(
                calculateAverage(biometricDataList.stream().map(BiometricDataEntity::getTemperature).toList())
            );
            reportEntity.setHeartRate(
                calculateAverage(biometricDataList.stream().map(BiometricDataEntity::getHeartRate).toList())
            );
            reportEntity.setSystolicBloodPressure(
                calculateAverage(biometricDataList.stream().map(BiometricDataEntity::getSystolicBloodPressure).toList())
            );
            reportEntity.setDiastolicBloodPressure(
                calculateAverage(biometricDataList.stream().map(BiometricDataEntity::getDiastolicBloodPressure).toList())
            );
            reportEntity.setPupilDilationRight(new BigDecimal(2.45));
            reportEntity.setPupilDilationLeft(new BigDecimal(2.47));
            reportEntity.setPredictionResult(new BigDecimal(45.5));
            // TODO: Esto es una simulación de respuesta del modelo de deep learning
            reportEntity.setCreatedAt(ZonedDateTime.now());
            reportEntity.setBiometricData(biometricDataList);

            reportRepository.save(reportEntity);

        }


    }
    
    private List<String> generateImageUrl(List<String> fileNames) {
        return fileNames.stream()
                .map(fileName -> fileStorageService.generateImageUrl(fileName))
                .toList();
    }

    private BigDecimal calculateAverage(List<BigDecimal> values) {
        return values.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(values.size()));
    }

}
