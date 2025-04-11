package com.api.idsa.domain.academic.service;

import com.api.idsa.common.exception.DuplicateResourceException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.academic.dto.request.SpecialtyRequest;
import com.api.idsa.domain.academic.dto.response.SpecialtyResponse;

import java.util.List;

public interface ISpecialtyService {

    /**
     * Obtiene todas las especialidades registradas.
     *
     * @return Lista de {@link SpecialtyResponse} con todas las especialidades.
     */
    List<SpecialtyResponse> getAllSpecialty();

    /**
     * Crea una nueva especialidad.
     *
     * @param specialtyRequest Objeto que contiene la información de la especialidad a crear.
     * @return {@link SpecialtyResponse} con la información de la especialidad creada.
     * @throws DuplicateResourceException si ya existe una especialidad con el mismo nombre o abreviatura.
     */
    SpecialtyResponse createSpecialty(SpecialtyRequest specialtyRequest);

    /**
     * Actualiza los datos de una especialidad existente.
     *
     * @param specialtyId      ID de la especialidad a actualizar.
     * @param specialtyRequest Objeto que contiene la información actualizada de la especialidad.
     * @return {@link SpecialtyResponse} con la información de la especialidad actualizada.
     * @throws ResourceNotFoundException  si la especialidad no existe.
     * @throws DuplicateResourceException si ya existe una especialidad con el mismo nombre o abreviatura.
     */
    SpecialtyResponse updateSpecialty(Long specialtyId, SpecialtyRequest specialtyRequest);

    /**
     * Elimina una especialidad existente.
     *
     * @param specialtyId ID de la especialidad a eliminar.
     * @throws ResourceNotFoundException si la especialidad no existe.
     */
    void deleteSpecialty(Long specialtyId);

}
