package com.api.idsa.domain.academic.service;

import com.api.idsa.common.exception.DuplicateResourceException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.academic.dto.request.GroupRequest;
import com.api.idsa.domain.academic.dto.response.GroupResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IGroupService {

    /**
     * Obtiene todos los grupos registrados con opción de búsqueda.
     *
     * @param pageable Configuración de paginación.
     * @param search Término de búsqueda opcional para filtrar por nombre del grupo.
     * @return Página de {@link GroupResponse} con la información de los grupos.
     */
    Page<GroupResponse> getAllGroup(Pageable pageable, String search);

    /**
     * Crea un grupo.
     *
     * @param groupRequest Objeto que contiene la información del grupo a crear.
     * @return {@link GroupResponse} con la información del grupo creado.
     * @throws DuplicateResourceException si ya existe un grupo con el mismo nombre.
     */
    GroupResponse createGroup(GroupRequest groupRequest);

    /**
     * Actualiza los datos de un grupo existente.
     *
     * @param groupId      ID del grupo a actualizar.
     * @param groupRequest Objeto que contiene la información actualizada del grupo.
     * @return {@link GroupResponse} con la información del grupo actualizado.
     * @throws ResourceNotFoundException si el grupo no existe.
     * @throws DuplicateResourceException si ya existe un grupo con el mismo nombre.
     */
    GroupResponse updateGroup(UUID groupId, GroupRequest groupRequest);

    /**
     * Elimina un grupo existente.
     *
     * @param groupId ID del grupo a eliminar.
     * @throws ResourceNotFoundException si el grupo no existe.
     */
    void deleteGroup(UUID groupId);

}
