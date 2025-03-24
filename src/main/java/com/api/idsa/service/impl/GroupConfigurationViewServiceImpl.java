package com.api.idsa.service.impl;

import com.api.idsa.dto.response.GroupConfigurationViewResponse;
import com.api.idsa.mapper.IGroupConfigurationViewMapper;
import com.api.idsa.repository.IGroupConfigurationRepository;
import com.api.idsa.service.IGroupConfigurationViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupConfigurationViewServiceImpl implements IGroupConfigurationViewService {

    @Autowired
    IGroupConfigurationRepository groupConfigurationRepository;

    @Autowired
    IGroupConfigurationViewMapper groupConfigurationViewMapper;

    // Endpoint para admin puede ver todas las configuraciones de grupos
    @Override
    public List<GroupConfigurationViewResponse> getGroupConfigurationViewList() {
        return groupConfigurationViewMapper.toResponseList(groupConfigurationRepository.findAll());
    }

    // Endpoint para tutor que obtiene los grupos de un tutor por su código de empleado
    @Override
    public List<GroupConfigurationViewResponse> getGroupConfigurationViewListByTutor(String employeeCode) {
        return groupConfigurationViewMapper.toResponseList(groupConfigurationRepository.findByTutor_EmployeeCode(employeeCode));
    }

    // Endpoint para tutor que obtiene los grupos de un tutor por su correo electrónico
    @Override
    public List<GroupConfigurationViewResponse> getGroupConfigurationViewListByTutorEmail(String personUserEmail) {
        return groupConfigurationViewMapper.toResponseList(groupConfigurationRepository.findByTutor_Person_User_Email(personUserEmail));
    }
}
