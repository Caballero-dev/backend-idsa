package com.api.idsa.infrastructure.fileStorage.service.impl;

import com.api.idsa.infrastructure.fileStorage.service.IFileStorageService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements IFileStorageService {

    @Value("${file.storage.base-path}")
    private String basePath;

    @Override
    public String storeBase64Image(String base64Image) {
        try {
            String base64Data = base64Image.contains(",") ? 
                    base64Image.split(",")[1] : 
                    base64Image;
            String fileName = UUID.randomUUID().toString() + ".jpg";
            byte[] imageBytes = Base64.getDecoder().decode(base64Data);
            Path filePath = Paths.get(basePath).resolve(fileName);
            Files.write(filePath, imageBytes);
            return fileName;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid base64 string: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("Error writing file: " + e.getMessage());
        }
    }

    @Override
    public String generateImageUrl(String fileName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/image/")
                .path(fileName)
                .toUriString();
    }

    @Override
    public Path getFilePath(String fileName) {
        return Paths.get(basePath).resolve(fileName);
    }
    
    @Override
    public void deleteFile(String fileName) {
        try {
            if (fileName == null || fileName.isEmpty()) {
                return;
            }
            
            Path filePath = getFilePath(fileName);
            if (Files.exists(filePath)) {
                Files.delete(filePath);
                return;
            }
        } catch (NoSuchFileException e) {
            throw new RuntimeException("File not found: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("Error deleting file: " + e.getMessage());
        }
    }

}
