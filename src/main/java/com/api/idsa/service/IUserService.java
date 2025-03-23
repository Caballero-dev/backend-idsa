package com.api.idsa.service;

import com.api.idsa.dto.request.UpdateUserRequest;
import com.api.idsa.dto.request.UserRequest;
import com.api.idsa.dto.response.UserResponse;
import com.api.idsa.exception.DuplicateResourceException;
import com.api.idsa.exception.ResourceNotFoundException;
import com.api.idsa.exception.UserRoleCreationDeniedException;

import java.util.List;

public interface IUserService {

    /**
     * Obtiene todos los usuarios.
     *
     * @return Lista de {@link UserResponse} con la información de los usuarios.
     */
    List<UserResponse> findAll();

    /**
     * Obtiene los usuarios activos diferentes de "ROLE_ADMIN".
     *
     * @return Lista de {@link UserResponse} con la información de los usuarios activos.
     */
    List<UserResponse> findAllActiveExceptAdmin();

    /**
     * Obtiene ls usuarios inactivos diferentes de "ROLE_ADMIN".
     *
     * @return Lista de {@link UserResponse} con la información de los usuarios inactivos.
     */
    List<UserResponse> findAllInactiveExceptAdmin();

    /**
     * Crea un nuevo usuario.
     *
     * @param userRequest Objeto que contiene la información del usuario a crear.
     * @return {@link UserResponse} con la información del usuario creado.
     * @throws DuplicateResourceException si ya existe un usuario con el mismo correo electrónico, número de teléfono y número de empleado.
     * @throws UserRoleCreationDeniedException si se intenta crear un usuario con rol no permitido.
     * @throws ResourceNotFoundException si el rol no existe.
     */
    UserResponse createUser(UserRequest userRequest) throws DuplicateResourceException, UserRoleCreationDeniedException, ResourceNotFoundException;

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param userId      ID del usuario a actualizar.
     * @param userRequest Objeto que contiene la información actualizada del usuario.
     * @return {@link UserResponse} con la información del usuario actualizado.
     * @throws ResourceNotFoundException  si el usuario no existe.
     * @throws DuplicateResourceException si ya existe un usuario con el mismo correo electrónico, número de teléfono y número de empleado.
     */
    // Nota: es opcinal actualizar la contraseña
    UserResponse updateUser(Long userId, boolean isUpdatePassword, UpdateUserRequest updateUserRequest) throws ResourceNotFoundException, DuplicateResourceException, UserRoleCreationDeniedException;

    /**
     * Elimina un usuario existente.
     *
     * @param userId ID del usuario a eliminar.
     * @throws ResourceNotFoundException si el usuario no existe.
     */
    void deleteUser(Long userId) throws ResourceNotFoundException;

}
