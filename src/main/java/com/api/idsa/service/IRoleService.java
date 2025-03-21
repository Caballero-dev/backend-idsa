package com.api.idsa.service;

import com.api.idsa.dto.response.RoleResponse;

import java.util.List;

public interface IRoleService {

    /**
     * Obtiene todos los roles disponibles.
     *
     * @return Lista de {@link RoleResponse} que representan los roles disponibles.
     */
    List<RoleResponse> findAll();

    /**
     * Obtiene los roles diferentes de "ROLE_ADMIN".
     *
     * @return Lista de {@link RoleResponse} que representan los roles diferentes de "ROLE_ADMIN".
     */
    List<RoleResponse> findAllExceptAdmin();

}
