package com.api.idsa.domain.academic.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.api.idsa.domain.academic.dto.request.GenerationRequest;
import com.api.idsa.domain.academic.dto.response.GenerationResponse;
import com.api.idsa.domain.academic.model.GenerationEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IGenerationMapper {

    @Mapping(source = "generationId", target = "generationId")
    @Mapping(source = "startYear", target = "yearStart")
    @Mapping(source = "endYear", target = "yearEnd")
    List<GenerationResponse> toResponseList(List<GenerationEntity> generationEntities);

    @Mapping(source = "generationId", target = "generationId")
    @Mapping(source = "startYear", target = "yearStart")
    @Mapping(source = "endYear", target = "yearEnd")
    GenerationResponse toResponse(GenerationEntity generationEntity);

    @Mapping(source = "yearStart", target = "startYear")
    @Mapping(source = "yearEnd", target = "endYear")
    GenerationEntity toEntity(GenerationRequest generationRequest);

    @Mapping(source = "generationId", target = "generationId")
    @Mapping(source = "yearStart", target = "startYear")
    @Mapping(source = "yearEnd", target = "endYear")
    GenerationEntity responseToEntity(GenerationResponse generationResponse);

}
