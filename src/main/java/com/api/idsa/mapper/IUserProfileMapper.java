package com.api.idsa.mapper;

import com.api.idsa.dto.response.UserProfileResponse;
import com.api.idsa.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface IUserProfileMapper {

    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "person.name", target = "name")
    @Mapping(source = "person.firstSurname", target = "firstSurname")
    @Mapping(source = "person.secondSurname", target = "secondSurname")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "person.phoneNumber", target = "phone")
    @Mapping(source = "role.roleName", target = "roleName")
    @Mapping(source = ".", target = "key", qualifiedByName = "getKey")
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
