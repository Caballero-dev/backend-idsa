package com.api.idsa.domain.personnel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.api.idsa.domain.personnel.dto.request.UserRequest;
import com.api.idsa.domain.personnel.dto.response.UserResponse;
import com.api.idsa.domain.personnel.model.PersonEntity;
import com.api.idsa.domain.personnel.model.UserEntity;

import java.util.List;

@Mapper(componentModel = "spring", uses = {IRoleMapper.class})
public interface IUserMapper {

    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "role", target = "role")
    @Mapping(source = "person.name", target = "name")
    @Mapping(source = "person.firstSurname", target = "firstSurname")
    @Mapping(source = "person.secondSurname", target = "secondSurname")
    @Mapping(target = "key", expression = "java(mapKey(userEntity))")
    @Mapping(source = "person.phoneNumber", target = "phoneNumber")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "isActive", target = "isActive")
    List<UserResponse> toResponseList(List<UserEntity> userEntities);

    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "role", target = "role")
    @Mapping(source = "person.name", target = "name")
    @Mapping(source = "person.firstSurname", target = "firstSurname")
    @Mapping(source = "person.secondSurname", target = "secondSurname")
    @Mapping(target = "key", expression = "java(mapKey(userEntity))")
    @Mapping(source = "person.phoneNumber", target = "phoneNumber")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "isActive", target = "isActive")
    UserResponse toResponse(UserEntity userEntity);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "firstSurname", target = "firstSurname")
    @Mapping(source = "secondSurname", target = "secondSurname")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    PersonEntity toPersonEntity(UserRequest userRequest);

    @Named("mapKey")
    default String mapKey(UserEntity userEntity) {
        return switch (userEntity.getRole().getRoleName()) {
            case "ROLE_TUTOR" -> userEntity.getPerson().getTutor().getEmployeeCode();
            case "ROLE_STUDENT" -> userEntity.getPerson().getStudent().getStudentCode();
            default -> null;
        };
    }
}
