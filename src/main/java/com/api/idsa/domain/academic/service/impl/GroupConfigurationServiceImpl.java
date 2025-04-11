package com.api.idsa.domain.academic.service.impl;

import com.api.idsa.common.exception.DuplicateResourceException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.academic.dto.request.GroupConfigurationRequest;
import com.api.idsa.domain.academic.dto.response.GroupConfigurationResponse;
import com.api.idsa.domain.academic.mapper.IGroupConfigurationMapper;
import com.api.idsa.domain.academic.model.GroupConfigurationEntity;
import com.api.idsa.domain.academic.repository.IGroupConfigurationRepository;
import com.api.idsa.domain.academic.service.IGroupConfigurationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupConfigurationServiceImpl implements IGroupConfigurationService {

    @Autowired
    IGroupConfigurationRepository groupConfigurationRepository;

    @Autowired
    IGroupConfigurationMapper groupConfigurationMapper;

    @Override
    public List<GroupConfigurationResponse> getAllGroupConfiguration() {
        return groupConfigurationMapper.toResponseList(groupConfigurationRepository.findAll());
    }

    // FIXME: Verificar si la validación de duplicados es correcta
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
                    "create",
                    "GroupConfiguration",
                    "UniqueGroupConfiguration",
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

    // FIXME: Verificar si la validación de duplicados es correcta
    @Override
    public GroupConfigurationResponse updateGroupConfiguration(Long groupConfigurationId, GroupConfigurationRequest groupConfigurationRequest) {

        GroupConfigurationEntity groupConfigurationEntity = groupConfigurationRepository.findById(groupConfigurationId)
                .orElseThrow(() -> new ResourceNotFoundException("update", "GroupConfiguration", groupConfigurationId));

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
                    "update",
                    "GroupConfiguration",
                    "UniqueGroupConfiguration",
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
    public void deleteGroupConfiguration(Long groupConfigurationId) throws ResourceNotFoundException {

        GroupConfigurationEntity groupConfigurationEntity = groupConfigurationRepository.findById(groupConfigurationId)
                .orElseThrow(() -> new ResourceNotFoundException("delete", "GroupConfiguration", groupConfigurationId));
        
        groupConfigurationRepository.delete(groupConfigurationEntity);
    }

}
