package com.api.idsa.domain.academic.service.impl;

import com.api.idsa.common.exception.DuplicateResourceException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.academic.dto.request.GenerationRequest;
import com.api.idsa.domain.academic.dto.response.GenerationResponse;
import com.api.idsa.domain.academic.mapper.IGenerationMapper;
import com.api.idsa.domain.academic.model.GenerationEntity;
import com.api.idsa.domain.academic.repository.IGenerationRepository;
import com.api.idsa.domain.academic.service.IGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GenerationServiceImpl implements IGenerationService {

    @Autowired
    IGenerationRepository generationRepository;

    @Autowired
    IGenerationMapper generationMapper;

    @Override
    public Page<GenerationResponse> getAllGeneration(Pageable pageable) {
        Page<GenerationEntity> generationPage = generationRepository.findAll(pageable);
        return generationPage.map(generationMapper::toResponse);
    }

    @Override
    public GenerationResponse createGeneration(GenerationRequest generationRequest) {

        if (generationRepository.existsByStartYearAndEndYear(generationRequest.getYearStart(), generationRequest.getYearEnd())) {
            throw new DuplicateResourceException("create", "Generation", "uniqueGeneration", generationRequest.getYearStart() + " - " + generationRequest.getYearEnd());
        }

        GenerationEntity generationEntity = generationMapper.toEntity(generationRequest);
        return generationMapper.toResponse(generationRepository.save(generationEntity));
    }

    @Override
    public GenerationResponse updateGeneration(Long generationId, GenerationRequest request) {

        GenerationEntity generationEntity = generationRepository.findById(generationId)
                .orElseThrow(() -> new ResourceNotFoundException("update", "Generation", generationId));

        if (generationRepository.existsByStartYearAndEndYear(request.getYearStart(), request.getYearEnd())) {
            throw new DuplicateResourceException("update", "Generation", "uniqueGeneration", request.getYearStart() + " - " + request.getYearEnd());
        }

        generationEntity.setStartYear(request.getYearStart());
        generationEntity.setEndYear(request.getYearEnd());
        return generationMapper.toResponse(generationRepository.save(generationEntity));
    }

    @Override
    public void deleteGeneration(Long generationId) {

        GenerationEntity generationEntity = generationRepository.findById(generationId)
                .orElseThrow(() -> new ResourceNotFoundException("delete", "Generation", generationId));
        
        generationRepository.delete(generationEntity);
    }
    
}
