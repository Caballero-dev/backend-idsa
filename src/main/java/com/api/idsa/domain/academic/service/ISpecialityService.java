package com.api.idsa.domain.academic.service;

import com.api.idsa.common.exception.DuplicateResourceException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.academic.dto.request.SpecialityRequest;
import com.api.idsa.domain.academic.dto.response.SpecialityResponse;

import java.util.List;

public interface ISpecialityService {

    /**
     * Ontiene todas las especialidades registradas.
     *
     * @return Lista de {@link SpecialityResponse} con todas las especialidades.
     */
    List<SpecialityResponse> findAll();

    /**
     * Crea una nueva especialidad.
     *
     * @param specialityRequest Objeto que contiene la información de la especialidad a crear.
     * @return {@link SpecialityResponse} con la información de la especialidad creada.
     * @throws DuplicateResourceException si ya existe una especialidad con el mismo nombre o abreviatura.
     */
    SpecialityResponse createEspeciality(SpecialityRequest specialityRequest) throws DuplicateResourceException;

    /**
     * Actualiza los datos de una especialidad existente.
     *
     * @param specialityId      ID de la especialidad a actualizar.
     * @param specialityRequest Objeto que contiene la información actualizada de la especialidad.
     * @return {@link SpecialityResponse} con la información de la especialidad actualizada.
     * @throws ResourceNotFoundException  si la especialidad no existe.
     * @throws DuplicateResourceException si ya existe una especialidad con el mismo nombre o abreviatura.
     */
    SpecialityResponse updateEspeciality(Long specialityId, SpecialityRequest specialityRequest) throws ResourceNotFoundException, DuplicateResourceException;

    /**
     * Elimina una especialidad existente.
     *
     * @param specialityId ID de la especialidad a eliminar.
     * @throws ResourceNotFoundException si la especialidad no existe.
     */
    void deleteEspeciality(Long specialityId) throws ResourceNotFoundException;

}
