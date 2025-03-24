package com.api.idsa.mapper;

import com.api.idsa.dto.response.ReportResponse;
import com.api.idsa.model.ReportEntity;
import com.api.idsa.model.ReportImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {IStudentMapper.class})
public interface IReportMapper {

    @Mapping(source = "reportId", target = "reportId")
    @Mapping(source = "student", target = "student")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "images", target = "images", qualifiedByName = "imagesToPaths")
    @Mapping(source = "temperature", target = "temperature")
    @Mapping(source = "pupilDilationRight", target = "pupilDilationRight")
    @Mapping(source = "pupilDilationLeft", target = "pupilDilationLeft")
    @Mapping(source = "heartRate", target = "heartRate")
    @Mapping(source = "oxygenLevels", target = "oxygenLevels")
    ReportResponse toResponse(ReportEntity reportEntity);

    @Mapping(source = "reportId", target = "reportId")
    @Mapping(source = "student", target = "student")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "images", target = "images", qualifiedByName = "imagesToPaths")
    @Mapping(source = "temperature", target = "temperature")
    @Mapping(source = "pupilDilationRight", target = "pupilDilationRight")
    @Mapping(source = "pupilDilationLeft", target = "pupilDilationLeft")
    @Mapping(source = "heartRate", target = "heartRate")
    @Mapping(source = "oxygenLevels", target = "oxygenLevels")
    List<ReportResponse> toResponseList(List<ReportEntity> reportEntities);

    @Named("imagesToPaths")
    default List<String> imagesToPaths(List<ReportImageEntity> images) {
        if (images == null) {
            return null;
        }
        return images.stream()
                .map(ReportImageEntity::getImagePath)
                .collect(Collectors.toList());
    }
}
