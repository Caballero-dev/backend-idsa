package com.api.idsa.infrastructure.mqtt.handler;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import com.api.idsa.domain.biometric.dto.request.BiometricDataRequest;
import com.api.idsa.domain.biometric.service.IBiometricDataService;
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

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        try {
            String payload = message.getPayload().toString();

            if (payload == null || payload.isEmpty() || !payload.startsWith("{")) {
                log.warn("Invalid payload received");
                return;
            }

            BiometricDataRequest biometricData = deserializePayload(payload);
            if (biometricData == null) return;

            biometricDataService.createBiometricData(biometricData);

        } catch (Exception e) {
            log.error("Error procesing message: {}", e.getMessage());
        }
    }

    private BiometricDataRequest deserializePayload(String payload) {
        BiometricDataRequest biometricData;

        try {
            biometricData = objectMapper.readValue(payload, BiometricDataRequest.class);
        } catch (JsonMappingException e) {
            log.warn("JSON structure error: {}", e.getMessage());
            return null;
        } catch (JsonProcessingException e) {
            log.warn("Error deserializing JSON: {}", e.getMessage());
            return null;
        }

        if (!validateBiometricData(biometricData)) return null;

        return biometricData;
    }

    private boolean validateBiometricData(BiometricDataRequest data) {
        Set<ConstraintViolation<BiometricDataRequest>> violations = validator.validate(data);

        if (!violations.isEmpty()) {
            String errorMessages = violations.stream()
                    .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                    .collect(Collectors.joining(", "));

            log.warn("Invalid biometric data: {}", errorMessages);
            return false;
        }

        return true;
    }

}
