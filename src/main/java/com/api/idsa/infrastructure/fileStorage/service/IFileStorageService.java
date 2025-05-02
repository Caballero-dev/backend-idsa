package com.api.idsa.infrastructure.fileStorage.service;

import java.nio.file.Path;

public interface IFileStorageService {

    /**
     * Almacena una imagen codificada en base64 en la ubicación de almacenamiento configurada
     * 
     * @param base64Image  La cadena de imagen codificada en base64
     * @return El nombre del archivo almacenado
     * @throws IllegalArgumentException Si la cadena base64 no es válida
     * @throws RuntimeException Si ocurre un error al almacenar la imagen
     */
    String storeBase64Image(String base64Image);

    /**
     * Genera una URL para acceder a la imagen a través de un endpoint
     * 
     * @param fileName El nombre del archivo de imagen
     * @return La URL de la imagen
     */
    String generateImageUrl(String fileName);

    /**
     * Obtiene la ruta completa del archivo almacenado
     * 
     * @param fileName El nombre del archivo
     * @return La ruta del archivo
     */
    Path getFilePath(String fileName);

}
