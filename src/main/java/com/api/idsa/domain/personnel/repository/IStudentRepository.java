package com.api.idsa.domain.personnel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.idsa.domain.personnel.model.StudentEntity;

import java.util.List;

public interface IStudentRepository extends JpaRepository<StudentEntity, Long> {

    boolean existsByStudentCode(String studentCode);

    boolean existsByPerson_PhoneNumber(String phoneNumber);

    List<StudentEntity> findByGroupConfiguration_GroupConfigurationId(Long groupConfigurationId);

}
