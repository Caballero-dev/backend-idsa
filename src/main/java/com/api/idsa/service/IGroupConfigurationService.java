package com.api.idsa.service;

import com.api.idsa.dto.request.GroupConfigurationRequest;
import com.api.idsa.dto.response.GroupConfigurationResponse;
import com.api.idsa.exception.DuplicateResourceException;
import com.api.idsa.exception.ResourceNotFoundException;

import java.util.List;

public interface IGroupConfigurationService {

    /**
     * Obtiene la lista de configuraciones de grupos.
     *
     * @return Lista de {@link GroupConfigurationResponse} con la información de las configuraciones de grupos.
     */
    List<GroupConfigurationResponse> findAll();

    /**
     * Crea una nueva configuración de grupo.
     *
     * @param groupConfigurationRequest Objeto que contiene la información de la configuración de grupo a crear.
     * @return {@link GroupConfigurationResponse} con la información de la configuración de grupo creada.
     * @throws DuplicateResourceException si ya existe una configuración de grupo con los mismos datos.
     */
    GroupConfigurationResponse createGroupConfiguration(GroupConfigurationRequest groupConfigurationRequest) throws DuplicateResourceException;

    /**
     * Actualiza una configuración de grupo existente.
     *
     * @param groupConfigurationId      ID de la configuración de grupo a actualizar.
     * @param groupConfigurationRequest Objeto que contiene la información actualizada de la configuración de grupo.
     * @return {@link GroupConfigurationResponse} con la información de la configuración de grupo actualizada.
     * @throws ResourceNotFoundException  si la configuración de grupo no existe.
     * @throws DuplicateResourceException si ya existe una configuración de grupo con los mismos datos.
     */
    GroupConfigurationResponse updateGroupConfiguration(Long groupConfigurationId, GroupConfigurationRequest groupConfigurationRequest) throws ResourceNotFoundException, DuplicateResourceException;

    /**
     * Elimina una configuración de grupo existente.
     *
     * @param groupConfigurationId ID de la configuración de grupo a eliminar.
     * @throws ResourceNotFoundException si la configuración de grupo no existe.
     */
    void deleteGroupConfiguration(Long groupConfigurationId) throws ResourceNotFoundException;

}
