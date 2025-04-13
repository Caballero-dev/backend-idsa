package com.api.idsa.domain.academic.service;

import com.api.idsa.common.exception.DuplicateResourceException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.academic.dto.request.GenerationRequest;
import com.api.idsa.domain.academic.dto.response.GenerationResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IGenerationService {

    /**
     * Obtiene todas las generaciones registradas.
     *
     * @return Lista de {@link GenerationResponse} con la información de las generaciones.
     */
    Page<GenerationResponse> getAllGeneration(Pageable pageable);

    /**
     * Crea una nueva generación.
     *
     * @param generationRequest Objeto que contiene la información de la generación a crear.
     * @return {@link GenerationResponse} con la información de la generación creada.
     * @throws DuplicateResourceException si ya existe una generación con las mismas fechas.
     */
    GenerationResponse createGeneration(GenerationRequest generationRequest);

    /**
     * Actualiza los datos de una generación existente.
     *
     * @param generationId ID de la generación a actualizar.
     * @param request      Objeto que contiene la información actualizada de la generación.
     * @return {@link GenerationResponse} con la información de la generación actualizada.
     * @throws ResourceNotFoundException si la generación no existe.
     * @throws DuplicateResourceException si ya existe una generación con las mismas fechas.
     */
    GenerationResponse updateGeneration(Long generationId, GenerationRequest generationRequest);

    /**
     * Elimina una generación existente.
     *
     * @param generationId ID de la generación a eliminar.
     * @throws ResourceNotFoundException si la generación no existe.
     */
    void deleteGeneration(Long generationId);

}
