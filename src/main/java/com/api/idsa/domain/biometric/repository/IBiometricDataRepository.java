package com.api.idsa.domain.biometric.repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.idsa.domain.biometric.model.BiometricDataEntity;

@Repository
public interface IBiometricDataRepository extends JpaRepository<BiometricDataEntity, UUID> {

    Long countByStudent_StudentIdAndCreatedAtBetween(UUID studentId, ZonedDateTime startDate, ZonedDateTime endDate);

    Long countByStudent_StudentId(UUID studentId);

    List<BiometricDataEntity> findByStudent_StudentIdAndCreatedAtBetween(UUID studentId, ZonedDateTime startDate, ZonedDateTime endDate);

    List<BiometricDataEntity> findByStudent_StudentId(UUID studentId);
    
}
