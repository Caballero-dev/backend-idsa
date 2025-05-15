package com.api.idsa.domain.biometric.service.impl;

import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.biometric.dto.request.BiometricDataRequest;
import com.api.idsa.domain.biometric.mapper.IBiometricDataMapper;
import com.api.idsa.domain.biometric.model.BiometricDataEntity;
import com.api.idsa.domain.biometric.repository.IBiometricDataRepository;
import com.api.idsa.domain.biometric.service.IBiometricDataService;
import com.api.idsa.domain.personnel.model.StudentEntity;
import com.api.idsa.domain.personnel.repository.IStudentRepository;
import com.api.idsa.infrastructure.fileStorage.service.IFileStorageService;

import jakarta.transaction.Transactional;

@Service
public class BiometricDataServiceImpl implements IBiometricDataService {

    @Autowired
    IBiometricDataRepository biometricDataRepository;

    @Autowired
    IStudentRepository studentRepository;

    @Autowired
    IBiometricDataMapper biometricDataMapper;

    @Autowired
    IFileStorageService fileStorageService;

    @Override
    @Transactional
    public BiometricDataEntity createBiometricData(BiometricDataRequest biometricDataRequest) {

        StudentEntity student = studentRepository.findByStudentCode(biometricDataRequest.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Create", "BiometricData", "StudentCode", biometricDataRequest.getStudentId()));

        String fileName = fileStorageService.storeBase64Image(biometricDataRequest.getImage());

        BiometricDataEntity biometricDataEntity = biometricDataMapper.toEntity(biometricDataRequest);
        biometricDataEntity.setStudent(student);
        biometricDataEntity.setImagePath(fileName);
        biometricDataEntity.setCreatedAt(ZonedDateTime.now());
        biometricDataRepository.save(biometricDataEntity);

        return biometricDataEntity;
    }

}
