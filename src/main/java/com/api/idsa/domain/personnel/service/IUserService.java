package com.api.idsa.domain.personnel.service;

import com.api.idsa.common.exception.DuplicateResourceException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.common.exception.UserRoleCreationDeniedException;
import com.api.idsa.domain.personnel.dto.request.UserRequest;
import com.api.idsa.domain.personnel.dto.response.UserResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {

    /**
     * Obtiene todos los usuarios excepto aquellos con el rol "ROLE_ADMIN".
     *
     * @return Lista de {@link UserResponse} con la información de los usuarios.
     */
    Page<UserResponse> getAllUserExceptAdmin(Pageable pageable);

    /**
     * Crea un nuevo usuario.
     *
     * @param userRequest Objeto que contiene la información del usuario a crear.
     * @return {@link UserResponse} con la información del usuario creado.
     * @throws DuplicateResourceException si ya existe un usuario con el mismo correo electrónico, número de teléfono y número de empleado.
     * @throws UserRoleCreationDeniedException si se intenta crear un usuario con rol no permitido.
     * @throws ResourceNotFoundException si el rol no existe.
     */
    UserResponse createUser(UserRequest userRequest);

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param userId      ID del usuario a actualizar.
     * @param userRequest Objeto que contiene la información actualizada del usuario.
     * @return {@link UserResponse} con la información del usuario actualizado.
     * @throws ResourceNotFoundException  si el usuario no existe.
     * @throws DuplicateResourceException si ya existe un usuario con el mismo correo electrónico, número de teléfono y número de empleado.
     * @throws UserRoleCreationDeniedException si se intenta actualizar un usuario con rol no permitido.
     */
    // Nota: es opcinal actualizar la contraseña
    UserResponse updateUser(Long userId, boolean isUpdatePassword, UserRequest updateUserRequest);

    /**
     * Actualiza el estado de un usuario existente a activo o inactivo.
     * 
     * @param userId ID del usuario a actualizar.
     * @param isActive Estado a establecer (true para activo, false para inactivo).
     * @throws ResourceNotFoundException si el usuario no existe.
     */
    void updateUserStatus(Long userId, boolean isActive);

    /**
     * Elimina un usuario existente.
     *
     * @param userId ID del usuario a eliminar.
     * @throws ResourceNotFoundException si el usuario no existe.
     */
    void deleteUser(Long userId);

}
