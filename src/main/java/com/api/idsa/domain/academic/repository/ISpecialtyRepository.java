package com.api.idsa.domain.academic.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.idsa.domain.academic.model.SpecialtyEntity;

import java.util.UUID;

@Repository
public interface ISpecialtyRepository extends JpaRepository<SpecialtyEntity, UUID> {

    boolean existsBySpecialtyName(String specialtyName);

    boolean existsByShortName(String shortName);

    Page<SpecialtyEntity> findByShortNameContainingIgnoreCaseOrSpecialtyNameContainingIgnoreCase(String shortName, String specialtyName, Pageable pageable);

}
