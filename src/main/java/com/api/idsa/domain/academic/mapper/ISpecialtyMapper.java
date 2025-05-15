package com.api.idsa.domain.academic.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.api.idsa.domain.academic.dto.request.SpecialtyRequest;
import com.api.idsa.domain.academic.dto.response.SpecialtyResponse;
import com.api.idsa.domain.academic.model.SpecialtyEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ISpecialtyMapper {

    @Mapping(source = "specialtyId", target = "specialtyId")
    @Mapping(source = "specialtyName", target = "name")
    @Mapping(source = "shortName", target = "shortName")
    List<SpecialtyResponse> toResponseList(List<SpecialtyEntity> specialtyEntities);

    @Mapping(source = "specialtyId", target = "specialtyId")
    @Mapping(source = "specialtyName", target = "name")
    @Mapping(source = "shortName", target = "shortName")
    SpecialtyResponse toResponse(SpecialtyEntity specialtyEntity);

    @Mapping(source = "name", target = "specialtyName")
    @Mapping(source = "shortName", target = "shortName")
    SpecialtyEntity toEntity(SpecialtyRequest specialtyRequest);

    @Mapping(source = "specialtyId", target = "specialtyId")
    @Mapping(source = "name", target = "specialtyName")
    @Mapping(source = "shortName", target = "shortName")
    SpecialtyEntity responseToEntity(SpecialtyResponse specialtyResponse);

}
