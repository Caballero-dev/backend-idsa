package com.api.idsa.repository;

import com.api.idsa.model.SpecialityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISpecialityRespository extends JpaRepository<SpecialityEntity, Long> {

    boolean existsBySpecialityName(String specialityName);

    boolean existsByShortName(String shortName);

}
