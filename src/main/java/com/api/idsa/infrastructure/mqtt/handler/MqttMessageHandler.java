package com.api.idsa.infrastructure.mqtt.handler;

import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import com.api.idsa.domain.biometric.dto.request.BiometricDataRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Service
public class MqttMessageHandler implements MessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(MqttMessageHandler.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private Validator validator;

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        try {
            String payload = message.getPayload().toString();

            if (payload == null || payload.isEmpty() || !payload.startsWith("{")) {
                logger.warn("Payload inválido recibido");
                return;
            }

            BiometricDataRequest biometricData = deserializePayload(payload);
            if (biometricData == null) {
                return;
            }

            // Validar valores de los campos tras deserialización
            if (!validateBiometricData(biometricData)) {
                return;
            }

            // Aquí procesarías los datos del sensor
            processSensorData(biometricData);

        } catch (Exception e) {
            logger.error("Error al procesar mensaje MQTT: {}", e.getMessage(), e);
        }
    }

    /**
     * Deserializa el payload JSON a un objeto BiometricDataRequest
     */
    private BiometricDataRequest deserializePayload(String payload) {
        try {
            return objectMapper.readValue(payload, BiometricDataRequest.class);
        } catch (JsonProcessingException e) {
            logger.warn("Error al deserializar JSON: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Valida el objeto BiometricDataRequest usando Jakarta Validation
     */
    private boolean validateBiometricData(BiometricDataRequest data) {
        Set<ConstraintViolation<BiometricDataRequest>> violations = validator.validate(data);

        if (!violations.isEmpty()) {
            String errorMessages = violations.stream()
                    .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                    .collect(Collectors.joining(", "));

            logger.warn("Datos biométricos inválidos: {}", errorMessages);
            return false;
        }

        return true;
    }

    private void processSensorData(BiometricDataRequest biometricData) {
        // En esta función implementarías la lógica de negocio para procesar los datos
        logger.info("Procesando datos del estudiante {}", biometricData.toString());

        // Guardar los datos en una base de datos

        // Aquí podrías:
        // - Guardar los datos en una base de datos
        // - Enviar notificaciones si se detectan valores anormales
        // - Realizar análisis en tiempo real
        // - Etc.
    }

}
