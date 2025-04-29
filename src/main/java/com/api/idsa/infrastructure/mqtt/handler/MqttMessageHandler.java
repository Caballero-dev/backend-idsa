package com.api.idsa.infrastructure.mqtt.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import com.api.idsa.infrastructure.mqtt.dto.requets.SensorData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
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
            // logger.debug("Mensaje MQTT recibido: {}", payload);

            // Validación básica del payload
            if (payload == null || payload.isEmpty()) {
                logger.warn("Payload vacío recibido, ignorando mensaje");
                return;
            }

            // Validar que el payload es un JSON válido y tiene la estructura esperada
            if (!isValidJson(payload)) {
                logger.warn("Payload recibido no es un JSON válido");
                return;
            }

            // Validar estructura mínima requerida
            if (!containsRequiredFields(payload)) {
                logger.warn("Payload no contiene los campos requeridos");
                return;
            }

            // Convertir el JSON a objeto SensorData
            SensorData sensorData;
            try {
                sensorData = objectMapper.readValue(payload, SensorData.class);
            } catch (JsonProcessingException e) {
                logger.warn("Error al deserializar el JSON a SensorData: {}", e.getMessage());
                return;
            }

            // Validar valores de los campos tras deserialización
            if (!isValidSensorData(sensorData)) {
                logger.warn("Los datos del sensor no son válidos");
                return;
            }

            // Aquí procesarías los datos del sensor
            processSensorData(sensorData);

        } catch (Exception e) {
            logger.error("Error al procesar mensaje MQTT: {}", e.getMessage(), e);
        }
    }

    /**
     * Valida si el string es un JSON sintácticamente correcto y representa un objeto.
     * Rechaza valores primitivos como números o strings.
     */
    private boolean isValidJson(String jsonString) {
        try {
            // Intenta verificar si la cadena comienza con { (objeto) o [ (array)
            String trimmed = jsonString.trim();
            if (!trimmed.startsWith("{") && !trimmed.startsWith("[")) {
                logger.warn("La cadena no comienza con {, no es un JSON válido");
                return false;
            }
            
            // Intenta parsear como JSON
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            
            // Verifica que sea específicamente un objeto JSON (lo que esperamos para SensorData)
            if (!jsonNode.isObject()) {
                logger.warn("El JSON no es un objeto");
                return false;
            }
            
            return true;
        } catch (Exception e) {
            logger.warn("Error al analizar JSON: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Verifica si el JSON contiene los campos mínimos requeridos
     */
    private boolean containsRequiredFields(String jsonString) {
        try {

            JsonNode rootNode = objectMapper.readTree(jsonString);

            List<String> missingFields = new ArrayList<>();
                    
            // Mostrar qué campos faltan para facilitar la depuración
            if (!rootNode.has("student_id")) missingFields.add("student_id");
            if (!rootNode.has("temperature")) missingFields.add("temperature");
            if (!rootNode.has("heart_rate")) missingFields.add("heart_rate");
            if (!rootNode.has("systolic_blood_pressure")) missingFields.add("systolic_blood_pressure");
            if (!rootNode.has("diastolic_blood_pressure")) missingFields.add("diastolic_blood_pressure");
            if (!rootNode.has("image")) missingFields.add("image");

            if (!missingFields.isEmpty()) {
                logger.warn("Faltan los siguientes campos en el JSON: {}", String.join(", ", missingFields));
                return false;
                
            }

            
            return true;
        } catch (Exception e) {
            logger.warn("Error al verificar campos requeridos: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Valida que los valores del objeto SensorData sean coherentes
     */
    private boolean isValidSensorData(SensorData data) {

        List<String> nullFields = new ArrayList<>();

        // Verificar cada campo y agregarlo a la lista si es nulo
        if (data.getStudent_id() == null || data.getStudent_id().isEmpty()) nullFields.add("student_id (null or empty)");
        if (data.getTemperature() == null) nullFields.add("temperature (null)");
        if (data.getHeart_rate() == null) nullFields.add("heart_rate (null)");
        if (data.getSystolic_blood_pressure() == null) nullFields.add("systolic_blood_pressure (null)");
        if (data.getDiastolic_blood_pressure() == null) nullFields.add("diastolic_blood_pressure (null)");
        if (data.getImage() == null || data.getImage().isEmpty() ) nullFields.add("image (null or empty)");

        // Si hay campos nulos, mostrarlos todos en un solo mensaje
        if (!nullFields.isEmpty()) {
            logger.warn("Datos del sensor con valores inválidos: {}", String.join(", ", nullFields));
            return false;
        }

        return true;
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
