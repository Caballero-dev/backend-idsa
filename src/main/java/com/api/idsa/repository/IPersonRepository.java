package com.api.idsa.repository;

import com.api.idsa.model.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPersonRepository extends JpaRepository<PersonEntity, Long> {

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByStudent_StudentCode(String studentCode);

    boolean existsByTutor_EmployeeCode(String employeeCode);

}
