package com.api.idsa.domain.biometric.repository;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.idsa.domain.biometric.model.BiometricDataEntity;

@Repository
public interface IBiometricDataRepository extends JpaRepository<BiometricDataEntity, Long> {

    Long countByStudent_StudentIdAndCreatedAtBetween(Long studentId, ZonedDateTime startDate, ZonedDateTime endDate);

    Long countByStudent_StudentId(Long studentId);

    List<BiometricDataEntity> findByStudent_StudentIdAndCreatedAtBetween(Long studentId, ZonedDateTime startDate, ZonedDateTime endDate);

    List<BiometricDataEntity> findByStudent_StudentId(Long studentId);
    
}
