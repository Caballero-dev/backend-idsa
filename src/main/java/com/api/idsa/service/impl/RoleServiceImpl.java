package com.api.idsa.service.impl;

import com.api.idsa.dto.response.RoleResponse;
import com.api.idsa.mapper.IRoleMapper;
import com.api.idsa.model.RoleEntity;
import com.api.idsa.repository.IRoleRepository;
import com.api.idsa.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    IRoleMapper roleMapper;

    @Override
    public List<RoleResponse> findAll() {
        return roleMapper.toResponseList(roleRepository.findAll());
    }

    @Override
    public List<RoleResponse> findAllExceptAdmin() {
        List<RoleEntity> roleEntities = roleRepository.findAll();
        roleEntities.removeIf(role -> "ROLE_ADMIN".equals(role.getRoleName()));
        return roleMapper.toResponseList(roleEntities);
    }
}
