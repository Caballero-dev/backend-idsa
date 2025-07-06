package com.api.idsa.domain.academic.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.idsa.domain.academic.model.CampusEntity;
import com.api.idsa.domain.academic.model.GenerationEntity;
import com.api.idsa.domain.academic.model.GradeEntity;
import com.api.idsa.domain.academic.model.GroupConfigurationEntity;
import com.api.idsa.domain.academic.model.GroupEntity;
import com.api.idsa.domain.academic.model.ModalityEntity;
import com.api.idsa.domain.academic.model.SpecialtyEntity;

@Repository
public interface IGroupConfigurationRepository extends JpaRepository<GroupConfigurationEntity, Long> {

    boolean existsByCampusAndSpecialtyAndModalityAndGradeAndGroupAndGeneration(
            CampusEntity campus,
            SpecialtyEntity specialty,
            ModalityEntity modality,
            GradeEntity grade,
            GroupEntity group,
            GenerationEntity generation
    );

    boolean existsByGroupConfigurationId(Long groupConfigurationId);

    Page<GroupConfigurationEntity> findByTutorPersonUserEmail(String personUserEmail, Pageable pageable);

    @Query(
            value = "select * from search_group_configurations(:search)",
            nativeQuery = true
    )
    Page<GroupConfigurationEntity> findGroupConfigurationsBySearchTerm(@Param("search") String search, Pageable pageable);

    @Query(
            value = "select * from search_group_configurations_by_tutor_email(:email, :search)",
            nativeQuery = true
    )
    Page<GroupConfigurationEntity> findGroupConfigurationsByTutorEmailAndSearchTerm(
            @Param("email") String email,
            @Param("search") String search,
            Pageable pageable
    );

}
