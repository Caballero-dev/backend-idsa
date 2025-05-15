package com.api.idsa.domain.biometric.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.api.idsa.domain.biometric.dto.response.ReportResponse;
import com.api.idsa.domain.biometric.model.BiometricDataEntity;
import com.api.idsa.domain.biometric.model.ReportEntity;
import com.api.idsa.domain.personnel.mapper.IStudentMapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {IStudentMapper.class})
public interface IReportMapper {

    @Mapping(source = "reportId", target = "reportId")
    @Mapping(source = "student", target = "student")
    @Mapping(source = "temperature", target = "temperature")
    @Mapping(source = "heartRate", target = "heartRate")
    @Mapping(source = "systolicBloodPressure", target = "systolicBloodPressure")
    @Mapping(source = "diastolicBloodPressure", target = "diastolicBloodPressure")
    @Mapping(source = "pupilDilationRight", target = "pupilDilationRight")
    @Mapping(source = "pupilDilationLeft", target = "pupilDilationLeft")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "biometricData", target = "images", qualifiedByName = "imagesToPaths")
    ReportResponse toResponse(ReportEntity reportEntity);

    @Mapping(source = "reportId", target = "reportId")
    @Mapping(source = "student", target = "student")
    @Mapping(source = "temperature", target = "temperature")
    @Mapping(source = "heartRate", target = "heartRate")
    @Mapping(source = "systolicBloodPressure", target = "systolicBloodPressure")
    @Mapping(source = "diastolicBloodPressure", target = "diastolicBloodPressure")
    @Mapping(source = "pupilDilationRight", target = "pupilDilationRight")
    @Mapping(source = "pupilDilationLeft", target = "pupilDilationLeft")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "biometricData", target = "images", qualifiedByName = "imagesToPaths")
    List<ReportResponse> toResponseList(List<ReportEntity> reportEntities);

    @Named("imagesToPaths")
    default List<String> imagesToPaths(List<BiometricDataEntity> biometricData) {
        if (biometricData == null) {
            return null;
        }
        return biometricData.stream()
                .map(BiometricDataEntity::getImagePath)
                .collect(Collectors.toList());
    }
}
