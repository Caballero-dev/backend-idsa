package com.api.idsa.repository;

import com.api.idsa.model.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPersonRepository extends JpaRepository<PersonEntity, Long> {

}
