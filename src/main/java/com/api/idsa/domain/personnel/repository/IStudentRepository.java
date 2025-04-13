package com.api.idsa.domain.personnel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.idsa.domain.personnel.model.StudentEntity;

@Repository
public interface IStudentRepository extends JpaRepository<StudentEntity, Long> {

    boolean existsByStudentCode(String studentCode);

    Page<StudentEntity> findByGroupConfiguration_GroupConfigurationId(Long groupConfigurationId, Pageable pageable);

}
