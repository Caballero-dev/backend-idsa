package com.api.idsa.mapper;

import com.api.idsa.dto.request.CampusRequest;
import com.api.idsa.dto.response.CampusResponse;
import com.api.idsa.model.CampusEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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

}
