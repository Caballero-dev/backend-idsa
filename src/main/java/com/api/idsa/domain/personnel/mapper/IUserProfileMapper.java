package com.api.idsa.domain.personnel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.api.idsa.domain.personnel.dto.response.UserProfileResponse;
import com.api.idsa.domain.personnel.model.UserEntity;

@Mapper(componentModel = "spring")
public interface IUserProfileMapper {

    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "person.name", target = "name")
    @Mapping(source = "person.firstSurname", target = "firstSurname")
    @Mapping(source = "person.secondSurname", target = "secondSurname")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "person.phoneNumber", target = "phone")
    @Mapping(source = "role.roleName", target = "roleName")
    @Mapping(target = "key", expression = "java(getKey(user))")
    @Mapping(source = "createdAt", target = "createdAt")
    UserProfileResponse toUserProfileResponse(UserEntity user);

    @Named("getKey")
    default String getKey(UserEntity user) {
        if (user.getRole().getRoleName().equals("ROLE_ADMIN")) {
            return null;
        }
        return user.getPerson().getTutor() != null ? user.getPerson().getTutor().getEmployeeCode() : null;
    }
}
