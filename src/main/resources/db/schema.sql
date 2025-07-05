CREATE DATABASE idsa;

-- ========================
-- ESQUEMA DE LA BASE DE DATOS
-- ========================

CREATE TABLE campuses
(
    campus_id   SERIAL,
    campus_name VARCHAR(100) UNIQUE NOT NULL,
    PRIMARY KEY (campus_id)
);

CREATE TABLE modalities
(
    modality_id   SERIAL,
    modality_name VARCHAR(50) UNIQUE NOT NULL,
    PRIMARY KEY (modality_id)
);

CREATE TABLE specialties
(
    specialty_id   SERIAL,
    specialty_name VARCHAR(100) UNIQUE NOT NULL,
    short_name     VARCHAR(10) UNIQUE  NOT NULL,
    PRIMARY KEY (specialty_id)
);

CREATE TABLE grades
(
    grade_id   SERIAL,
    grade_name VARCHAR(30) UNIQUE NOT NULL,
    PRIMARY KEY (grade_id)
);

CREATE TABLE groups
(
    group_id   SERIAL,
    group_name VARCHAR(2) UNIQUE NOT NULL,
    PRIMARY KEY (group_id)
);

CREATE TABLE generations
(
    generation_id SERIAL,
    start_year    TIMESTAMP WITH TIME ZONE NOT NULL,
    end_year      TIMESTAMP WITH TIME ZONE NOT NULL,
    PRIMARY KEY (generation_id),
    CONSTRAINT unique_generation UNIQUE (start_year, end_year)
);

CREATE TABLE people
(
    person_id      SERIAL,
    name           VARCHAR(50)        NOT NULL,
    first_surname  VARCHAR(50)        NOT NULL,
    second_surname VARCHAR(50)        NOT NULL,
    phone_number   VARCHAR(10) UNIQUE NOT NULL,
    PRIMARY KEY (person_id)
);

CREATE TABLE roles
(
    role_id   SERIAL,
    role_name VARCHAR(50) UNIQUE NOT NULL,
    PRIMARY KEY (role_id)
);

CREATE TABLE users
(
    user_id           SERIAL,
    person_id         INT                      NOT NULL,
    role_id           INT                      NOT NULL,
    email             VARCHAR(100) UNIQUE      NOT NULL,
    password          VARCHAR(255),
    created_at        TIMESTAMP WITH TIME ZONE NOT NULL,
    is_active         BOOLEAN                  NOT NULL,
    is_verified_email BOOLEAN                  NOT NULL,
    PRIMARY KEY (user_id),
    FOREIGN KEY (person_id) REFERENCES people (person_id),
    FOREIGN KEY (role_id) REFERENCES roles (role_id)
);

CREATE TABLE tutors
(
    tutor_id      SERIAL,
    person_id     INT                NOT NULL,
    employee_code VARCHAR(20) UNIQUE NOT NULL,
    PRIMARY KEY (tutor_id),
    FOREIGN KEY (person_id) REFERENCES people (person_id)
);

CREATE TABLE group_configurations
(
    group_configuration_id SERIAL,
    campus_id              INT NOT NULL,
    specialty_id           INT NOT NULL,
    modality_id            INT NOT NULL,
    grade_id               INT NOT NULL,
    group_id               INT NOT NULL,
    generation_id          INT NOT NULL,
    tutor_id               INT NOT NULL,
    PRIMARY KEY (group_configuration_id),
    CONSTRAINT unique_group_configuration UNIQUE (campus_id, specialty_id, modality_id, grade_id, group_id,
                                                  generation_id),
    FOREIGN KEY (campus_id) REFERENCES campuses (campus_id),
    FOREIGN KEY (modality_id) REFERENCES modalities (modality_id),
    FOREIGN KEY (specialty_id) REFERENCES specialties (specialty_id),
    FOREIGN KEY (grade_id) REFERENCES grades (grade_id),
    FOREIGN KEY (group_id) REFERENCES groups (group_id),
    FOREIGN KEY (generation_id) REFERENCES generations (generation_id),
    FOREIGN KEY (tutor_id) REFERENCES tutors (tutor_id)
);

