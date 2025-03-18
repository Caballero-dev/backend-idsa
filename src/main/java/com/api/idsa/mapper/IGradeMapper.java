package com.api.idsa.mapper;

import com.api.idsa.dto.request.GradeRequest;
import com.api.idsa.dto.response.GradeResponse;
import com.api.idsa.model.GradeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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

}
