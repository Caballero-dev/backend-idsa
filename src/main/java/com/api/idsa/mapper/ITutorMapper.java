package com.api.idsa.mapper;

import com.api.idsa.dto.request.TutorRequest;
import com.api.idsa.dto.response.TutorResponse;
import com.api.idsa.model.PersonEntity;
import com.api.idsa.model.TutorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ITutorMapper {

    @Mapping(source = "tutorId", target = "tutorId")
    @Mapping(source = "person.user.email", target = "email")
    @Mapping(source = "person.name", target = "name")
    @Mapping(source = "person.firstSurname", target = "firstSurname")
    @Mapping(source = "person.secondSurname", target = "secondSurname")
    @Mapping(source = "person.phoneNumber", target = "phoneNumber")
    @Mapping(source = "employeeCode", target = "employeeCode")
    List<TutorResponse> toResponseList(List<TutorEntity> tutorEntities);

    @Mapping(source = "tutorId", target = "tutorId")
    @Mapping(source = "person.user.email", target = "email")
    @Mapping(source = "person.name", target = "name")
    @Mapping(source = "person.firstSurname", target = "firstSurname")
    @Mapping(source = "person.secondSurname", target = "secondSurname")
    @Mapping(source = "person.phoneNumber", target = "phoneNumber")
    @Mapping(source = "employeeCode", target = "employeeCode")
    TutorResponse toResponse(TutorEntity tutorEntity);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "firstSurname", target = "firstSurname")
    @Mapping(source = "secondSurname", target = "secondSurname")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    PersonEntity toPersonEntity(TutorRequest tutorRequest);

    @Mapping(source = "tutorId", target = "tutorId")
    @Mapping(source = "name", target = "person.name")
    @Mapping(source = "firstSurname", target = "person.firstSurname")
    @Mapping(source = "secondSurname", target = "person.secondSurname")
    @Mapping(source = "phoneNumber", target = "person.phoneNumber")
    @Mapping(source = "email", target = "person.user.email")
    @Mapping(source = "employeeCode", target = "employeeCode")
    TutorEntity responseToEntity(TutorResponse tutorResponse);

}
