package com.api.idsa.domain.academic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.idsa.domain.academic.dto.response.GroupConfigurationViewResponse;
import com.api.idsa.domain.academic.service.IGroupConfigurationViewService;

import java.util.List;

@RestController
@RequestMapping("/group-configuration-view")
public class GroupConfigurationViewController {

    @Autowired
    IGroupConfigurationViewService groupConfigurationViewService;

    // Endpoint para admin puede ver todas las configuraciones de grupos
    @GetMapping
    public List<GroupConfigurationViewResponse> getGroupConfigurationViewList() {
        return groupConfigurationViewService.getGroupConfigurationViewList();
    }

    // Endpoint para tutor puede ver sus configuraciones de grupos por numero de empleado
    @GetMapping("/by-employee-code/{employeeCode}")
    public List<GroupConfigurationViewResponse> getGroupConfigurationViewListByTutor(@PathVariable String employeeCode) {
        return groupConfigurationViewService.getGroupConfigurationViewListByTutor(employeeCode);
    }

    @GetMapping("/by-tutor-email/{personUserEmail}")
    public List<GroupConfigurationViewResponse> getGroupConfigurationViewListByTutorEmail(@PathVariable String personUserEmail) {
        return groupConfigurationViewService.getGroupConfigurationViewListByTutorEmail(personUserEmail);
    }

}
