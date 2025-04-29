package com.api.idsa.infrastructure.mqtt.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import com.api.idsa.infrastructure.mqtt.dto.requets.SensorData;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MqttMessageHandler implements MessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(MqttMessageHandler.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        try {
            // Obtener el payload como String
            String payload = message.getPayload().toString();
            logger.info("Mensaje MQTT recibido: {}", payload);

            // Convertir el JSON a objeto SensorData
            SensorData sensorData = objectMapper.readValue(payload, SensorData.class);

            // Aquí procesarías los datos del sensor
            processSensorData(sensorData);  

        } catch (Exception e) {
            logger.error("Error al procesar mensaje MQTT: {}", e.getMessage(), e);
        }
    }

    private void processSensorData(SensorData sensorData) {
        // En esta función implementarías la lógica de negocio para procesar los datos
        logger.info("Procesando datos del estudiante {}", sensorData.toString());

        // Aquí podrías:
        // - Guardar los datos en una base de datos
        // - Enviar notificaciones si se detectan valores anormales
        // - Realizar análisis en tiempo real
        // - Etc.
    }
}
