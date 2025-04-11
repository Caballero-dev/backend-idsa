package com.api.idsa.domain.biometric.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.idsa.domain.biometric.model.BiometricDataEntity;

@Repository
public interface IBiometricDataRepository extends JpaRepository<BiometricDataEntity, Long> {
    
}
