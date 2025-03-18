package com.api.idsa.service.impl;

import com.api.idsa.dto.request.GroupRequest;
import com.api.idsa.dto.response.GroupResponse;
import com.api.idsa.exception.DuplicateResourceException;
import com.api.idsa.exception.ResourceNotFoundException;
import com.api.idsa.mapper.IGroupMapper;
import com.api.idsa.model.GroupEntity;
import com.api.idsa.repository.IGroupRepository;
import com.api.idsa.service.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements IGroupService {

    @Autowired
    IGroupRepository groupRepository;

    @Autowired
    IGroupMapper groupMapper;

    @Override
    public List<GroupResponse> findAll() {
        return groupMapper.toResponseList(groupRepository.findAll());
    }

    @Override
    public GroupResponse createGroup(GroupRequest groupRequest) throws DuplicateResourceException {

        if (groupRepository.existsByGroupName(groupRequest.getName())) {
            throw new DuplicateResourceException("create", "Group", groupRequest.getName());
        }

        GroupEntity groupEntity = groupMapper.toEntity(groupRequest);
        return groupMapper.toResponse(groupRepository.save(groupEntity));
    }

    @Override
    public GroupResponse updateGroup(Long groupId, GroupRequest groupRequest) throws ResourceNotFoundException, DuplicateResourceException {
        GroupEntity groupEntity = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("update", "Group", groupId));

        if (!groupEntity.getGroupName().equals(groupRequest.getName()) && groupRepository.existsByGroupName(groupRequest.getName())) {
            throw new DuplicateResourceException("update", "Group", groupRequest.getName());
        }

        groupEntity.setGroupName(groupRequest.getName());
        return groupMapper.toResponse(groupRepository.save(groupEntity));
    }

    @Override
    public void deleteGroup(Long groupId) throws ResourceNotFoundException {
        GroupEntity groupEntity = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("delete", "Group", groupId));
        groupRepository.delete(groupEntity);
    }


}
