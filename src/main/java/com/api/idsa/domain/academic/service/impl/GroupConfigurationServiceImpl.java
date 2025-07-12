package com.api.idsa.domain.academic.service.impl;

import com.api.idsa.common.exception.DuplicateResourceException;
import com.api.idsa.common.exception.ResourceDependencyException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.academic.dto.request.GroupConfigurationRequest;
import com.api.idsa.domain.academic.dto.response.GroupConfigurationResponse;
import com.api.idsa.domain.academic.mapper.IGroupConfigurationMapper;
import com.api.idsa.domain.academic.model.GroupConfigurationEntity;
import com.api.idsa.domain.academic.repository.IGroupConfigurationRepository;
import com.api.idsa.domain.academic.service.IGroupConfigurationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
public class GroupConfigurationServiceImpl implements IGroupConfigurationService {

    @Autowired
    IGroupConfigurationRepository groupConfigurationRepository;

    @Autowired
    IGroupConfigurationMapper groupConfigurationMapper;

    @Override
    public Page<GroupConfigurationResponse> getAllGroupConfiguration(Pageable pageable, String search) {
        Page<GroupConfigurationEntity> groupConfigurationPage;

        if (StringUtils.hasText(search)) {
            groupConfigurationPage = groupConfigurationRepository.findGroupConfigurationsBySearchTerm(search.trim(), pageable);
        } else {
            groupConfigurationPage = groupConfigurationRepository.findAll(pageable);
        }

        return groupConfigurationPage.map(groupConfigurationMapper::toResponse);
    }

    @Override
    public GroupConfigurationResponse createGroupConfiguration(GroupConfigurationRequest groupConfigurationRequest) {
        GroupConfigurationEntity groupConfigurationEntity = groupConfigurationMapper.toEntity(groupConfigurationRequest);

        if (groupConfigurationRepository.existsByCampusAndSpecialtyAndModalityAndGradeAndGroupAndGeneration(
                groupConfigurationEntity.getCampus(),
                groupConfigurationEntity.getSpecialty(),
                groupConfigurationEntity.getModality(),
                groupConfigurationEntity.getGrade(),
                groupConfigurationEntity.getGroup(),
                groupConfigurationEntity.getGeneration())
        ) {
            throw new DuplicateResourceException(
                    "Group Configuration",
                    "configuration",
                    groupConfigurationRequest.getCampus().getName() + ", " +
                            groupConfigurationRequest.getSpecialty().getName() + ", " +
                            groupConfigurationRequest.getModality().getName() + ", " +
                            groupConfigurationRequest.getGrade().getName() + ", " +
                            groupConfigurationRequest.getGroup().getName() + ", " +
                            groupConfigurationRequest.getGeneration().getYearStart() + "/" + groupConfigurationRequest.getGeneration().getYearEnd()
            );
        }

        return groupConfigurationMapper.toResponse(groupConfigurationRepository.save(groupConfigurationEntity));
    }

    @Override
    public GroupConfigurationResponse updateGroupConfiguration(UUID groupConfigurationId, GroupConfigurationRequest groupConfigurationRequest) {

        GroupConfigurationEntity groupConfigurationEntity = groupConfigurationRepository.findById(groupConfigurationId)
                .orElseThrow(() -> new ResourceNotFoundException("Group Configuration", "id", groupConfigurationId));

        GroupConfigurationEntity gc = groupConfigurationMapper.toEntity(groupConfigurationRequest);

        boolean hasChanged = !groupConfigurationEntity.getCampus().equals(gc.getCampus()) ||
                !groupConfigurationEntity.getSpecialty().equals(gc.getSpecialty()) ||
                !groupConfigurationEntity.getModality().equals(gc.getModality()) ||
                !groupConfigurationEntity.getGrade().equals(gc.getGrade()) ||
                !groupConfigurationEntity.getGroup().equals(gc.getGroup()) ||
                !groupConfigurationEntity.getGeneration().equals(gc.getGeneration());

        if (hasChanged && groupConfigurationRepository.existsByCampusAndSpecialtyAndModalityAndGradeAndGroupAndGeneration(
                gc.getCampus(),
                gc.getSpecialty(),
                gc.getModality(),
                gc.getGrade(),
                gc.getGroup(),
                gc.getGeneration())
        ) {
            throw new DuplicateResourceException(
                    "Group Configuration",
                    "configuration",
                    groupConfigurationRequest.getCampus().getName() + ", " +
                            groupConfigurationRequest.getSpecialty().getName() + ", " +
                            groupConfigurationRequest.getModality().getName() + ", " +
                            groupConfigurationRequest.getGrade().getName() + ", " +
                            groupConfigurationRequest.getGroup().getName() + ", " +
                            groupConfigurationRequest.getGeneration().getYearStart() + "/" + groupConfigurationRequest.getGeneration().getYearEnd()
            );
        }

        groupConfigurationEntity.setCampus(gc.getCampus());
        groupConfigurationEntity.setSpecialty(gc.getSpecialty());
        groupConfigurationEntity.setModality(gc.getModality());
        groupConfigurationEntity.setGrade(gc.getGrade());
        groupConfigurationEntity.setGroup(gc.getGroup());
        groupConfigurationEntity.setGeneration(gc.getGeneration());
        groupConfigurationEntity.setTutor(gc.getTutor());
        return groupConfigurationMapper.toResponse(groupConfigurationRepository.save(groupConfigurationEntity));
    }

    @Override
    public void deleteGroupConfiguration(UUID groupConfigurationId) {
        try {
            GroupConfigurationEntity groupConfigurationEntity = groupConfigurationRepository.findById(groupConfigurationId)
                    .orElseThrow(() -> new ResourceNotFoundException("Group Configuration", "id", groupConfigurationId));
            groupConfigurationRepository.delete(groupConfigurationEntity);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("students_group_configuration_id_fkey")) {
                throw new ResourceDependencyException("Group Configuration", groupConfigurationId, "enrolled students", "students");
            } else {
                throw new ResourceDependencyException("Group Configuration", groupConfigurationId, "associated records", "unknown");
            }
        }
    }

}
