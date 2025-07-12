package com.api.idsa.domain.biometric.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.biometric.dto.response.ReportResponse;
import com.api.idsa.domain.biometric.dto.response.ReportSummaryResponse;
import com.api.idsa.domain.biometric.enums.PredictionLevel;
import com.api.idsa.domain.biometric.mapper.IReportMapper;
import com.api.idsa.domain.biometric.model.BiometricDataEntity;
import com.api.idsa.domain.biometric.model.ReportEntity;
import com.api.idsa.domain.biometric.repository.IBiometricDataRepository;
import com.api.idsa.domain.biometric.repository.IReportRepository;
import com.api.idsa.domain.biometric.service.IReportService;
import com.api.idsa.domain.personnel.model.StudentEntity;
import com.api.idsa.domain.personnel.repository.IStudentRepository;
import com.api.idsa.infrastructure.fileStorage.service.IFileStorageService;
import com.api.idsa.infrastructure.model.dto.response.ModelPredictionResponse;
import com.api.idsa.infrastructure.model.service.ModelPredictionService;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ReportServiceImpl implements IReportService {

    @Value("${report.generation.threshold}")
    private int biometricDataThreshold;

    @Autowired
    private IReportRepository reportRepository;

    @Autowired
    private IBiometricDataRepository biometricDataRepository;

    @Autowired
    private IStudentRepository studentRepository;

    @Autowired
    private IReportMapper reportMapper;

    @Autowired
    IFileStorageService fileStorageService;

    @Autowired
    private ModelPredictionService modelPredictionService;

    @Override
    public Page<ReportResponse> findAll(Pageable pageable) {
        Page<ReportEntity> reportPage = reportRepository.findAll(pageable);
        return reportPage.map(this::mapToResponseWithImages);
    }

    @Override
    public Page<ReportResponse> getReportsByStudentId(UUID studentId, Pageable pageable) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student", "id", studentId);
        }

        Page<ReportEntity> reportPage = reportRepository.findByStudentStudentIdOrderByCreatedAtDesc(studentId, pageable);
        return reportPage.map(this::mapToResponseWithImages);
    }

    @Override
    public ReportSummaryResponse getReportSummary() {
        Integer totalStudents = studentRepository.countStudents();
        Integer studentsWithReports = reportRepository.countStudentsWithReports();
        Integer studentsWithoutReports = totalStudents - studentsWithReports;
        Integer studentsWithLowProbability = reportRepository.countStudentsByPredictionResult(PredictionLevel.BAJA.name());
        Integer studentsWithMediumProbability = reportRepository.countStudentsByPredictionResult(PredictionLevel.MEDIA.name());
        Integer studentsWithHighProbability = reportRepository.countStudentsByPredictionResult(PredictionLevel.ALTA.name());
        return ReportSummaryResponse.builder()
                .totalStudents(totalStudents)
                .studentsWithReports(studentsWithReports)
                .studentsWithoutReports(studentsWithoutReports)
                .studentsWithLowProbability(studentsWithLowProbability)
                .studentsWithMediumProbability(studentsWithMediumProbability)
                .studentsWithHighProbability(studentsWithHighProbability)
                .build();
    }
    
    @Override
    @Transactional
    public void createReport(StudentEntity studentEntity, ZonedDateTime endDate) {

        // Realizar consulta par la fecha del ultimo reporte generado con ese estudiante
        ReportEntity lastReport = reportRepository.findFirstByStudent_StudentIdOrderByCreatedAtDesc(studentEntity.getStudentId()).orElse(null);
        ZonedDateTime startDate = lastReport != null ? lastReport.getCreatedAt() : null;

        Long recordCount = startDate != null ?
                biometricDataRepository.countByStudent_StudentIdAndCreatedAtBetween(studentEntity.getStudentId(), startDate, endDate) :
                biometricDataRepository.countByStudent_StudentId(studentEntity.getStudentId());

        System.out.println("Se consulta el conteo de registros: " + recordCount);
        
        if (recordCount >= biometricDataThreshold) {
            // Si el conteo de registros es mayor o igual al umbral, se genera el reporte
            List<BiometricDataEntity> biometricDataList = startDate != null ?
                    biometricDataRepository.findByStudent_StudentIdAndCreatedAtBetween(studentEntity.getStudentId(), startDate, endDate) :
                    biometricDataRepository.findByStudent_StudentId(studentEntity.getStudentId());

            ModelPredictionResponse modelPredictionResponse = modelPredictionService.predictFromBiometricData(biometricDataList);

            if (modelPredictionResponse == null) {
                return;
            }

            ReportEntity reportEntity = new ReportEntity();
            reportEntity.setStudent(studentEntity);
            reportEntity.setTemperature(modelPredictionResponse.getTemperature());
            reportEntity.setHeartRate(modelPredictionResponse.getHeartRate());
            reportEntity.setSystolicBloodPressure(modelPredictionResponse.getSystolicBloodPressure());
            reportEntity.setDiastolicBloodPressure(modelPredictionResponse.getDiastolicBloodPressure());
            reportEntity.setPredictionResult(PredictionLevel.valueOf(modelPredictionResponse.getPrediction()));
            reportEntity.setCreatedAt(ZonedDateTime.now());
            reportEntity.setBiometricData(biometricDataList);

            reportRepository.save(reportEntity);

        }


    }

    private ReportResponse mapToResponseWithImages(ReportEntity report) {
        ReportResponse response = reportMapper.toResponse(report);
        response.setImages(generateImageUrls(response.getImages()));
        return response;
    }
    
    private List<String> generateImageUrls(List<String> fileNames) {
        return fileNames.stream()
                .map(fileName -> fileStorageService.generateImageUrl(fileName))
                .toList();
    }

}
