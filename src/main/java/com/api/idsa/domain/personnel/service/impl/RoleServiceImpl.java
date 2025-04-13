package com.api.idsa.domain.personnel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.idsa.domain.personnel.dto.response.RoleResponse;
import com.api.idsa.domain.personnel.mapper.IRoleMapper;
import com.api.idsa.domain.personnel.repository.IRoleRepository;
import com.api.idsa.domain.personnel.service.IRoleService;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    IRoleMapper roleMapper;

    @Override
    public List<RoleResponse> getAllRole() {
        return roleMapper.toResponseList(roleRepository.findAll());
    }

    @Override
    public List<RoleResponse> getAllRoleExceptAdmin() {
        return roleMapper.toResponseList(roleRepository.findAllByRoleNameNot("ROLE_ADMIN"));
    }
}
