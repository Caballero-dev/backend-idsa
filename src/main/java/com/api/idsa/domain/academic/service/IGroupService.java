package com.api.idsa.domain.academic.service;

import com.api.idsa.common.exception.DuplicateResourceException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.academic.dto.request.GroupRequest;
import com.api.idsa.domain.academic.dto.response.GroupResponse;

import java.util.List;

public interface IGroupService {

    /**
     * Obtiene todos los grupos registrados.
     *
     * @return Lista de {@link GroupResponse} con la información de los grupos.
     */
    List<GroupResponse> findAll();

    /**
     * Crea un grupo.
     *
     * @param groupRequest Objeto que contiene la información del grupo a crear
     * @return {@link GroupResponse} con la información del grupo creado
     * @throws DuplicateResourceException si ya existe un grupo con el mismo nombre
     */
    GroupResponse createGroup(GroupRequest groupRequest) throws DuplicateResourceException;

    /**
     * Actualiza los datos de un grupo existente.
     *
     * @param groupId      ID del grupo a actualizar
     * @param groupRequest Objeto que contiene la información actualizada del grupo
     * @return {@link GroupResponse} con la información del grupo actualizado
     * @throws ResourceNotFoundException  si el grupo no existe
     * @throws DuplicateResourceException si ya existe un grupo con el mismo nombre
     */
    GroupResponse updateGroup(Long groupId, GroupRequest groupRequest) throws ResourceNotFoundException, DuplicateResourceException;

    /**
     * Elimina un grupo existente.
     *
     * @param groupId ID del grupo a eliminar
     * @throws ResourceNotFoundException si el grupo no existe
     */
    void deleteGroup(Long groupId) throws ResourceNotFoundException;

}
