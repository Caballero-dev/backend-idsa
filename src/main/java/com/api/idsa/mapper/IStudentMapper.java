package com.api.idsa.mapper;

import com.api.idsa.dto.request.StudentRequest;
import com.api.idsa.dto.response.StudentResponse;
import com.api.idsa.model.PersonEntity;
import com.api.idsa.model.StudentEntity;
import com.api.idsa.model.ReportEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Mapper(componentModel = "spring")
public interface IStudentMapper {

    @Mapping(source = "studentId", target = "studentId")
    @Mapping(source = "person.name", target = "name")
    @Mapping(source = "person.firstSurname", target = "firstSurname")
    @Mapping(source = "person.secondSurname", target = "secondSurname")
    @Mapping(source = "person.phoneNumber", target = "phoneNumber")
    @Mapping(source = "studentCode", target = "studentCode")
    @Mapping(source = "reports", target = "predictionResult", qualifiedByName = "getLatestPredictionResult")
    List<StudentResponse> toResponseList(List<StudentEntity> studentEntities);

    @Mapping(source = "studentId", target = "studentId")
    @Mapping(source = "person.name", target = "name")
    @Mapping(source = "person.firstSurname", target = "firstSurname")
    @Mapping(source = "person.secondSurname", target = "secondSurname")
    @Mapping(source = "person.phoneNumber", target = "phoneNumber")
    @Mapping(source = "studentCode", target = "studentCode")
    @Mapping(source = "reports", target = "predictionResult", qualifiedByName = "getLatestPredictionResult")
    StudentResponse toResponse(StudentEntity studentEntity);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "firstSurname", target = "firstSurname")
    @Mapping(source = "secondSurname", target = "secondSurname")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    PersonEntity toPersonEntity(StudentRequest studentRequest);

    @Named("getLatestPredictionResult")
    default BigDecimal getLatestPredictionResult(List<ReportEntity> reports) {
        if (reports == null || reports.isEmpty()) {
            return null;
        }
        return reports.stream()
                .max(Comparator.comparing(ReportEntity::getCreatedAt))
                .map(ReportEntity::getPredictionResult)
                .orElse(null);
    }

}
