package com.api.idsa.domain.academic.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.api.idsa.domain.academic.dto.request.GroupRequest;
import com.api.idsa.domain.academic.dto.response.GroupResponse;
import com.api.idsa.domain.academic.model.GroupEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IGroupMapper {

    @Mapping(source = "groupId", target = "groupId")
    @Mapping(source = "groupName", target = "name")
    List<GroupResponse> toResponseList(List<GroupEntity> groupEntities);

    @Mapping(source = "groupId", target = "groupId")
    @Mapping(source = "groupName", target = "name")
    GroupResponse toResponse(GroupEntity groupEntity);

    @Mapping(source = "name", target = "groupName")
    GroupEntity toEntity(GroupRequest groupRequest);

    @Mapping(source = "groupId", target = "groupId")
    @Mapping(source = "name", target = "groupName")
    GroupEntity responseToEntity(GroupResponse groupResponse);

}
