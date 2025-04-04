CREATE DATABASE idsa;

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
    speciality_id   SERIAL,
    speciality_name VARCHAR(100) UNIQUE NOT NULL,
    short_name      VARCHAR(10) UNIQUE  NOT NULL,
    PRIMARY KEY (speciality_id)
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
    start_year    DATE NOT NULL,
    end_year      DATE NOT NULL,
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
    user_id    SERIAL,
    person_id  INT                      NOT NULL,
    role_id    INT                      NOT NULL,
    email      VARCHAR(100) UNIQUE      NOT NULL,
    password   VARCHAR(255)             NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    is_active  BOOLEAN                  NOT NULL,
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
    speciality_id          INT NOT NULL,
    modality_id            INT NOT NULL,
    grade_id               INT NOT NULL,
    group_id               INT NOT NULL,
    generation_id          INT NOT NULL,
    tutor_id               INT NOT NULL,
    PRIMARY KEY (group_configuration_id),
    CONSTRAINT unique_group_configuration UNIQUE (campus_id, speciality_id, modality_id, grade_id, group_id,
                                                  generation_id),
    FOREIGN KEY (campus_id) REFERENCES campuses (campus_id),
    FOREIGN KEY (modality_id) REFERENCES modalities (modality_id),
    FOREIGN KEY (speciality_id) REFERENCES specialties (speciality_id),
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
    biometric_data_id SERIAL,
    student_id        INT                      NOT NULL,
    temperature       NUMERIC(4, 2)            NOT NULL,
    heart_rate        NUMERIC(4, 2)            NOT NULL,
    systolic_blood_pressure NUMERIC(5, 2)      NOT NULL,
    diastolic_blood_pressure NUMERIC(5, 2)     NOT NULL,
    image_path        VARCHAR(255)             NOT NULL,
    created_at        TIMESTAMP WITH TIME ZONE NOT NULL,
    PRIMARY KEY (biometric_data_id),
    FOREIGN KEY (student_id) REFERENCES students (student_id)
);

CREATE TABLE reports
(
    report_id            SERIAL,
    student_id           INT                      NOT NULL,
    temperature          NUMERIC(4, 2)            NOT NULL,
    heart_rate           NUMERIC(4, 2)            NOT NULL,
    systolic_blood_pressure NUMERIC(5, 2)      NOT NULL,
    diastolic_blood_pressure NUMERIC(5, 2)     NOT NULL,
    pupil_dilation_right NUMERIC(4, 2)            NOT NULL,
    pupil_dilation_left  NUMERIC(4, 2)            NOT NULL,
    prediction_result    NUMERIC(5, 2)            NOT NULL,
    created_at           TIMESTAMP WITH TIME ZONE NOT NULL,
    PRIMARY KEY (report_id),
    FOREIGN KEY (student_id) REFERENCES students (student_id)
);

CREATE TABLE data_reports
(
    data_report_id SERIAL,
    report_id      INT NOT NULL,
    biometric_data_id INT NOT NULL,
    PRIMARY KEY (data_report_id),
    FOREIGN KEY (report_id) REFERENCES reports (report_id),
    FOREIGN KEY (biometric_data_id) REFERENCES biometric_data (biometric_data_id)
);