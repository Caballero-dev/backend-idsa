package com.api.idsa.domain.academic.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.api.idsa.domain.academic.dto.request.GradeRequest;
import com.api.idsa.domain.academic.dto.response.GradeResponse;
import com.api.idsa.domain.academic.model.GradeEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IGradeMapper {

    @Mapping(source = "gradeId", target = "gradeId")
    @Mapping(source = "gradeName", target = "name")
    List<GradeResponse> toResponseList(List<GradeEntity> gradeEntities);

    @Mapping(source = "gradeId", target = "gradeId")
    @Mapping(source = "gradeName", target = "name")
    GradeResponse toResponse(GradeEntity gradeEntity);

    @Mapping(source = "name", target = "gradeName")
    GradeEntity toEntity(GradeRequest gradeRequest);

    @Mapping(source = "gradeId", target = "gradeId")
    @Mapping(source = "name", target = "gradeName")
    GradeEntity responseToEntity(GradeResponse gradeResponse);

}
