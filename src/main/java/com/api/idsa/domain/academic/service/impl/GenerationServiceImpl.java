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
import org.springframework.dao.DataIntegrityViolationException;
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
            throw new DuplicateResourceException("Generation", "start_end_year ", generationRequest.getYearStart() + " / " + generationRequest.getYearEnd());
        }

        GenerationEntity generationEntity = generationMapper.toEntity(generationRequest);
        return generationMapper.toResponse(generationRepository.save(generationEntity));
    }

    @Override
    public GenerationResponse updateGeneration(Long generationId, GenerationRequest request) {

        GenerationEntity generationEntity = generationRepository.findById(generationId)
                .orElseThrow(() -> new ResourceNotFoundException("Generation", "id", generationId));

        if (
            ( 
                !generationEntity.getStartYear().equals(request.getYearStart()) || !generationEntity.getEndYear().equals(request.getYearEnd()) 
            ) && generationRepository.existsByStartYearAndEndYear(request.getYearStart(), request.getYearEnd())
        ) {
            throw new DuplicateResourceException("Generation", "start_end_year", request.getYearStart() + " / " + request.getYearEnd());
        }

        generationEntity.setStartYear(request.getYearStart());
        generationEntity.setEndYear(request.getYearEnd());
        return generationMapper.toResponse(generationRepository.save(generationEntity));
    }

    @Override
    public void deleteGeneration(Long generationId) {
        try {
            GenerationEntity generationEntity = generationRepository.findById(generationId)
                    .orElseThrow(() -> new ResourceNotFoundException("Generation", "id", generationId));
            generationRepository.delete(generationEntity);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("group_configurations_generation_id_fkey")) {
                throw new com.api.idsa.common.exception.ResourceDependencyException("Generation", generationId, "assigned groups", "group_configurations");
            } else {
                throw new com.api.idsa.common.exception.ResourceDependencyException("Generation", generationId, "associated records", "unknown");
            }
        }
    }
    
}
