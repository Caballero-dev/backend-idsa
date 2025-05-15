package com.api.idsa.domain.academic.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.api.idsa.domain.academic.dto.request.CampusRequest;
import com.api.idsa.domain.academic.dto.response.CampusResponse;
import com.api.idsa.domain.academic.model.CampusEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICampusMapper {

    @Mapping(source = "campusId", target = "campusId")
    @Mapping(source = "campusName", target = "name")
    List<CampusResponse> toResponseList(List<CampusEntity> campusEntities);

    @Mapping(source = "campusId", target = "campusId")
    @Mapping(source = "campusName", target = "name")
    CampusResponse toResponse(CampusEntity campusEntity);

    @Mapping(source = "name", target = "campusName")
    CampusEntity toEntity(CampusRequest campusRequest);

    @Mapping(source = "campusId", target = "campusId")
    @Mapping(source = "name", target = "campusName")
    CampusEntity responseToEntity(CampusResponse campusResponse);

}
