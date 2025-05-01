package com.api.idsa.domain.biometric.service;

import com.api.idsa.domain.biometric.dto.request.BiometricDataRequest;

public interface IBiometricDataService {

    /**
     * Crea un nuevo registro de datos biométricos.
     * 
     * @param biometricDataRequest Objeto que contiene la información de los datos biométricos a crear.
     * @throws ResourceNotFoundException Si el estudiante no se encuentra en la base de datos.
     */
    void createBiometricData(BiometricDataRequest biometricDataRequest);

}
