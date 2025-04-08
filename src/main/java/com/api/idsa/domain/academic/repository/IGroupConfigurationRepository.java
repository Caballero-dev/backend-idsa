package com.api.idsa.domain.academic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.idsa.domain.academic.model.CampusEntity;
import com.api.idsa.domain.academic.model.GenerationEntity;
import com.api.idsa.domain.academic.model.GradeEntity;
import com.api.idsa.domain.academic.model.GroupConfigurationEntity;
import com.api.idsa.domain.academic.model.GroupEntity;
import com.api.idsa.domain.academic.model.ModalityEntity;
import com.api.idsa.domain.academic.model.SpecialityEntity;

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
