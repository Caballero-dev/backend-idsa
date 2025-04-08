package com.api.idsa.domain.academic.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.api.idsa.domain.academic.dto.request.GroupConfigurationRequest;
import com.api.idsa.domain.academic.dto.response.GroupConfigurationResponse;
import com.api.idsa.domain.academic.model.GroupConfigurationEntity;
import com.api.idsa.domain.personnel.mapper.ITutorMapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        ITutorMapper.class,
        ICampusMapper.class,
        ISpecialityMapper.class,
        IModalityMapper.class,
        IGradeMapper.class,
        IGroupMapper.class,
        IGenerationMapper.class
})
public interface IGroupConfigurationMapper {

    @Mapping(source = "groupConfigurationId", target = "groupConfigurationId")
    @Mapping(source = "tutor", target = "tutor")
    @Mapping(source = "campus", target = "campus")
    @Mapping(source = "speciality", target = "speciality")
    @Mapping(source = "modality", target = "modality")
    @Mapping(source = "grade", target = "grade")
    @Mapping(source = "group", target = "group")
    @Mapping(source = "generation", target = "generation")
    List<GroupConfigurationResponse> toResponseList(List<GroupConfigurationEntity> entities);

    @Mapping(source = "groupConfigurationId", target = "groupConfigurationId")
    @Mapping(source = "tutor", target = "tutor")
    @Mapping(source = "campus", target = "campus")
    @Mapping(source = "speciality", target = "speciality")
    @Mapping(source = "modality", target = "modality")
    @Mapping(source = "grade", target = "grade")
    @Mapping(source = "group", target = "group")
    @Mapping(source = "generation", target = "generation")
    GroupConfigurationResponse toResponse(GroupConfigurationEntity entity);

    @Mapping(source = "tutor", target = "tutor")
    @Mapping(source = "campus", target = "campus")
    @Mapping(source = "speciality", target = "speciality")
    @Mapping(source = "modality", target = "modality")
    @Mapping(source = "grade", target = "grade")
    @Mapping(source = "group", target = "group")
    @Mapping(source = "generation", target = "generation")
    GroupConfigurationEntity toEntity(GroupConfigurationRequest request);

}
