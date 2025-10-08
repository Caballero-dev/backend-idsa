# IDSA - Sistema para la identificación de síntomas de consumo de sustancias adictivas

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.3-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.10.1-blue.svg)](https://maven.apache.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16+-blue.svg)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-28+-blue.svg)](https://www.docker.com/)
[![Swagger](https://img.shields.io/badge/API%20Docs-Swagger-85EA2D.svg)](https://swagger.io/)

## 📋 Descripción

IDSA es una API REST desarrollada con Spring Boot que actúa como puente central en el sistema para la identificación de síntomas de consumo de sustancias adictivas. La API recibe datos biométricos y fisiológicos desde dispositivos IoT (Raspberry Pi) a través del protocolo MQTT, gestiona el almacenamiento de información en PostgreSQL, coordina el procesamiento con modelos de predicción, y proporciona endpoints para que la aplicación web Angular pueda consultar resultados y reportes.

La API es responsable de:
- Recibir y procesar datos biométricos (imágenes faciales, temperatura, ritmo cardíaco, presión arterial) desde dispositivos IoT
- Gestionar la comunicación MQTT para recepción de datos en tiempo real
- Almacenar información en base de datos PostgreSQL
- Coordinar el análisis con modelos de predicción cuando se acumulan suficientes registros (mínimo 10)
- Proporcionar endpoints para consulta de historiales y reportes de análisis
- Gestionar la autenticación y autorización de usuarios del sistema

## 🔄 Flujo de Datos y Arquitectura
1. **Captura y Envío de Datos:**
   - Dispositivos IoT (Raspberry Pi) capturan imágenes y datos fisiológicos al ingreso del estudiante.
   - Los datos se envían vía MQTT en formato JSON a la API.
2. **Recepción y Procesamiento:**
   - La API almacena los datos y, al acumular 10 registros, los envía al modelo de predicción para análisis.
3. **Predicción y Almacenamiento:**
   - El modelo retorna la probabilidad de consumo, métricas fisiológicas y dilatación pupilar.
   - La API almacena los resultados y referencias a las imágenes.
4. **Visualización:**
   - El panel web (Angular) consulta la API para mostrar historiales y reportes a tutores y administradores.

## 🚀 Características Principales

- **Autenticación y Autorización:** Sistema JWT completo con refresh tokens
- **Gestión de Usuarios:** CRUD completo para estudiantes, tutores y roles
- **Gestión Académica:** Campus, generaciones, grados, grupos y especialidades
- **Datos Biométricos:** Procesamiento y reportes de datos biométricos
- **Comunicación MQTT:** Integración con protocolo MQTT para IoT
- **Sistema de Correos:** Envío de emails con plantillas Thymeleaf
- **Almacenamiento de Archivos:** Gestión de archivos con configuración flexible
- **Validación de Datos:** Validación robusta con Bean Validation
- **Manejo de Excepciones:** Sistema global de manejo de errores
- **CORS Configurado:** Soporte para aplicaciones frontend
- **Documentación API:** Documentación interactiva con Swagger/OpenAPI 3

## 📚 Documentación de la API

La API cuenta con documentación generada con **Swagger/OpenAPI 3** utilizando **Springdoc OpenAPI**. La documentación incluye todos los endpoints, modelos de datos y parámetros.

### Acceso a la Documentación

La interfaz de Swagger UI permite explorar y probar todos los endpoints de forma interactiva:

- **Desarrollo:**
  - http://localhost:8080/swagger-ui.html
  - http://localhost:8080/swagger-ui/index.html
  - http://localhost:8080/api-docs

- **Producción:**
  La documentación está **deshabilitada** en producción por seguridad.

## 🛠️ Tecnologías Utilizadas

### Backend
- **Java 21**: Lenguaje de programación principal
- **Spring Boot 3.4.3**: Framework principal
- **Spring Security**: Autenticación y autorización
- **Spring Data JPA**: Acceso a datos
- **Spring Integration MQTT**: Comunicación MQTT
- **Spring Mail**: Envío de correos electrónicos
- **Thymeleaf**: Plantillas para emails
- **Springdoc OpenAPI**: Documentación API con Swagger

### Base de Datos
- **PostgreSQL**

### Herramientas de Desarrollo
- **Maven**: Gestión de dependencias y build
- **MapStruct**: Mapeo de objetos
- **Lombok**: Reducción de código boilerplate
- **JWT**: Tokens de autenticación

## 📁 Estructura del Proyecto

```
backend-idsa/
├── src/                                  # Código fuente de la aplicación
│   ├── main/
│   │   ├── java/com/api/idsa/
│   │   │   ├── IdsaApplication.java      # Clase principal de Spring Boot
│   │   │   ├── common/                    # Componentes comunes
│   │   │   │   ├── config/               # Configuraciones globales
│   │   │   │   ├── exception/            # Excepciones personalizadas
│   │   │   │   ├── response/             # Respuestas API estandarizadas
│   │   │   │   ├── util/                 # Utilidades
│   │   │   │   └── validation/           # Validaciones personalizadas
│   │   │   ├── domain/                   # Dominios de negocio
│   │   │   │   ├── academic/             # Gestión académica
│   │   │   │   │   ├── controller/       # Controladores REST
│   │   │   │   │   ├── dto/              # Objetos de transferencia
│   │   │   │   │   ├── mapper/           # Mapeadores MapStruct
│   │   │   │   │   ├── model/            # Entidades JPA
│   │   │   │   │   ├── repository/       # Repositorios de datos
│   │   │   │   │   └── service/          # Lógica de negocio
│   │   │   │   ├── biometric/            # Datos biométricos
│   │   │   │   └── personnel/            # Gestión de personal
│   │   │   ├── infrastructure/           # Infraestructura
│   │   │   │   ├── fileStorage/          # Almacenamiento de archivos
│   │   │   │   ├── mail/                 # Servicio de correos
│   │   │   │   ├── model/                # Modelo de predicción
│   │   │   │   └── mqtt/                 # Configuración MQTT
│   │   │   └── security/                 # Seguridad y autenticación
│   │   │       ├── config/               # Configuración de seguridad
│   │   │       ├── controller/           # Controladores de auth
│   │   │       ├── dto/                  # DTOs de autenticación
│   │   │       ├── enums/                # Enumeraciones
│   │   │       ├── filter/               # Filtros JWT
│   │   │       ├── provider/             # Proveedores de tokens
│   │   │       └── service/              # Servicios de auth
│   │   └── resources/
│   │       ├── application.properties    # Configuración base común
│   │       ├── application-dev.properties # Configuración específica desarrollo
│   │       ├── application-prod.properties # Configuración específica producción
│   │       ├── db/                       # Scripts de base de datos
│   │       │   └── schema.sql            # Esquema de base de datos
│   │       ├── static/                   # Archivos estáticos
│   │       └── templates/                # Plantillas Thymeleaf
│   │           └── mail/                 # Plantillas de email
│   └── test/                             # Pruebas unitarias
├── .docker/                              # Configuraciones de Docker
│   ├── mosquitto/                        # Configuración del broker MQTT
│   │   ├── Dockerfile                    # Imagen personalizada de Mosquitto
│   │   ├── entrypoint.sh                 # Script de inicialización MQTT
│   │   └── .env.example                  # Variables de entorno para MQTT
│   └── README.md                         # Documentación de Docker
├── .mvn/                                 # Configuración Maven Wrapper
├── .gitignore                            # Archivos ignorados por Git
├── .dockerignore                         # Archivos ignorados por Docker
├── .env.example                          # Variables de entorno de ejemplo
├── docker-compose.yml                    # Orquestación de servicios Docker
├── Dockerfile                            # Configuración de la imagen de la API
├── mvnw                                  # Wrapper Maven
├── mvnw.cmd                              # Wrapper Maven (Windows)
├── pom.xml                               # Configuración Maven
└── README.md                             # Documentación del proyecto
```

## 🚀 Instalación y Configuración

### Prerrequisitos
- Java 21 o superior
- Maven 3.6+
- PostgreSQL 16+
- IDE compatible (IntelliJ IDEA, Eclipse, VS Code)

### Configuración de Base de Datos
1. Crear una base de datos PostgreSQL:
```sql
CREATE DATABASE idsa_db;
```
2. Ejecutar el script de esquema ubicado en [schema.sql](src/main/resources/db/schema.sql) para crear las tablas necesarias.

### Configuración de Variables de Entorno
1. Copiar el archivo `.env.example` a `.env`:
```bash
cp .env.example .env
```
2. Configurar las variables de entorno en el archivo `.env` según tus necesidades. Consulta el archivo [.env.example](.env.example) para ver todas las variables disponibles y sus descripciones.

### Configuración de Perfiles de Spring Boot
El proyecto utiliza una arquitectura de configuración modular con perfiles específicos que se establecen mediante la variable de entorno `SPRING_PROFILES_ACTIVE` en el archivo `.env`.

#### **Archivos de Configuración:**
- **`application.properties`**: Configuración base común (se aplica a todos los perfiles)
- **`application-dev.properties`**: Configuración específica para desarrollo
- **`application-prod.properties`**: Configuración específica para producción

#### **Perfiles Disponibles:**
- **Desarrollo (dev):**
  - Base de datos: PostgreSQL local
  - DDL: `none` (no modifica esquema)
  - SQL: Visible en consola (`show-sql=true`)
  - Logging: Detallado (DEBUG)
  - Configuración: `SPRING_PROFILES_ACTIVE=dev` en archivo `.env`
  - Ejecución: `mvn spring-boot:run`
- **Producción (prod):**
  - Base de datos: PostgreSQL de producción
  - DDL: `none` (no modifica esquema)
  - SQL: Oculto en consola (`show-sql=false`)
  - Logging: Optimizado (INFO/WARN)
  - Seguridad: Configuraciones adicionales
  - Configuración: `SPRING_PROFILES_ACTIVE=prod` en archivo `.env`
  - Ejecución: `mvn spring-boot:run`
- **Sin Perfil (default):**
  - Base de datos: PostgreSQL
  - DDL: `none` (no modifica esquema)
  - SQL: Oculto en consola
  - Logging: Configuración base
  - Configuración: `SPRING_PROFILES_ACTIVE=` (vacío) o sin definir en archivo `.env`
  - Ejecución: `mvn spring-boot:run`

## 🐳 Despliegue con Docker

El proyecto incluye configuración completa para Docker y Docker Compose, permitiendo un despliegue rápido y consistente en cualquier entorno.

### Configuración Inicial

1. **Clonar el repositorio:**
```bash
git clone <repository-url>
cd backend-idsa
```

2. **Configurar variables de entorno:**
```bash
cp .env.example .env
# Editar el archivo .env con tus configuraciones
```

### Opciones de Despliegue

#### 🚀 Opción 1: Despliegue Completo con Docker Compose (Recomendado)

Esta opción despliega toda la infraestructura necesaria: API, base de datos PostgreSQL y broker MQTT.

```bash
# Construir y ejecutar todos los servicios
docker-compose --env-file .env up -d
```

**Servicios incluidos:**
- **API (idsa-api)**: Puerto 8080
- **PostgreSQL (idsa-db)**: Puerto 5432
- **MQTT Broker (idsa-mqtt)**: Puerto 1883

#### 🐳 Opción 2: Solo la API con Docker

Si ya tienes PostgreSQL y MQTT configurados externamente, puedes ejecutar solo la API:

```bash
# Construir la imagen
docker build -t idsa-back .

# Ejecutar el contenedor
docker run -d -p 8080:8080 --name idsa-app-api --env-file .env idsa-back
```

## 🌐 Relación con el Front-End
La API está diseñada para ser consumida por el panel web [(frontend-idsa)](https://github.com/Caballero-dev/frontend-idsa), que permite a los usuarios finales consultar los resultados y reportes generados por el sistema.

---

**IDSA** - Sistema para la identificación de síntomas de consumo de sustancias adictivas 