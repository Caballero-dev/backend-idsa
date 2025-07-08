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

@Service
public class ModelPredictionService {

    @Value("${model.endpoint.url}")
    private String modelEndpointUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IFileStorageService fileStorageService;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    


    /**
     * Envía los datos biométricos al modelo y recibe la predicción
     * @param biometricDataList Lista de datos biométricos
     * @return Respuesta del modelo con la predicción y valores calculados
     */
    public ModelPredictionResponse predictFromBiometricData(List<BiometricDataEntity> biometricDataList) {
        try {
            // Crear el cuerpo multipart
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            
            // Agregar datos biométricos como JSON (incluyendo imagePath para la correspondencia)
            List<ModelPredictionRequest> biometricData = biometricDataList.stream()
                    .map(this::convertToDataPoint)
                    .collect(Collectors.toList());
            
            // Convertir a JSON string para enviarlo como parte del form-data
            String biometricDataJson = objectMapper.writeValueAsString(biometricData);
            body.add("biometric_data", biometricDataJson);

            System.out.println("JSON enviado: " + biometricDataJson);

            
            // Agregar archivos de imágenes manteniendo el orden y nombres
            for (BiometricDataEntity data : biometricDataList) {
                String imagePath = data.getImagePath();
                
                if (imagePath != null && !imagePath.isEmpty()) {
                    File imageFile = fileStorageService.getFile(imagePath);
                    if (imageFile.exists()) {
                        // Usar el nombre del archivo como clave para mantener la correspondencia
                        FileSystemResource fileResource = new FileSystemResource(imageFile);
                        body.add("images", fileResource);
                        
                        System.out.println("Agregando imagen: " + imagePath + " para datos biométricos con temp: " + data.getTemperature());
                    } else {
                        System.err.println("Archivo de imagen no encontrado: " + imagePath);
                    }
                }
            }
            
            System.out.println("Se envían " + biometricDataList.size() + " datos biométricos con sus respectivas imágenes al modelo");
            
            // Configurar headers para multipart
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            
            // Crear la entidad HTTP
            HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, headers);
            
            // Realizar la petición POST al modelo
            ModelPredictionResponse response = restTemplate.postForObject(
                    modelEndpointUrl, 
                    httpEntity, 
                    ModelPredictionResponse.class
            );
            
            if (response != null) {
                System.out.println("Respuesta recibida del modelo: " + response.toString());
                return response;
            }
            
            // Si no hay respuesta, devolvemos un valor por defecto solo para pruebas
            return createDefaultResponse();
        } catch (Exception e) {
            // En caso de error, registramos el error y devolvemos un valor por defecto
            System.err.println("Error al llamar al modelo de predicción: " + e.getMessage());
            // e.printStackTrace();
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
                .fileName(data.getImagePath())
                .createdAt(data.getCreatedAt().toString())
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
