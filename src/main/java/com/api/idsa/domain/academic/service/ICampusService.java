package com.api.idsa.domain.academic.service;

import com.api.idsa.common.exception.DuplicateResourceException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.academic.dto.request.CampusRequest;
import com.api.idsa.domain.academic.dto.response.CampusResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ICampusService {

    /**
     * Obtiene todas los campus registrados.
     *
     * @return Lista de {@link CampusResponse} con la información de los campus.
     */
    Page<CampusResponse> getAllCampus(Pageable pageable);

    /**
     * Crea un campus.
     *
     * @param campusRequest Objeto que contiene la información del campus a crear.
     * @return {@link CampusResponse} con la información del campus creado.
     * @throws DuplicateResourceException si ya existe un campus con el mismo nombre.
     */
    CampusResponse createCampus(CampusRequest campusRequest);

    /**
     * Actualiza los datos de un campus existente.
     *
     * @param campusId      ID del campus a actualizar.
     * @param campusRequest Objeto que contiene la información actualizada del campus.
     * @return {@link CampusResponse} con la información del campus actualizado.
     * @throws ResourceNotFoundException si el campus no existe.
     * @throws DuplicateResourceException si ya existe un campus con el mismo nombre.
     */
    CampusResponse updateCampus(Long campusId, CampusRequest campusRequest);

    /**
     * Elimina un campus existente.
     *
     * @param campusId ID del campus a eliminar.
     * @throws ResourceNotFoundException si el campus no existe.
     */
    void deleteCampus(Long campusId);

}
