package com.api.idsa.domain.academic.service;

import com.api.idsa.common.exception.DuplicateResourceException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.academic.dto.request.GroupConfigurationRequest;
import com.api.idsa.domain.academic.dto.response.GroupConfigurationResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IGroupConfigurationService {

    /**
     * Obtiene todas las configuraciones de grupos registradas con opción de búsqueda.
     *
     * @param pageable Configuración de paginación.
     * @param search Término de búsqueda opcional para filtrar por diferentes campos.
     * @return Página de {@link GroupConfigurationResponse} con la información de las configuraciones.
     */
    Page<GroupConfigurationResponse> getAllGroupConfiguration(Pageable pageable, String search);

    /**
     * Crea una configuración de grupo.
     *
     * @param groupConfigurationRequest Objeto que contiene la información de la configuración a crear.
     * @return {@link GroupConfigurationResponse} con la información de la configuración creada.
     * @throws DuplicateResourceException si ya existe una configuración con los mismos parámetros.
     */
    GroupConfigurationResponse createGroupConfiguration(GroupConfigurationRequest groupConfigurationRequest);

    /**
     * Actualiza los datos de una configuración de grupo existente.
     *
     * @param groupConfigurationId      ID de la configuración a actualizar.
     * @param groupConfigurationRequest Objeto que contiene la información actualizada de la configuración.
     * @return {@link GroupConfigurationResponse} con la información de la configuración actualizada.
     * @throws ResourceNotFoundException si la configuración no existe.
     * @throws DuplicateResourceException si ya existe una configuración con los mismos parámetros.
     */
    GroupConfigurationResponse updateGroupConfiguration(UUID groupConfigurationId, GroupConfigurationRequest groupConfigurationRequest);

    /**
     * Elimina una configuración de grupo existente.
     *
     * @param groupConfigurationId ID de la configuración a eliminar.
     * @throws ResourceNotFoundException si la configuración no existe.
     */
    void deleteGroupConfiguration(UUID groupConfigurationId);

}
