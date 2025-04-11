package com.api.idsa.domain.personnel.service;

import java.util.List;

import com.api.idsa.domain.personnel.dto.response.RoleResponse;

public interface IRoleService {

    /**
     * Obtiene todos los roles disponibles.
     *
     * @return Lista de {@link RoleResponse} que representan los roles disponibles.
     */
    List<RoleResponse> getAllRole();

    /**
     * Obtiene los roles diferentes de "ROLE_ADMIN".
     *
     * @return Lista de {@link RoleResponse} que representan los roles diferentes de "ROLE_ADMIN".
     */
    List<RoleResponse> getAllRoleExceptAdmin();

}
