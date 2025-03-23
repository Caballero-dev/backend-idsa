package com.api.idsa.service;

import com.api.idsa.dto.request.TutorRequest;
import com.api.idsa.dto.response.TutorResponse;
import com.api.idsa.exception.DuplicateResourceException;
import com.api.idsa.exception.ResourceNotFoundException;

import java.util.List;

public interface ITutorService {

    /**
     * Obtiene todos los tutores registrados.
     *
     * @return Lista de {@link TutorResponse} con la información de los tutores.
     */
    List<TutorResponse> findAll();

    /**
     * Crea un tutor.
     *
     * @param tutorRequest Objeto que contiene la información del tutor a crear.
     * @return {@link TutorResponse} con la información del tutor creado.
     * @throws DuplicateResourceException si ya existe un tutor con el mismo correo electrónico, número de teléfono o número de empleado.
     */
    TutorResponse createTutor(TutorRequest tutorRequest) throws DuplicateResourceException, ResourceNotFoundException;

    /**
     * Actualiza los datos de un tutor existente.
     *
     * @param tutorId      ID del tutor a actualizar.
     * @param tutorRequest Objeto que contiene la información actualizada del tutor.
     * @return {@link TutorResponse} con la información del tutor actualizado.
     * @throws DuplicateResourceException si ya existe un tutor con el mismo correo electrónico, número de teléfono o número de empleado.
     * @throws ResourceNotFoundException  si el tutor no existe.
     */
    TutorResponse updateTutor(Long tutorId, TutorRequest tutorRequest) throws ResourceNotFoundException, DuplicateResourceException;

    /**
     * Elimina un tutor existente.
     *
     * @param tutorId ID del tutor a eliminar.
     * @throws ResourceNotFoundException si el tutor no existe.
     */
    void deleteTutor(Long tutorId) throws ResourceNotFoundException;

}
