package com.api.idsa.domain.personnel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.idsa.domain.personnel.dto.response.RoleResponse;
import com.api.idsa.domain.personnel.service.IRoleService;

import java.util.List;

@RestController
@RequestMapping("/admin/roles")
public class RoleController {

    @Autowired
    IRoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoleExceptAdmin());
    }

}
