package com.api.idsa.service;

import com.api.idsa.dto.request.StudentRequest;
import com.api.idsa.dto.response.StudentResponse;
import com.api.idsa.exception.DuplicateResourceException;
import com.api.idsa.exception.ResourceNotFoundException;

import java.util.List;

public interface IStudentService {

    /**
     * Obtiene una lista de estudiantes.
     *
     * @return Lista de  {@link StudentResponse} con la información de los estudiantes.
     */
    List<StudentResponse> findAll();

    /**
     * Obtiene una lista de estudiantes por groupConfigurationId.
     *
     * @param groupConfigurationId ID de la configuración del grupo.
     * @return Lista de {@link StudentResponse} con la información de los estudiantes.
     */
    List<StudentResponse> findByGroupConfigurationId(Long groupConfigurationId);

    /**
     * Crea un nuevo estudiante.
     *
     * @param studentRequest Objeto que contiene la información del estudiante a crear.
     * @return {@link StudentResponse} con la información del estudiante creado.
     * @throws DuplicateResourceException si ya existe un estudiante con el mismo código, email o número de teléfono.
     */
    StudentResponse createStudent(StudentRequest studentRequest, Long groupConfigurationId) throws DuplicateResourceException, ResourceNotFoundException;

    /**
     * Actualiza la información de un estudiante existente.
     *
     * @param studentId      ID del estudiante a actualizar.
     * @param studentRequest Objeto que contiene la información actualizada del estudiante.
     * @return {@link StudentResponse} con la información del estudiante actualizado.
     * @throws DuplicateResourceException si ya existe un estudiante con el mismo código, email o número de teléfono.
     * @throws ResourceNotFoundException si el estudiante no existe.
     */
    StudentResponse updateStudent(Long studentId, StudentRequest studentRequest) throws DuplicateResourceException, ResourceNotFoundException;

    /**
     * Elimina un estudiante existente.
     *
     * @param studentId ID del estudiante a eliminar.
     * @throws ResourceNotFoundException si el estudiante no existe.
     */
    void deleteStudent(Long studentId) throws ResourceNotFoundException;

}
