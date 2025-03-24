package com.api.idsa.repository;

import com.api.idsa.model.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IStudentRepository extends JpaRepository<StudentEntity, Long> {

    boolean existsByStudentCode(String studentCode);

    boolean existsByPerson_PhoneNumber(String phoneNumber);

    List<StudentEntity> findByGroupConfiguration_GroupConfigurationId(Long groupConfigurationId);

}
