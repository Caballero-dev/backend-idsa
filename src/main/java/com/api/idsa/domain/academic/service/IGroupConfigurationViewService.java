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

}

