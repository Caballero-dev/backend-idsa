package com.api.idsa.infrastructure.model.service;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.api.idsa.domain.biometric.enums.PredictionLevel;
import com.api.idsa.domain.biometric.model.BiometricDataEntity;
import com.api.idsa.infrastructure.fileStorage.service.IFileStorageService;
import com.api.idsa.infrastructure.model.dto.request.ModelPredictionRequest;
import com.api.idsa.infrastructure.model.dto.response.ModelPredictionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ModelPredictionService {

    @Value("${model.base.url}")
    private String modelBaseUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IFileStorageService fileStorageService;
    
    public ModelPredictionResponse predictFromBiometricData(List<BiometricDataEntity> biometricDataList) {
        try {
            log.debug("Se envían los datos biométricos al modelo: {}", biometricDataList.size());

            // Construir el cuerpo de la petición
            MultiValueMap<String, Object> requestBody = buildMultipartRequest(biometricDataList);
            
            // Configurar headers para multipart
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            
            // Crear la entidad HTTP
            HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(requestBody, headers);
            
            // Realizar la petición POST al modelo
            ModelPredictionResponse response = restTemplate.postForObject(
                    modelBaseUrl + "/model/predict", 
                    httpEntity, 
                    ModelPredictionResponse.class
            );
            
            if (response != null) {
                log.debug("Respuesta recibida del modelo: {}", response.toString());
                return response;
            }
            
            log.warn("No se recibió respuesta del modelo");
            // return createDefaultResponse();
            return null;
        } catch (Exception e) {
            log.error("Error al llamar al modelo de predicción: {}", e.getMessage());
            // Solo para pruebas         
            return createDefaultResponse();
            // return null;
        }
    }

    private MultiValueMap<String, Object> buildMultipartRequest(List<BiometricDataEntity> biometricDataList) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        
        addBiometricDataAsJson(body, biometricDataList);
        addImageFiles(body, biometricDataList);
        
        return body;
    }
    
    private void addBiometricDataAsJson(MultiValueMap<String, Object> body, List<BiometricDataEntity> biometricDataList) {
        try {
            List<ModelPredictionRequest> requestData = biometricDataList.stream()
                    .map(this::convertToRequestDto)
                    .collect(Collectors.toList());
            
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonData = objectMapper.writeValueAsString(requestData);
            body.add("biometric_data", jsonData);
            
            log.debug("Datos biométricos convertidos a JSON: {}", jsonData);
        } catch (Exception e) {
            log.error("Error al serializar datos biométricos", e);
        }
    }
    
    private void addImageFiles(MultiValueMap<String, Object> body, List<BiometricDataEntity> biometricDataList) {
        int addedImages = 0;
        
        for (BiometricDataEntity data : biometricDataList) {
            if (addImageFile(body, data)) {
                addedImages++;
            }
        }
        
        log.debug("Se agregaron {} imágenes al request", addedImages);
    }
    
    private boolean addImageFile(MultiValueMap<String, Object> body, BiometricDataEntity data) {
        String imagePath = data.getImagePath();
        
        if (imagePath == null || imagePath.isEmpty()) {
            log.warn("Ruta de imagen vacía para datos biométricos con temp: {}", data.getTemperature());
            return false;
        }
        
        File imageFile = fileStorageService.getFile(imagePath);
        if (!imageFile.exists()) {
            log.error("Archivo de imagen no encontrado: {}", imagePath);
            return false;
        }
        
        FileSystemResource fileResource = new FileSystemResource(imageFile);
        body.add("images", fileResource);
        
        log.debug("Imagen agregada: {} (temp: {})", imagePath, data.getTemperature());
        return true;
    }
    
    /**
     * Convierte una entidad de datos biométricos a un objeto DTO
     */
    private ModelPredictionRequest convertToRequestDto(BiometricDataEntity entity) {
        return ModelPredictionRequest.builder()
                .temperature(entity.getTemperature())
                .heartRate(entity.getHeartRate())
                .systolicBloodPressure(entity.getSystolicBloodPressure())
                .diastolicBloodPressure(entity.getDiastolicBloodPressure())
                .fileName(entity.getImagePath())
                .createdAt(entity.getCreatedAt().toString())
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
