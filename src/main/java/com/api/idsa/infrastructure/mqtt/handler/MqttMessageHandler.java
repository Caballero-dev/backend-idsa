package com.api.idsa.infrastructure.mqtt.handler;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import com.api.idsa.domain.biometric.dto.request.BiometricDataRequest;
import com.api.idsa.domain.biometric.model.BiometricDataEntity;
import com.api.idsa.domain.biometric.service.IBiometricDataService;
import com.api.idsa.domain.biometric.service.IReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MqttMessageHandler implements MessageHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Validator validator;

    @Autowired
    private IBiometricDataService biometricDataService;

    @Autowired
    private IReportService reportService;

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        String payload = message.getPayload().toString();

        if (payload == null || payload.isEmpty() || !payload.startsWith("{")) {
            log.warn("Failed: Invalid payload format <<invalid_payload>>");
            return;
        }

        BiometricDataRequest biometricData = deserializePayload(payload);
        if (biometricData == null) return;

        processBiometricData(biometricData);
    }

    private BiometricDataRequest deserializePayload(String payload) {
        try {
            BiometricDataRequest biometricData = objectMapper.readValue(payload, BiometricDataRequest.class);

            if (!validateBiometricData(biometricData)) return null;
            
            return biometricData;
        } catch (JsonMappingException e) {
            log.warn("Failed: JSON structure error [ {} ] <<json_structure_error>>", e.getMessage());
            return null;
        } catch (JsonProcessingException e) {
            log.warn("Failed: Error deserializing JSON [ {} ] <<json_deserialization_error>>", e.getMessage());
            return null;
        }
    }

    private boolean validateBiometricData(BiometricDataRequest data) {
        Set<ConstraintViolation<BiometricDataRequest>> violations = validator.validate(data);

        if (!violations.isEmpty()) {
            String errorMessages = violations.stream()
                    .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                    .collect(Collectors.joining(", "));

            log.warn("Failed: Invalid biometric data format [ {} ] <<validation_error>>", errorMessages);
            return false;
        }

        return true;
    }

    private void processBiometricData(BiometricDataRequest biometricData) {
        try {
            BiometricDataEntity biometricDataEntity = biometricDataService.createBiometricData(biometricData);
            reportService.createReport(biometricDataEntity.getStudent(), biometricDataEntity.getCreatedAt());
        } catch (Exception e) {
            log.warn("Error processing biometric data [ {} ]", e.getMessage());
        }
    }

}
