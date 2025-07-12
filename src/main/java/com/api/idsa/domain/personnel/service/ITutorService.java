package com.api.idsa.domain.personnel.service;

import com.api.idsa.common.exception.DuplicateResourceException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.personnel.dto.request.TutorRequest;
import com.api.idsa.domain.personnel.dto.response.TutorResponse;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITutorService {

    /**
     * Obtiene todos los tutores registrados con opción de búsqueda.
     *
     * @param pageable Configuración de paginación.
     * @param search Término de búsqueda opcional para filtrar tutores por nombre, apellidos,
     *              correo electrónico, número de teléfono o código de empleado.
     * @return Página de {@link TutorResponse} con la información filtrada de los tutores.
     */
    Page<TutorResponse> getAllTutor(Pageable pageable, String search);

    /**
     * Crea un tutor.
     *
     * @param tutorRequest Objeto que contiene la información del tutor a crear.
     * @return {@link TutorResponse} con la información del tutor creado.
     * @throws DuplicateResourceException si ya existe un tutor con el mismo correo electrónico, número de teléfono o número de empleado.
     * @throws ResourceNotFoundException  si el rol del tutor no existe.
     */
    TutorResponse createTutor(TutorRequest tutorRequest);

    /**
     * Actualiza los datos de un tutor existente.
     *
     * @param tutorId      ID del tutor a actualizar.
     * @param tutorRequest Objeto que contiene la información actualizada del tutor.
     * @return {@link TutorResponse} con la información del tutor actualizado.
     * @throws DuplicateResourceException si ya existe un tutor con el mismo correo electrónico, número de teléfono o número de empleado.
     * @throws ResourceNotFoundException  si el tutor no existe.
     */
    TutorResponse updateTutor(UUID tutorId, TutorRequest tutorRequest);

    /**
     * Elimina un tutor existente.
     *
     * @param tutorId ID del tutor a eliminar.
     * @throws ResourceNotFoundException si el tutor no existe.
     */
    void deleteTutor(UUID tutorId);

}
