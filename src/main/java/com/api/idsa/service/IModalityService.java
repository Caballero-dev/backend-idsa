package com.api.idsa.service;

import com.api.idsa.dto.request.ModalityRequest;
import com.api.idsa.dto.response.ModalityResponse;
import com.api.idsa.exception.DuplicateResourceException;
import com.api.idsa.exception.ResourceNotFoundException;

import java.util.List;

public interface IModalityService {

    /**
     * Obtiene todas las modalidades
     *
     * @return Lista de {@link ModalityResponse} con la información de las modalidades.
     */
    List<ModalityResponse> findAll();

    /**
     * Crea una modalidad
     *
     * @param modalityRequest Objeto que contiene la información de la modalidad a crear.
     * @return {@link ModalityResponse} con la información de la modalidad creada.
     * @throws DuplicateResourceException si ya existe una modalidad con el mismo nombre.
     */
    ModalityResponse createModality(ModalityRequest modalityRequest) throws DuplicateResourceException;

    /**
     * Actualiza los datos de una modalidad existente.
     *
     * @param modalityId ID de la modalidad a actualizar.
     * @param modalityRequest Objeto que contiene la información actualizada de la modalidad.
     * @return {@link ModalityResponse} con la información de la modalidad actualizada.
     * @throws DuplicateResourceException si ya existe una modalidad con el mismo nombre.
     * @throws ResourceNotFoundException si la modalidad no existe.
     * @throws DuplicateResourceException si ya existe una modalidad con el mismo nombre.
     */
    ModalityResponse updateModality(Long modalityId, ModalityRequest modalityRequest) throws ResourceNotFoundException, DuplicateResourceException;

    /**
     * Elimina una modalidad existente.
     *
     * @param modalityId ID de la modalidad a eliminar.
     * @throws ResourceNotFoundException si la modalidad no existe.
     */
    void deleteModality(Long modalityId) throws ResourceNotFoundException;

}
