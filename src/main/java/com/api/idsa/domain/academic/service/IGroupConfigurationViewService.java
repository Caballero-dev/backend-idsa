package com.api.idsa.domain.academic.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.api.idsa.domain.academic.dto.response.GroupConfigurationViewResponse;

public interface IGroupConfigurationViewService {

    /**
     * Obtiene una lista de vista de configuraciones de grupos.
     *
     * @return Lista de {@link GroupConfigurationViewResponse} que representan las configuraciones de grupos.
     */
    Page<GroupConfigurationViewResponse> getAllGroupConfigurationView(Pageable pageable);

    /**
     * Obtiene una lista de vista de configuraciones de grupos por numéro de empleado del tutor.
     *
     * @param employeeCode El código del empleado del tutor.
     * @return Lista de {@link GroupConfigurationViewResponse} que representan las configuraciones de grupos del tutor.
     */
    Page<GroupConfigurationViewResponse> getAllGroupConfigurationViewByTutor(String employeeCode, Pageable pageable);

    /**
     * Obtiene una lista de vista de configuraciones de grupos por correo electrónico del tutor.
     *
     * @param tutorEmail El correo electrónico del tutor.
     * @return Lista de {@link GroupConfigurationViewResponse} que representan las configuraciones de grupos del tutor.
     */
    Page<GroupConfigurationViewResponse> getAllGroupConfigurationViewByTutorEmail(String tutorEmail, Pageable pageable);

}

