# IDSA - Sistema de Identificación de Síntomas de Consumo de Sustancias Adictivas

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.3-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.10.1-blue.svg)](https://maven.apache.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16+-blue.svg)](https://www.postgresql.org/)

## 📋 Descripción

IDSA es una API REST desarrollada con Spring Boot que actúa como puente central en el sistema de identificación de síntomas de consumo de sustancias adictivas. La API recibe datos biométricos y fisiológicos desde dispositivos IoT (Raspberry Pi) a través del protocolo MQTT, gestiona el almacenamiento de información en PostgreSQL, coordina el procesamiento con modelos de predicción, y proporciona endpoints para que la aplicación web Angular pueda consultar resultados y reportes.

La API es responsable de:
- Recibir y procesar datos biométricos (imágenes faciales, temperatura, ritmo cardíaco, presión arterial) desde dispositivos IoT
- Gestionar la comunicación MQTT para recepción de datos en tiempo real
- Almacenar información en base de datos PostgreSQL
- Coordinar el análisis con modelos de predicción cuando se acumulan suficientes registros (mínimo 10)
- Proporcionar endpoints para consulta de historiales y reportes de análisis
- Gestionar la autenticación y autorización de usuarios del sistema

## 🚀 Características Principales

- **Autenticación y Autorización**: Sistema JWT completo con refresh tokens
- **Gestión de Usuarios**: CRUD completo para estudiantes, tutores y roles
- **Gestión Académica**: Campus, generaciones, grados, grupos y especialidades
- **Datos Biométricos**: Procesamiento y reportes de datos biométricos
- **Comunicación MQTT**: Integración con protocolo MQTT para IoT
- **Sistema de Correos**: Envío de emails con plantillas Thymeleaf
- **Almacenamiento de Archivos**: Gestión de archivos con configuración flexible
- **Validación de Datos**: Validación robusta con Bean Validation
- **Manejo de Excepciones**: Sistema global de manejo de errores
- **CORS Configurado**: Soporte para aplicaciones frontend

## 🛠️ Tecnologías Utilizadas

### Backend
- **Java 21**: Lenguaje de programación principal
- **Spring Boot 3.4.3**: Framework principal
- **Spring Security**: Autenticación y autorización
- **Spring Data JPA**: Acceso a datos
- **Spring Integration MQTT**: Comunicación MQTT
- **Spring Mail**: Envío de correos electrónicos
- **Thymeleaf**: Plantillas para emails

### Base de Datos
- **PostgreSQL**: Base de datos principal

### Herramientas de Desarrollo
- **Maven**: Gestión de dependencias y build
- **MapStruct**: Mapeo de objetos
- **Lombok**: Reducción de código boilerplate
- **JWT**: Tokens de autenticación

## 📁 Estructura del Proyecto

```
idsa/
├── src/
│   ├── main/
│   │   ├── java/com/api/idsa/
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
├── .env.example                          # Variables de entorno de ejemplo
├── pom.xml                               # Configuración Maven
├── mvnw                                  # Wrapper Maven
└── README.md                             # Este archivo
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

El proyecto utiliza una arquitectura de configuración modular con perfiles específicos:

#### **📁 Archivos de Configuración:**

- **`application.properties`**: Configuración base común (se aplica a todos los perfiles)
- **`application-dev.properties`**: Configuración específica para desarrollo
- **`application-prod.properties`**: Configuración específica para producción

#### **🔧 Perfiles Disponibles:**

##### **Desarrollo (dev)**
- **Base de datos**: PostgreSQL local
- **DDL**: `none` (no modifica esquema)
- **SQL**: Visible en consola (`show-sql=true`)
- **Logging**: Detallado (DEBUG)


**Ejecutar en desarrollo:**
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

##### **Producción (prod)**
- **Base de datos**: PostgreSQL de producción}
- **DDL**: `none` (no modifica esquema)
- **SQL**: Oculto en consola (`show-sql=false`)
- **Logging**: Optimizado (INFO/WARN)
- **Seguridad**: Configuraciones adicionales

**Ejecutar en producción:**
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

##### **Sin Perfil (default)**
- **Base de datos**: PostgreSQL
- **DDL**: `none` (no modifica esquema)
- **SQL**: Oculto en consola
- **Logging**: Configuración base

**Ejecutar sin perfil:**
```bash
mvn spring-boot:run
```

### Ejecución del Proyecto

1. **Clonar el repositorio:**
```bash
git clone <url-del-repositorio>
cd idsa
```

2. **Instalar dependencias:**
```bash
mvn clean install
```

3. **Configurar variables de entorno:**
```bash
cp .env.example .env
# Editar .env con tus configuraciones
```

4. **Ejecutar la aplicación según el ambiente:**

**Para desarrollo:**
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

**Para producción:**
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

**Sin perfil específico:**
```bash
mvn spring-boot:run
```

5. **Acceder a la aplicación:**
```
http://localhost:8080
```

### Configuraciones Específicas por Ambiente

#### **Desarrollo (`application-dev.properties`)**
- SQL visible para debugging
- Logging detallado
- Configuraciones de desarrollo optimizadas

#### **Producción (`application-prod.properties`)**
- SQL oculto para seguridad y rendimiento
- Logging optimizado con rotación de archivos
- Configuraciones de seguridad adicionales

---

**IDSA** - Sistema de Identificación de Síntomas de Consumo de Sustancias Adictivas 