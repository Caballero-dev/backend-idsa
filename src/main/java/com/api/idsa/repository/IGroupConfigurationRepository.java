package com.api.idsa.repository;

import com.api.idsa.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGroupConfigurationRepository extends JpaRepository<GroupConfigurationEntity, Long> {

    boolean existsByCampusAndSpecialityAndModalityAndGradeAndGroupAndGeneration(
            CampusEntity campus,
            SpecialityEntity speciality,
            ModalityEntity modality,
            GradeEntity grade,
            GroupEntity group,
            GenerationEntity generation
    );

}
