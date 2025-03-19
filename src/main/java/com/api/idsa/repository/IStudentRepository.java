package com.api.idsa.repository;

import com.api.idsa.model.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStudentRepository extends JpaRepository<StudentEntity, Long> {

}