CREATE TABLE students
(
    student_id             SERIAL,
    person_id              INT                NOT NULL,
    group_configuration_id INT                NOT NULL,
    student_code           VARCHAR(20) UNIQUE NOT NULL,
    PRIMARY KEY (student_id),
    FOREIGN KEY (group_configuration_id) REFERENCES group_configurations (group_configuration_id),
    FOREIGN KEY (person_id) REFERENCES people (person_id)
);

CREATE TABLE biometric_data
(
    biometric_data_id        SERIAL,
    student_id               INT                      NOT NULL,
    temperature              NUMERIC(4, 2)            NOT NULL,
    heart_rate               NUMERIC(4, 2)            NOT NULL,
    systolic_blood_pressure  NUMERIC(5, 2)            NOT NULL,
    diastolic_blood_pressure NUMERIC(5, 2)            NOT NULL,
    image_path               VARCHAR(255)             NOT NULL,
    created_at               TIMESTAMP WITH TIME ZONE NOT NULL,
    PRIMARY KEY (biometric_data_id),
    FOREIGN KEY (student_id) REFERENCES students (student_id)
);

CREATE TABLE reports
(
    report_id                SERIAL,
    student_id               INT                      NOT NULL,
    temperature              NUMERIC(4, 2)            NOT NULL,
    heart_rate               NUMERIC(4, 2)            NOT NULL,
    systolic_blood_pressure  NUMERIC(5, 2)            NOT NULL,
    diastolic_blood_pressure NUMERIC(5, 2)            NOT NULL,
    pupil_dilation_right     NUMERIC(4, 2)            NOT NULL,
    pupil_dilation_left      NUMERIC(4, 2)            NOT NULL,
    prediction_result        NUMERIC(5, 2)            NOT NULL,
    created_at               TIMESTAMP WITH TIME ZONE NOT NULL,
    PRIMARY KEY (report_id),
    FOREIGN KEY (student_id) REFERENCES students (student_id)
);

CREATE TABLE report_biometric_data
(
    report_id         INT NOT NULL,
    biometric_data_id INT NOT NULL,
    PRIMARY KEY (report_id, biometric_data_id),
    FOREIGN KEY (report_id) REFERENCES reports (report_id),
    FOREIGN KEY (biometric_data_id) REFERENCES biometric_data (biometric_data_id)
);

-- ========================
-- FUNCIONES PARA CONSULTAS
-- ========================

-- Función para contar estudiantes por rango de probabilidad
CREATE OR REPLACE FUNCTION count_students_by_prediction_range(
    min_prediction NUMERIC,
    max_prediction NUMERIC
)
    RETURNS INTEGER AS
$$
BEGIN
    RETURN (SELECT COUNT(*)
            FROM (SELECT DISTINCT ON (student_id) student_id, prediction_result
                  FROM reports
                  ORDER BY student_id, created_at DESC) latest_reports
            WHERE prediction_result >= min_prediction
              AND prediction_result < max_prediction);
END;
$$ LANGUAGE plpgsql;

-- Función para buscar generaciones por un valor de búsqueda
CREATE OR REPLACE FUNCTION search_generations(searchValue varchar)
    RETURNS SETOF generations
AS
$BODY$
BEGIN
    RETURN QUERY
        SELECT g.*
        FROM generations g
        WHERE To_CHAR(g.start_year, 'YYYY-MM-DD') ILIKE '%' || searchValue || '%'
           OR To_CHAR(g.end_year, 'YYYY-MM-DD') ILIKE '%' || searchValue || '%';
END;
$BODY$ LANGUAGE plpgsql;

-- Función para buscar configuraciones de grupos por un valor de búsqueda
CREATE OR REPLACE FUNCTION search_group_configurations(searchValue varchar)
    RETURNS SETOF group_configurations
