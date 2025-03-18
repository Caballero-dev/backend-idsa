package com.api.idsa.service;

import com.api.idsa.dto.request.GenerationRequest;
import com.api.idsa.dto.response.GenerationResponse;
import com.api.idsa.exception.DuplicateResourceException;
import com.api.idsa.exception.ResourceNotFoundException;

import java.util.List;

public interface IGenerationService {

    /**
     * Obtiene todas las generaciones registradas.
     *
     * @return Lista de {@link GenerationResponse} con la información de las generaciones.
     */
    List<GenerationResponse> findAll();

    /**
     * Crea una nueva generación.
     *
     * @param generationRequest Objeto que contiene la información de la generación a crear.
     * @return {@link GenerationResponse} con la información de la generación creada.
     * @throws DuplicateResourceException si ya existe una generación con las mismas fechas.
     */
    GenerationResponse createGeneration(GenerationRequest generationRequest) throws DuplicateResourceException;

    /**
     * Actualiza los datos de una generación existente.
     *
     * @param generationId ID de la generación a actualizar.
     * @param request      Objeto que contiene la información actualizada de la generación.
     * @return {@link GenerationResponse} con la información de la generación actualizada.
     * @throws ResourceNotFoundException si la generación no existe.
     */
    GenerationResponse updateGeneration(Long generationId, GenerationRequest generationRequest) throws DuplicateResourceException, ResourceNotFoundException;

    /**
     * Elimina una generación existente.
     *
     * @param generationId ID de la generación a eliminar.
     * @throws ResourceNotFoundException si la generación no existe.
     */
    void deleteGeneration(Long generationId) throws ResourceNotFoundException;
}
