package com.api.idsa.domain.academic.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.api.idsa.domain.academic.dto.response.GroupConfigurationViewResponse;

public interface IGroupConfigurationViewService {

    /**
     * Obtiene la lista de vista de configuraciones de grupos con opción de búsqueda.
     *
     * @param pageable Configuración de paginación.
     * @param search Término de búsqueda opcional.
     * @return Página de {@link GroupConfigurationViewResponse} con la información filtrada.
     */
    Page<GroupConfigurationViewResponse> getAllGroupConfigurationView(Pageable pageable, String search);

}

