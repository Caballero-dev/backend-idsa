package com.api.idsa.infrastructure.model.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.api.idsa.domain.biometric.enums.PredictionLevel;
import com.api.idsa.domain.biometric.model.BiometricDataEntity;
import com.api.idsa.infrastructure.model.dto.request.ModelPredictionRequest;
import com.api.idsa.infrastructure.model.dto.response.ModelPredictionResponse;

@Service
public class ModelPredictionService {

    @Value("${model.endpoint.url}")
    private String modelEndpointUrl;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Envía los datos biométricos al modelo y recibe la predicción
     * @param biometricDataList Lista de datos biométricos
     * @return Respuesta del modelo con la predicción y valores calculados
     */
    public ModelPredictionResponse predictFromBiometricData(List<BiometricDataEntity> biometricDataList) {
        // Convertir los datos biométricos al formato esperado por la API
        List<ModelPredictionRequest> request = biometricDataList.stream()
                .map(this::convertToDataPoint)
                .collect(Collectors.toList());
            
        System.out.println("Se envían los datos biométricos al modelo: " + request.toString());
        
        // Configurar los headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        // Crear la entidad HTTP con el cuerpo y los headers
        HttpEntity<List<ModelPredictionRequest>> httpEntity = new HttpEntity<>(request, headers);
        
        try {
            // Realizar la petición POST al modelo
            ModelPredictionResponse response = restTemplate.postForObject(
                    modelEndpointUrl, 
                    httpEntity, 
                    ModelPredictionResponse.class
            );
            
            if (response != null) {
                return response;
            }
            
            // Si no hay respuesta, devolvemos un valor por defecto solo para pruebas
            return createDefaultResponse();
        } catch (Exception e) {
            // En caso de error, registramos el error y devolvemos un valor por defecto
            System.err.println("Error al llamar al modelo de predicción: " + e.getMessage());
            return createDefaultResponse();
        }
    }
    
    /**
     * Convierte una entidad de datos biométricos a un objeto DTO
     */
    private ModelPredictionRequest convertToDataPoint(BiometricDataEntity data) {
        return ModelPredictionRequest.builder()
                .temperature(data.getTemperature())
                .heartRate(data.getHeartRate())
                .systolicBloodPressure(data.getSystolicBloodPressure())
                .diastolicBloodPressure(data.getDiastolicBloodPressure())
                .imagePath(data.getImagePath())
                .createdAt(data.getCreatedAt())
                .build();
    }
    
    /**
     * Crea una respuesta por defecto en caso de error
     */
    private ModelPredictionResponse createDefaultResponse() {
        return ModelPredictionResponse.builder()
                .temperature(new BigDecimal("36.5"))
                .heartRate(new BigDecimal("80.0"))
                .systolicBloodPressure(new BigDecimal("120.0"))
                .diastolicBloodPressure(new BigDecimal("80.0"))
                .prediction(PredictionLevel.BAJA.name())
                .build();
    }
    
}
