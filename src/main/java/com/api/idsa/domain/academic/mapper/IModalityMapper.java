package com.api.idsa.domain.academic.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.api.idsa.domain.academic.dto.request.ModalityRequest;
import com.api.idsa.domain.academic.dto.response.ModalityResponse;
import com.api.idsa.domain.academic.model.ModalityEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IModalityMapper {

    @Mapping(source = "modalityId", target = "modalityId")
    @Mapping(source = "modalityName", target = "name")
    List<ModalityResponse> toResponseList(List<ModalityEntity> modalityResponses);

    @Mapping(source = "modalityId", target = "modalityId")
    @Mapping(source = "modalityName", target = "name")
    ModalityResponse toResponse(ModalityEntity modalityEntity);

    @Mapping(source = "name", target = "modalityName")
    ModalityEntity toEntity(ModalityRequest modalityRequest);

    @Mapping(source = "modalityId", target = "modalityId")
    @Mapping(source = "name", target = "modalityName")
    ModalityEntity responseToEntity(ModalityResponse modalityResponse);

}
