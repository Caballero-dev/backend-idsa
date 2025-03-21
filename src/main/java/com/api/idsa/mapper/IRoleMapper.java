package com.api.idsa.mapper;

import com.api.idsa.dto.response.RoleResponse;
import com.api.idsa.model.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IRoleMapper {

    @Mapping(target = "roleId", expression = "java(mapRoleId(roleEntity.getRoleName()))")
    @Mapping(target = "roleName", expression = "java(mapRoleName(roleEntity.getRoleName()))")
    List<RoleResponse> toResponseList(List<RoleEntity> roleEntities);

    @Mapping(target = "roleId", expression = "java(mapRoleId(roleEntity.getRoleName()))")
    @Mapping(target = "roleName", expression = "java(mapRoleName(roleEntity.getRoleName()))")
    RoleResponse toResponse(RoleEntity roleEntity);

    default String mapRoleId(String roleName) {
        return roleName;
    }

    default String mapRoleName(String roleName) {
        return switch (roleName) {
            case "ROLE_ADMIN" -> "Administrador";
            case "ROLE_TUTOR" -> "Tutor";
            default -> roleName;
        };
    }

}

