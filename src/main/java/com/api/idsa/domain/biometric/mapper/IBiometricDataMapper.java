package com.api.idsa.domain.biometric.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.api.idsa.domain.biometric.dto.request.BiometricDataRequest;
import com.api.idsa.domain.biometric.model.BiometricDataEntity;

@Mapper(componentModel = "spring")
public interface IBiometricDataMapper {
    
    @Mapping(source = "temperature", target = "temperature")
    @Mapping(source = "heartRate", target = "heartRate")
    @Mapping(source = "systolicBloodPressure", target = "systolicBloodPressure")
    @Mapping(source = "diastolicBloodPressure", target = "diastolicBloodPressure")
    BiometricDataEntity toEntity(BiometricDataRequest biometricDataRequest);

}
