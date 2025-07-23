# Documentación de Servicios Dockerizados

Este directorio contiene la configuración y archivos necesarios para los servicios auxiliares utilizados en el entorno de desarrollo y pruebas del proyecto. Cada subcarpeta corresponde a un servicio específico y contiene su propia configuración y scripts de inicialización.

---

## Mosquitto (Broker MQTT)

**Ruta:** `mosquitto/`

### Descripción
Contiene la configuración personalizada para el broker MQTT Eclipse Mosquitto, utilizado para la comunicación en tiempo real entre servicios.

### Archivos principales
- `Dockerfile`: Construye una imagen personalizada de Mosquitto.
- `entrypoint.sh`: Script de entrada que genera usuarios, contraseñas, ACL y el archivo de configuración principal de Mosquitto en tiempo de ejecución.
- `.env.example`: Archivo de ejemplo con las variables de entorno necesarias. Debes copiarlo a `.env` y editarlo con los valores de tu entorno.

### Configuración y uso con `docker-compose.yml`

El servicio Mosquitto se define en el archivo `docker-compose.yml` principal del proyecto. Ejemplo de sección:

```yaml
mqtt:
  build:
    context: .docker/mosquitto
    dockerfile: Dockerfile
  container_name: idsa-mqtt
  env_file:
    - .docker/mosquitto/.env
  ports:
    - 1883:1883
```

#### Variables de entorno requeridas
Copia el archivo `.env.example` a `.env` dentro de la carpeta `mosquitto/` y edítalo con los valores necesarios para tu entorno.

- `MQTT_USER_PUB`: Usuario con permisos de publicación
- `MQTT_USER_PUB_PASSWORD`: Contraseña del publicador
- `MQTT_USER_SUB`: Usuario con permisos de suscripción
- `MQTT_USER_SUB_PASSWORD`: Contraseña del suscriptor
- `MQTT_TOPIC`: Topic principal permitido

#### ¿Cómo modificar la configuración?
- Edita el archivo `.env` para cambiar usuarios, contraseñas o el topic.
- Si necesitas cambiar la lógica de permisos, edita `entrypoint.sh`.
- Para agregar configuraciones avanzadas de Mosquitto, modifica el bloque de creación de `mosquitto.conf` en `entrypoint.sh`.

---
