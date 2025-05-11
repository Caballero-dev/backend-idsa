package com.api.idsa.infrastructure.fileStorage.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@Slf4j
public class FileStorageConfig {

    @Value("${file.storage.base-path}")
    private String basePath;

    @PostConstruct
    public void init() {
        try {
            Path path = Paths.get(basePath);
            if (Files.exists(path)) {
                log.info("Storage directory already exists at: {}", path.toAbsolutePath());
                return;
            }
            Files.createDirectories(path);
            log.info("Storage directory initialized at: {}", path.toAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Could not create storage directory", e);
        }
    }

}
