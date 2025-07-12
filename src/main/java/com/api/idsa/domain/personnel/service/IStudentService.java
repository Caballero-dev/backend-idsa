package com.api.idsa.domain.personnel.service;

import com.api.idsa.common.exception.DuplicateResourceException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.personnel.dto.request.StudentRequest;
import com.api.idsa.domain.personnel.dto.response.StudentResponse;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IStudentService {

    /**
     * Obtiene una lista de estudiantes.
     *
     * @param pageable Configuración de paginación.
     * @param search Término de búsqueda opcional para filtrar estudiantes por nombre, apellidos,
     *              número de teléfono o código de estudiante.
     * @return Lista de  {@link StudentResponse} con la información de los estudiantes.
     */
    Page<StudentResponse> getAllStudent(Pageable pageable, String search);

    /**
     * Obtiene una lista de estudiantes por groupConfigurationId con posibilidad de búsqueda.
     *
     * @param groupConfigurationId ID de la configuración del grupo.
     * @param pageable Configuración de paginación.
     * @param search Término de búsqueda opcional para filtrar estudiantes por nombre, apellidos,
     *              número de teléfono o código de estudiante.
     * @return Lista de {@link StudentResponse} con la información de los estudiantes.
     */
    Page<StudentResponse> getStudentsByGroupConfigurationId(UUID groupConfigurationId, String search ,Pageable pageable);

    /**
     * Crea un nuevo estudiante.
     *
     * @param studentRequest Objeto que contiene la información del estudiante a crear.
     * @return {@link StudentResponse} con la información del estudiante creado.
     * @throws DuplicateResourceException si ya existe un estudiante con el mismo código, email o número de teléfono.
     * @throws ResourceNotFoundException si la configuración del grupo no existe.
     */
    StudentResponse createStudent(StudentRequest studentRequest, UUID groupConfigurationId);

    /**
     * Actualiza la información de un estudiante existente.
     *
     * @param studentId      ID del estudiante a actualizar.
     * @param studentRequest Objeto que contiene la información actualizada del estudiante.
     * @return {@link StudentResponse} con la información del estudiante actualizado.
     * @throws DuplicateResourceException si ya existe un estudiante con el mismo código, email o número de teléfono.
     * @throws ResourceNotFoundException si el estudiante no existe.
     */
    StudentResponse updateStudent(UUID studentId, StudentRequest studentRequest);

    /**
     * Elimina un estudiante existente.
     *
     * @param studentId ID del estudiante a eliminar.
     * @throws ResourceNotFoundException si el estudiante no existe.
     */
    void deleteStudent(UUID studentId);

}
