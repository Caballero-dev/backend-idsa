package com.api.idsa.domain.academic.service.impl;

import com.api.idsa.common.exception.DuplicateResourceException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.academic.dto.request.GroupRequest;
import com.api.idsa.domain.academic.dto.response.GroupResponse;
import com.api.idsa.domain.academic.mapper.IGroupMapper;
import com.api.idsa.domain.academic.model.GroupEntity;
import com.api.idsa.domain.academic.repository.IGroupRepository;
import com.api.idsa.domain.academic.service.IGroupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;

@Service
public class GroupServiceImpl implements IGroupService {

    @Autowired
    IGroupRepository groupRepository;

    @Autowired
    IGroupMapper groupMapper;

    @Override
    public Page<GroupResponse> getAllGroup(Pageable pageable) {
        Page<GroupEntity> groupPage = groupRepository.findAll(pageable);
        return groupPage.map(groupMapper::toResponse);
    }

    @Override
    public GroupResponse createGroup(GroupRequest groupRequest) {

        if (groupRepository.existsByGroupName(groupRequest.getName())) {
            throw new DuplicateResourceException("Group", "name", groupRequest.getName());
        }

        GroupEntity groupEntity = groupMapper.toEntity(groupRequest);
        return groupMapper.toResponse(groupRepository.save(groupEntity));
    }

    @Override
    public GroupResponse updateGroup(Long groupId, GroupRequest groupRequest) {

        GroupEntity groupEntity = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "id", groupId));

        if (!groupEntity.getGroupName().equals(groupRequest.getName()) && groupRepository.existsByGroupName(groupRequest.getName())) {
            throw new DuplicateResourceException("Group", "name", groupRequest.getName());
        }

        groupEntity.setGroupName(groupRequest.getName());
        return groupMapper.toResponse(groupRepository.save(groupEntity));
    }

    @Override
    public void deleteGroup(Long groupId) {
        try {
            GroupEntity groupEntity = groupRepository.findById(groupId)
                    .orElseThrow(() -> new ResourceNotFoundException("Group", "id", groupId));
            groupRepository.delete(groupEntity);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage() != null && e.getMessage().contains("group_configurations_group_id_fkey")) {
                throw new com.api.idsa.common.exception.ResourceDependencyException("Group", groupId, "assigned group configurations", "group_configurations");
            } else {
                throw new com.api.idsa.common.exception.ResourceDependencyException("Group", groupId, "associated records", "unknown");
            }
        }
    }

}
