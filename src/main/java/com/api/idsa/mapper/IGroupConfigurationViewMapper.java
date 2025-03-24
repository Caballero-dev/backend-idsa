package com.api.idsa.mapper;

import com.api.idsa.dto.response.GroupConfigurationViewResponse;
import com.api.idsa.model.GroupConfigurationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IGroupConfigurationViewMapper {

    @Mapping(source = "groupConfigurationId", target = "id")
    @Mapping(target = "tutor", expression = "java(getFullNameTutor(entity))")
    @Mapping(source = "campus.campusName", target = "campus")
    @Mapping(source = "speciality.specialityName", target = "specialty")
    @Mapping(source = "speciality.shortName", target = "specialtyShortName")
    @Mapping(source = "modality.modalityName", target = "modality")
    @Mapping(source = "grade.gradeName", target = "grade")
    @Mapping(source = "group.groupName", target = "group")
    @Mapping(target = "generation", expression = "java(getGeneration(entity))")
    @Mapping(target = "students", expression = "java(getStudents(entity))")
    List<GroupConfigurationViewResponse> toResponseList(List<GroupConfigurationEntity> entities);

    @Mapping(source = "groupConfigurationId", target = "id")
    @Mapping(target = "tutor", expression = "java(getFullNameTutor(entity))")
    @Mapping(source = "campus.campusName", target = "campus")
    @Mapping(source = "speciality.specialityName", target = "specialty")
    @Mapping(source = "speciality.shortName", target = "specialtyShortName")
    @Mapping(source = "modality.modalityName", target = "modality")
    @Mapping(source = "grade.gradeName", target = "grade")
    @Mapping(source = "group.groupName", target = "group")
    @Mapping(target = "generation", expression = "java(getGeneration(entity))")
    @Mapping(target = "students", expression = "java(getStudents(entity))")
    GroupConfigurationViewResponse toResponse(GroupConfigurationEntity entity);

    // Obtener el nobre completo del tutor
    @Named("getFullNameTutor")
    default String getFullNameTutor(GroupConfigurationEntity entity) {
        return entity.getTutor().getPerson().getName() + " " + entity.getTutor().getPerson().getFirstSurname() + " " + entity.getTutor().getPerson().getSecondSurname();
    }

    // Obtener la generacion en el siguiente formato         generation: '2023-01-01 / 2023-12-31',
    @Named("getGeneration")
    default String getGeneration(GroupConfigurationEntity entity) {
        return entity.getGeneration().getStartYear() + " / " + entity.getGeneration().getEndYear();
    }

    // Obtener los estudiantes en el siguiente formato         students: 0,
    @Named("getStudents")
    default Integer getStudents(GroupConfigurationEntity entity) {
        return entity.getStudents().size();
    }

}
