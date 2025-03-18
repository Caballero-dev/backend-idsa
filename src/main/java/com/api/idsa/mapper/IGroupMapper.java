package com.api.idsa.mapper;

import com.api.idsa.dto.request.GroupRequest;
import com.api.idsa.dto.response.GroupResponse;
import com.api.idsa.model.GroupEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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

}
