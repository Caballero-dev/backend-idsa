package com.api.idsa.domain.academic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.idsa.domain.academic.dto.response.GroupConfigurationViewResponse;
import com.api.idsa.domain.academic.mapper.IGroupConfigurationViewMapper;
import com.api.idsa.domain.academic.repository.IGroupConfigurationRepository;
import com.api.idsa.domain.academic.service.IGroupConfigurationViewService;

import java.util.List;

@Service
public class GroupConfigurationViewServiceImpl implements IGroupConfigurationViewService {

    @Autowired
    IGroupConfigurationRepository groupConfigurationRepository;

    @Autowired
    IGroupConfigurationViewMapper groupConfigurationViewMapper;

    // Endpoint para admin puede ver todas las configuraciones de grupos
    @Override
    public List<GroupConfigurationViewResponse> getAllGroupConfigurationView() {
        return groupConfigurationViewMapper.toResponseList(groupConfigurationRepository.findAll());
    }

    // Endpoint para tutor que obtiene los grupos de un tutor por su código de empleado
    @Override
    public List<GroupConfigurationViewResponse> getAllGroupConfigurationViewByTutor(String employeeCode) {
        return groupConfigurationViewMapper.toResponseList(groupConfigurationRepository.findByTutor_EmployeeCode(employeeCode));
    }

    // Endpoint para tutor que obtiene los grupos de un tutor por su correo electrónico
    @Override
    public List<GroupConfigurationViewResponse> getAllGroupConfigurationViewByTutorEmail(String tutorEmail) {
        return groupConfigurationViewMapper.toResponseList(groupConfigurationRepository.findByTutor_Person_User_Email(tutorEmail));
    }
}
