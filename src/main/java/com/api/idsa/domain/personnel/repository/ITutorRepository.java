package com.api.idsa.domain.personnel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.idsa.domain.personnel.model.TutorEntity;

@Repository
public interface ITutorRepository extends JpaRepository<TutorEntity, Long> {

    boolean existsByEmployeeCode(String employeeCode);

}
