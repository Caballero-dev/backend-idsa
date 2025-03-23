package com.api.idsa.repository;

import com.api.idsa.model.TutorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITutorRepository extends JpaRepository<TutorEntity, Long> {

    boolean existsByEmployeeCode(String employeeCode);

}
