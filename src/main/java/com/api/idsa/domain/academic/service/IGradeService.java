package com.api.idsa.domain.academic.service;

import com.api.idsa.common.exception.DuplicateResourceException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.academic.dto.request.GradeRequest;
import com.api.idsa.domain.academic.dto.response.GradeResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IGradeService {

    /**
     * Obtiene todos los grados registrados con opción de búsqueda.
     *
     * @param pageable Configuración de paginación.
     * @param search Término de búsqueda opcional para filtrar por nombre del grado.
     * @return Página de {@link GradeResponse} con la información de los grados.
     */
    Page<GradeResponse> getAllGrade(Pageable pageable, String search);

    /**
     * Crea un grado.
     *
     * @param gradeRequest Objeto que contiene la información del grado a crear.
     * @return {@link GradeResponse} con la información del grado creado.
     * @throws DuplicateResourceException si ya existe un grado con el mismo nombre.
     */
    GradeResponse createGrade(GradeRequest gradeRequest);

    /**
     * Actualiza los datos de un grado existente.
     *
     * @param gradeId      ID del grado a actualizar.
     * @param gradeRequest Objeto que contiene la información actualizada del grado.
     * @return {@link GradeResponse} con la información del grado actualizado.
     * @throws ResourceNotFoundException si el grado no existe.
     * @throws DuplicateResourceException si ya existe un grado con el mismo nombre.
     */
    GradeResponse updateGrade(UUID gradeId, GradeRequest gradeRequest);

    /**
     * Elimina un grado existente.
     *
     * @param gradeId ID del grado a eliminar.
     * @throws ResourceNotFoundException si el grado no existe.
     */
    void deleteGrade(UUID gradeId);

}
