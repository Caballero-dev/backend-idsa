package com.api.idsa.domain.personnel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.api.idsa.domain.personnel.dto.response.RoleResponse;
import com.api.idsa.domain.personnel.model.RoleEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IRoleMapper {

    @Mapping(target = "roleId", expression = "java(mapRoleId(roleEntity.getRoleName()))")
    @Mapping(target = "roleName", expression = "java(mapRoleName(roleEntity.getRoleName()))")
    List<RoleResponse> toResponseList(List<RoleEntity> roleEntities);

    @Mapping(target = "roleId", expression = "java(mapRoleId(roleEntity.getRoleName()))")
    @Mapping(target = "roleName", expression = "java(mapRoleName(roleEntity.getRoleName()))")
    RoleResponse toResponse(RoleEntity roleEntity);

    @Named("mapRoleId")
    default String mapRoleId(String roleName) {
        return roleName;
    }

    @Named("mapRoleName")
    default String mapRoleName(String roleName) {
        return switch (roleName) {
            case "ROLE_ADMIN" -> "Administrador";
            case "ROLE_TUTOR" -> "Tutor";
            default -> roleName;
        };
    }

}

