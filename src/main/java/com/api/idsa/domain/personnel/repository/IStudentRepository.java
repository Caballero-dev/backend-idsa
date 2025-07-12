package com.api.idsa.domain.personnel.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.idsa.domain.personnel.model.StudentEntity;

@Repository
public interface IStudentRepository extends JpaRepository<StudentEntity, UUID> {

    boolean existsByStudentCode(String studentCode);

    Optional<StudentEntity> findByStudentCode(String studentCode);

    Page<StudentEntity> findByGroupConfigurationGroupConfigurationId(UUID groupConfigurationId, Pageable pageable);

    @Query(value = "select count(*) from students", nativeQuery = true)
    Integer countStudents();

    @Query(
            value = "select * from search_students(:search)",
            nativeQuery = true
    )
    Page<StudentEntity> findStudentsBySearchTerm(@Param("search") String search, Pageable pageable);

    @Query(
            value = "select * from search_students_by_group_configuration(:search, :groupConfigurationId)",
            nativeQuery = true
    )
    Page<StudentEntity> findStudentsBySearchTermAndGroupConfigurationId(@Param("search") String search, @Param("groupConfigurationId") UUID groupConfigurationId, Pageable pageable);

}
