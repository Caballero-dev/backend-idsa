package com.api.idsa.domain.academic.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.api.idsa.domain.academic.dto.response.GroupConfigurationViewResponse;
import com.api.idsa.domain.academic.model.GroupConfigurationEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IGroupConfigurationViewMapper {

    @Mapping(source = "groupConfigurationId", target = "id")
    @Mapping(target = "tutor", expression = "java(getFullNameTutor(groupConfigurationEntity))")
    @Mapping(source = "campus.campusName", target = "campus")
    @Mapping(source = "specialty.specialtyName", target = "specialty")
    @Mapping(source = "specialty.shortName", target = "specialtyShortName")
    @Mapping(source = "modality.modalityName", target = "modality")
    @Mapping(source = "grade.gradeName", target = "grade")
    @Mapping(source = "group.groupName", target = "group")
    @Mapping(target = "generation", expression = "java(getGeneration(groupConfigurationEntity))")
    @Mapping(target = "students", expression = "java(getStudents(groupConfigurationEntity))")
    List<GroupConfigurationViewResponse> toResponseList(List<GroupConfigurationEntity> groupConfigurationEntities);

    @Mapping(source = "groupConfigurationId", target = "id")
    @Mapping(target = "tutor", expression = "java(getFullNameTutor(groupConfigurationEntity))")
    @Mapping(source = "campus.campusName", target = "campus")
    @Mapping(source = "specialty.specialtyName", target = "specialty")
    @Mapping(source = "specialty.shortName", target = "specialtyShortName")
    @Mapping(source = "modality.modalityName", target = "modality")
    @Mapping(source = "grade.gradeName", target = "grade")
    @Mapping(source = "group.groupName", target = "group")
    @Mapping(target = "generation", expression = "java(getGeneration(groupConfigurationEntity))")
    @Mapping(target = "students", expression = "java(getStudents(groupConfigurationEntity))")
    GroupConfigurationViewResponse toResponse(GroupConfigurationEntity groupConfigurationEntity);

    // Obtener el nobre completo del tutor
    @Named("getFullNameTutor")
    default String getFullNameTutor(GroupConfigurationEntity entity) {
        return entity.getTutor().getPerson().getName() + " " + entity.getTutor().getPerson().getFirstSurname() + " " + entity.getTutor().getPerson().getSecondSurname();
    }

    // Obtener la generacion en el siguiente formato    generation: '2023-01-01 / 2023-12-31',
    @Named("getGeneration")
    default String getGeneration(GroupConfigurationEntity entity) {
        return entity.getGeneration().getStartYear() + " / " + entity.getGeneration().getEndYear();
    }

    // Obtener los estudiantes en el siguiente formato  students: 0,
    @Named("getStudents")
    default Integer getStudents(GroupConfigurationEntity entity) {
        return entity.getStudents().size();
    }

}
