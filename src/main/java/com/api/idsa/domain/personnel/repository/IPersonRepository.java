package com.api.idsa.domain.personnel.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.idsa.domain.personnel.model.PersonEntity;

@Repository
public interface IPersonRepository extends JpaRepository<PersonEntity, UUID> {

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByStudent_StudentCode(String studentCode);

    boolean existsByTutor_EmployeeCode(String employeeCode);

}
