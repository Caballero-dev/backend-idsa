package com.api.idsa.domain.personnel.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.api.idsa.domain.personnel.model.StudentEntity;

@Repository
public interface IStudentRepository extends JpaRepository<StudentEntity, Long> {

    boolean existsByStudentCode(String studentCode);

    Optional<StudentEntity> findByStudentCode(String studentCode);

    Page<StudentEntity> findByGroupConfiguration_GroupConfigurationId(Long groupConfigurationId, Pageable pageable);

    @Query(value = "select count(*) from students", nativeQuery = true)
    Integer countStudents();

}