AS
$BODY$
BEGIN
    RETURN QUERY
        SELECT gc.*
        FROM group_configurations gc
                 JOIN tutors t on t.tutor_id = gc.tutor_id
                 JOIN people p on p.person_id = t.person_id
                 JOIN users u on u.person_id = p.person_id
                 JOIN campuses c on c.campus_id = gc.campus_id
                 JOIN specialties s on s.specialty_id = gc.specialty_id
                 JOIN modalities m on m.modality_id = gc.modality_id
                 JOIN grades gr on gr.grade_id = gc.grade_id
                 JOIN groups gp on gp.group_id = gc.group_id
                 JOIN generations g on g.generation_id = gc.generation_id
        WHERE p.name ILIKE '%' || searchValue || '%'
           OR p.first_surname ILIKE '%' || searchValue || '%'
           OR p.second_surname ILIKE '%' || searchValue || '%'
           OR u.email ILIKE '%' || searchValue || '%'
           OR c.campus_name ILIKE '%' || searchValue || '%'
           OR s.specialty_name ILIKE '%' || searchValue || '%'
           OR m.modality_name ILIKE '%' || searchValue || '%'
           OR gr.grade_name ILIKE '%' || searchValue || '%'
           OR gp.group_name ILIKE '%' || searchValue || '%'
           OR To_CHAR(g.start_year, 'YYYY-MM-DD') ILIKE '%' || searchValue || '%'
           OR To_CHAR(g.end_year, 'YYYY-MM-DD') ILIKE '%' || searchValue || '%';
END;
$BODY$ LANGUAGE plpgsql;

-- Función para buscar configuraciones de grupos por un correo electrónico de tutor y un valor de búsqueda
CREATE OR REPLACE FUNCTION search_group_configurations_by_tutor_email(emailValue varchar, searchValue varchar)
    RETURNS SETOF group_configurations
AS
$BODY$
BEGIN
    RETURN QUERY
        SELECT gc.*
        FROM search_group_configurations(searchValue) gc
                 JOIN tutors t ON t.tutor_id = gc.tutor_id
                 JOIN people p ON p.person_id = t.person_id
                 JOIN users u ON u.person_id = p.person_id
        WHERE u.email = emailValue;
END;
$BODY$ LANGUAGE plpgsql;

-- Función para buscar tutores por un valor de búsqueda
CREATE OR REPLACE FUNCTION search_tutors(searchValue varchar)
    RETURNS SETOF tutors
AS
$BODY$
BEGIN
    RETURN QUERY
        SELECT t.*
        FROM tutors t
                 JOIN people p ON p.person_id = t.person_id
                 JOIN users u ON u.person_id = p.person_id
        WHERE t.employee_code ILIKE '%' || searchValue || '%'
           OR p.name ILIKE '%' || searchValue || '%'
           OR p.first_surname ILIKE '%' || searchValue || '%'
           OR p.second_surname ILIKE '%' || searchValue || '%'
           OR p.phone_number ILIKE '%' || searchValue || '%'
           OR u.email ILIKE '%' || searchValue || '%';
END;
$BODY$ LANGUAGE plpgsql;

-- Función para buscar usuarios por un valor de búsqueda y una opción para excluir administradores
CREATE OR REPLACE FUNCTION search_users(searchValue varchar, excludeAdmin boolean)
    RETURNS SETOF users
AS
$BODY$
BEGIN
    RETURN QUERY
        SELECT u.*
        FROM users u
                 JOIN people p ON p.person_id = u.person_id
                 JOIN roles r ON r.role_id = u.role_id
        WHERE (p.name ILIKE '%' || searchValue || '%'
            OR p.first_surname ILIKE '%' || searchValue || '%'
            OR p.second_surname ILIKE '%' || searchValue || '%'
            OR u.email ILIKE '%' || searchValue || '%'
            OR To_CHAR(u.created_at, 'DD-MM-YYYY HH12:MI') ILIKE '%' || searchValue || '%')
          AND (NOT excludeAdmin OR r.role_name != 'ROLE_ADMIN');
END;
$BODY$ LANGUAGE plpgsql;

-- Función para buscar estudiantes por un valor de búsqueda
CREATE OR REPLACE FUNCTION search_students(searchValue varchar)
    RETURNS SETOF students
