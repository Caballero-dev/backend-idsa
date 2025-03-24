package com.api.idsa.service.impl;

import com.api.idsa.dto.request.GroupConfigurationRequest;
import com.api.idsa.dto.response.GroupConfigurationResponse;
import com.api.idsa.exception.DuplicateResourceException;
import com.api.idsa.exception.ResourceNotFoundException;
import com.api.idsa.mapper.IGroupConfigurationMapper;
import com.api.idsa.model.GroupConfigurationEntity;
import com.api.idsa.repository.IGroupConfigurationRepository;
import com.api.idsa.service.IGroupConfigurationService;
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
    public List<GroupConfigurationResponse> findAll() {
        return groupConfigurationMapper.toResponseList(groupConfigurationRepository.findAll());
    }

    @Override
    public GroupConfigurationResponse createGroupConfiguration(GroupConfigurationRequest groupConfigurationRequest) throws DuplicateResourceException {
        GroupConfigurationEntity groupConfigurationEntity = groupConfigurationMapper.toEntity(groupConfigurationRequest);

        if (groupConfigurationRepository.existsByCampusAndSpecialityAndModalityAndGradeAndGroupAndGeneration(
                groupConfigurationEntity.getCampus(),
                groupConfigurationEntity.getSpeciality(),
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
                            groupConfigurationRequest.getSpeciality().getName() + ", " +
                            groupConfigurationRequest.getModality().getName() + ", " +
                            groupConfigurationRequest.getGrade().getName() + ", " +
                            groupConfigurationRequest.getGroup().getName() + ", " +
                            groupConfigurationRequest.getGeneration().getYearStart() + "/" + groupConfigurationRequest.getGeneration().getYearEnd()
            );
        }

        return groupConfigurationMapper.toResponse(groupConfigurationRepository.save(groupConfigurationEntity));
    }

    @Override
    public GroupConfigurationResponse updateGroupConfiguration(Long groupConfigurationId, GroupConfigurationRequest groupConfigurationRequest) throws ResourceNotFoundException, DuplicateResourceException {

        GroupConfigurationEntity groupConfigurationEntity = groupConfigurationRepository.findById(groupConfigurationId)
                .orElseThrow(() -> new ResourceNotFoundException("update", "GroupConfiguration", groupConfigurationId));

        GroupConfigurationEntity gc = groupConfigurationMapper.toEntity(groupConfigurationRequest);

        boolean hasChanged = !groupConfigurationEntity.getCampus().equals(gc.getCampus()) ||
                !groupConfigurationEntity.getSpeciality().equals(gc.getSpeciality()) ||
                !groupConfigurationEntity.getModality().equals(gc.getModality()) ||
                !groupConfigurationEntity.getGrade().equals(gc.getGrade()) ||
                !groupConfigurationEntity.getGroup().equals(gc.getGroup()) ||
                !groupConfigurationEntity.getGeneration().equals(gc.getGeneration());

        if (hasChanged && groupConfigurationRepository.existsByCampusAndSpecialityAndModalityAndGradeAndGroupAndGeneration(
                gc.getCampus(),
                gc.getSpeciality(),
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
                            groupConfigurationRequest.getSpeciality().getName() + ", " +
                            groupConfigurationRequest.getModality().getName() + ", " +
                            groupConfigurationRequest.getGrade().getName() + ", " +
                            groupConfigurationRequest.getGroup().getName() + ", " +
                            groupConfigurationRequest.getGeneration().getYearStart() + "/" + groupConfigurationRequest.getGeneration().getYearEnd()
            );
        }

        groupConfigurationEntity.setCampus(gc.getCampus());
        groupConfigurationEntity.setSpeciality(gc.getSpeciality());
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
