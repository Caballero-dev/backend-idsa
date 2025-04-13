package com.api.idsa.domain.academic.service;

import com.api.idsa.common.exception.DuplicateResourceException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.academic.dto.request.GroupConfigurationRequest;
import com.api.idsa.domain.academic.dto.response.GroupConfigurationResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IGroupConfigurationService {

    /**
     * Obtiene la lista de configuraciones de grupos.
     *
     * @return Lista de {@link GroupConfigurationResponse} con la información de las configuraciones de grupos.
     */
    Page<GroupConfigurationResponse> getAllGroupConfiguration(Pageable pageable);

    /**
     * Crea una nueva configuración de grupo.
     *
     * @param groupConfigurationRequest Objeto que contiene la información de la configuración de grupo a crear.
     * @return {@link GroupConfigurationResponse} con la información de la configuración de grupo creada.
     * @throws DuplicateResourceException si ya existe una configuración de grupo con los mismos datos.
     */
    GroupConfigurationResponse createGroupConfiguration(GroupConfigurationRequest groupConfigurationRequest);

    /**
     * Actualiza una configuración de grupo existente.
     *
     * @param groupConfigurationId      ID de la configuración de grupo a actualizar.
     * @param groupConfigurationRequest Objeto que contiene la información actualizada de la configuración de grupo.
     * @return {@link GroupConfigurationResponse} con la información de la configuración de grupo actualizada.
     * @throws ResourceNotFoundException  si la configuración de grupo no existe.
     * @throws DuplicateResourceException si ya existe una configuración de grupo con los mismos datos.
     */
    GroupConfigurationResponse updateGroupConfiguration(Long groupConfigurationId, GroupConfigurationRequest groupConfigurationRequest);

    /**
     * Elimina una configuración de grupo existente.
     *
     * @param groupConfigurationId ID de la configuración de grupo a eliminar.
     * @throws ResourceNotFoundException si la configuración de grupo no existe.
     */
    void deleteGroupConfiguration(Long groupConfigurationId);

}
