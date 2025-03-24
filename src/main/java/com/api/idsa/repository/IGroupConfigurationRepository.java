package com.api.idsa.repository;

import com.api.idsa.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IGroupConfigurationRepository extends JpaRepository<GroupConfigurationEntity, Long> {

    boolean existsByCampusAndSpecialityAndModalityAndGradeAndGroupAndGeneration(
            CampusEntity campus,
            SpecialityEntity speciality,
            ModalityEntity modality,
            GradeEntity grade,
            GroupEntity group,
            GenerationEntity generation
    );

    List<GroupConfigurationEntity> findByTutor_EmployeeCode(String employeeCode);

    List<GroupConfigurationEntity> findByTutor_Person_User_Email(String personUserEmail);

}
