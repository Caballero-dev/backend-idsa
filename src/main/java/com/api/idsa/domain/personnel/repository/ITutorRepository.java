package com.api.idsa.domain.personnel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.idsa.domain.personnel.model.TutorEntity;

@Repository
public interface ITutorRepository extends JpaRepository<TutorEntity, Long> {

    boolean existsByEmployeeCode(String employeeCode);

    @Query(
            value = "select * from search_tutors(:search)",
            nativeQuery = true
    )
    Page<TutorEntity> findTutorsBySearchTerm(@Param("search") String search, Pageable pageable);

}
