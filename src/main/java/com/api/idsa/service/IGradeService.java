package com.api.idsa.service;

import com.api.idsa.dto.request.GradeRequest;
import com.api.idsa.dto.response.GradeResponse;
import com.api.idsa.exception.DuplicateResourceException;
import com.api.idsa.exception.ResourceNotFoundException;

import java.util.List;

public interface IGradeService {

    /**
     * Obtiene todos los grados registrados.
     *
     * @return Lista de {@link GradeResponse} con la información de los grados.
     */
    List<GradeResponse> findAll();

    /**
     * Crea un grado.
     *
     * @param gradeRequest Objeto que contiene la información del grado a crear.
     * @return {@link GradeResponse} con la información del grado creado.
     * @throws DuplicateResourceException si ya existe un grado con el mismo nombre.
     */
    GradeResponse create(GradeRequest gradeRequest) throws DuplicateResourceException;

    /**
     * Actualiza los datos de un grado existente.
     *
     * @param gradeId      ID del grado a actualizar.
     * @param gradeRequest Objeto que contiene la información actualizada del grado.
     * @return {@link GradeResponse} con la información del grado actualizado.
     * @throws ResourceNotFoundException  si el grado no existe.
     * @throws DuplicateResourceException si ya existe un grado con el mismo nombre.
     */
    GradeResponse update(Long gradeId, GradeRequest gradeRequest) throws ResourceNotFoundException, DuplicateResourceException;

    /**
     * Elimina un grado existente.
     *
     * @param gradeId ID del grado a eliminar.
     * @throws ResourceNotFoundException si el grado no existe.
     */
    void delete(Long gradeId) throws ResourceNotFoundException;

}
