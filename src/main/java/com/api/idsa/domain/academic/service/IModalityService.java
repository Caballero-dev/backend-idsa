package com.api.idsa.domain.academic.service;

import com.api.idsa.common.exception.DuplicateResourceException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.academic.dto.request.ModalityRequest;
import com.api.idsa.domain.academic.dto.response.ModalityResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IModalityService {

    /**
     * Obtiene todas las modalidades registradas con opción de búsqueda.
     *
     * @param pageable Configuración de paginación.
     * @param search Término de búsqueda opcional para filtrar por nombre de la modalidad.
     * @return Página de {@link ModalityResponse} con la información de las modalidades.
     */
    Page<ModalityResponse> getAllModality(Pageable pageable, String search);

    /**
     * Crea una modalidad
     *
     * @param modalityRequest Objeto que contiene la información de la modalidad a crear.
     * @return {@link ModalityResponse} con la información de la modalidad creada.
     * @throws DuplicateResourceException si ya existe una modalidad con el mismo nombre.
     */
    ModalityResponse createModality(ModalityRequest modalityRequest);

    /**
     * Actualiza los datos de una modalidad existente.
     *
     * @param modalityId ID de la modalidad a actualizar.
     * @param modalityRequest Objeto que contiene la información actualizada de la modalidad.
     * @return {@link ModalityResponse} con la información de la modalidad actualizada.
     * @throws ResourceNotFoundException si la modalidad no existe.
     * @throws DuplicateResourceException si ya existe una modalidad con el mismo nombre.
     */
    ModalityResponse updateModality(UUID modalityId, ModalityRequest modalityRequest);

    /**
     * Elimina una modalidad existente.
     *
     * @param modalityId ID de la modalidad a eliminar.
     * @throws ResourceNotFoundException si la modalidad no existe.
     */
    void deleteModality(UUID modalityId);

}
