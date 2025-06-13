package com.api.idsa.domain.personnel.service;

import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.personnel.dto.request.UpdatePasswordRequest;
import com.api.idsa.domain.personnel.dto.response.UserProfileResponse;

public interface IUserProfileService {

    /**
     * Obtiene el perfil de usuario por correo electrónico.
     *
     * @return  {@link UserProfileResponse} con la información del perfil de usuario.
     * @throws ResourceNotFoundException si no se encuentra el usuario.
     */
    UserProfileResponse getUserProfile();

    /**
     * Actualiza la contraseña del usuario.
     *
     * @param request Objeto que contiene la información de la contraseña a actualizar.
     * @throws ResourceNotFoundException si no se encuentra el usuario.
     */
    void updatePassword(UpdatePasswordRequest request);

}
