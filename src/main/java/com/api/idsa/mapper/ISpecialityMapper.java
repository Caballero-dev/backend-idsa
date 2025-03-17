package com.api.idsa.mapper;

import com.api.idsa.dto.request.SpecialityRequest;
import com.api.idsa.dto.response.SpecialityResponse;
import com.api.idsa.model.SpecialityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ISpecialityMapper {

    @Mapping(source = "specialityId", target = "specialityId")
    @Mapping(source = "specialityName", target = "name")
    @Mapping(source = "shortName", target = "shortName")
    List<SpecialityResponse> toResponseList(List<SpecialityEntity> specialityEntities);

    @Mapping(source = "specialityId", target = "specialityId")
    @Mapping(source = "specialityName", target = "name")
    @Mapping(source = "shortName", target = "shortName")
    SpecialityResponse toResponse(SpecialityEntity specialityEntity);

    @Mapping(source = "name", target = "specialityName")
    @Mapping(source = "shortName", target = "shortName")
    SpecialityEntity toEntity(SpecialityRequest specialityRequest);

}