AS
$BODY$
BEGIN
    RETURN QUERY
        SELECT s.*
        FROM students s
                 JOIN people p ON p.person_id = s.person_id
                 JOIN group_configurations gc ON gc.group_configuration_id = s.group_configuration_id
        WHERE p.name ILIKE '%' || searchValue || '%'
           OR p.first_surname ILIKE '%' || searchValue || '%'
           OR p.second_surname ILIKE '%' || searchValue || '%'
           OR p.phone_number ILIKE '%' || searchValue || '%'
           OR s.student_code ILIKE '%' || searchValue || '%';
END;
$BODY$ LANGUAGE plpgsql;

-- Función para buscar estudiantes por un valor de búsqueda y una configuración de grupo específica
CREATE OR REPLACE FUNCTION search_students_by_group_configuration(searchValue varchar, groupConfigurationId bigint)
    RETURNS SETOF students
AS
$BODY$
BEGIN
    RETURN QUERY
        SELECT s.*
        FROM search_students(searchValue) s
        WHERE s.group_configuration_id = groupConfigurationId;
END;
$BODY$ LANGUAGE plpgsql;

-- ========================
-- ÍNDICES PARA OPTIMIZACIÓN
-- ========================

-- Índices sobre la tabla users
CREATE INDEX IF NOT EXISTS idx_users_email ON users (email);
CREATE INDEX IF NOT EXISTS idx_users_person_id ON users (person_id);
CREATE INDEX IF NOT EXISTS idx_users_role_id ON users (role_id);

-- Índices sobre la tabla tutors
CREATE INDEX IF NOT EXISTS idx_tutors_person_id ON tutors (person_id);
CREATE INDEX IF NOT EXISTS idx_tutors_employee_code ON tutors (employee_code);

-- Índices sobre la tabla group_configurations
CREATE INDEX IF NOT EXISTS idx_group_configurations_unique ON group_configurations
    (campus_id, specialty_id, modality_id, grade_id, group_id, generation_id);
CREATE INDEX IF NOT EXISTS idx_group_configurations_campus_id ON group_configurations (campus_id);
CREATE INDEX IF NOT EXISTS idx_group_configurations_specialty_id ON group_configurations (specialty_id);
CREATE INDEX IF NOT EXISTS idx_group_configurations_modality_id ON group_configurations (modality_id);
CREATE INDEX IF NOT EXISTS idx_group_configurations_grade_id ON group_configurations (grade_id);
CREATE INDEX IF NOT EXISTS idx_group_configurations_group_id ON group_configurations (group_id);
CREATE INDEX IF NOT EXISTS idx_group_configurations_generation_id ON group_configurations (generation_id);
CREATE INDEX IF NOT EXISTS idx_group_configurations_tutor_id ON group_configurations (tutor_id);

-- Índices sobre la tabla students
CREATE INDEX IF NOT EXISTS idx_students_student_code ON students (student_code);
CREATE INDEX IF NOT EXISTS idx_students_person_id ON students (person_id);
CREATE INDEX IF NOT EXISTS idx_students_group_configuration_id ON students (group_configuration_id);

-- Índices sobre la tabla biometric_data
CREATE INDEX IF NOT EXISTS idx_biometric_data_student_id ON biometric_data (student_id);
CREATE INDEX IF NOT EXISTS idx_biometric_data_created_at ON biometric_data (created_at);
CREATE INDEX IF NOT EXISTS idx_biometric_data_student_created_at ON biometric_data (student_id, created_at);

-- Índices sobre la tabla reports
CREATE INDEX IF NOT EXISTS idx_reports_student_id ON reports (student_id);
CREATE INDEX IF NOT EXISTS idx_reports_created_at ON reports (created_at);
CREATE INDEX IF NOT EXISTS idx_reports_prediction_result ON reports (prediction_result);
CREATE INDEX IF NOT EXISTS idx_reports_student_date ON reports (student_id, created_at);
CREATE INDEX IF NOT EXISTS idx_reports_student_date_desc ON reports (student_id, created_at DESC);

-- Índices sobre la tabla report_biometric_data
CREATE INDEX IF NOT EXISTS idx_report_biometric_data_report_id ON report_biometric_data (report_id);
CREATE INDEX IF NOT EXISTS idx_report_biometric_data_biometric_data_id ON report_biometric_data (biometric_data_id);
