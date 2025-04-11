package com.api.idsa.domain.academic.service;

import java.util.List;

import com.api.idsa.domain.academic.dto.response.GroupConfigurationViewResponse;

public interface IGroupConfigurationViewService {

    /**
     * Obtiene una lista de vista de configuraciones de grupos.
     *
     * @return Lista de {@link GroupConfigurationViewResponse} que representan las configuraciones de grupos.
     */
    List<GroupConfigurationViewResponse> getAllGroupConfigurationView();

    /**
     * Obtiene una lista de vista de configuraciones de grupos por numéro de empleado del tutor.
     *
     * @param employeeCode El código del empleado del tutor.
     * @return Lista de {@link GroupConfigurationViewResponse} que representan las configuraciones de grupos del tutor.
     */
    List<GroupConfigurationViewResponse> getAllGroupConfigurationViewByTutor(String employeeCode);

    /**
     * Obtiene una lista de vista de configuraciones de grupos por correo electrónico del tutor.
     *
     * @param personUserEmail El correo electrónico del tutor.
     * @return Lista de {@link GroupConfigurationViewResponse} que representan las configuraciones de grupos del tutor.
     */
    List<GroupConfigurationViewResponse> getAllGroupConfigurationViewByTutorEmail(String personUserEmail);

}

