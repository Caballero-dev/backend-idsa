package com.api.idsa.service;

import com.api.idsa.dto.response.GroupConfigurationViewResponse;

import java.util.List;

public interface IGroupConfigurationViewService {

    /**
     * Obtiene una lista de vista de configuraciones de grupos.
     *
     * @return Lista de {@link GroupConfigurationViewResponse} que representan las configuraciones de grupos.
     */
    List<GroupConfigurationViewResponse> getGroupConfigurationViewList();

    /**
     * Obtiene una lista de vista de configuraciones de grupos por numéro de empleado del tutor.
     *
     * @param employeeCode El código del empleado del tutor.
     * @return Lista de {@link GroupConfigurationViewResponse} que representan las configuraciones de grupos del tutor.
     */
    List<GroupConfigurationViewResponse> getGroupConfigurationViewListByTutor(String employeeCode);

    /**
     * Obtiene una lista de vista de configuraciones de grupos por correo electrónico del tutor.
     *
     * @param personUserEmail El correo electrónico del tutor.
     * @return Lista de {@link GroupConfigurationViewResponse} que representan las configuraciones de grupos del tutor.
     */
    List<GroupConfigurationViewResponse> getGroupConfigurationViewListByTutorEmail(String personUserEmail);

}

