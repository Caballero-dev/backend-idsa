package com.api.idsa.domain.academic.service.impl;

import com.api.idsa.common.exception.DuplicateResourceException;
import com.api.idsa.common.exception.ResourceDependencyException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.academic.dto.request.ModalityRequest;
import com.api.idsa.domain.academic.dto.response.ModalityResponse;
import com.api.idsa.domain.academic.mapper.IModalityMapper;
import com.api.idsa.domain.academic.model.ModalityEntity;
import com.api.idsa.domain.academic.repository.IModalityRepository;
import com.api.idsa.domain.academic.service.IModalityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.util.StringUtils;

@Service
public class ModalityServiceImpl implements IModalityService {

    @Autowired
    IModalityRepository modalityRepository;

    @Autowired
    IModalityMapper modalityMapper;

    @Override
    public Page<ModalityResponse> getAllModality(Pageable pageable, String search) {
        Page<ModalityEntity> modalityPage;
        if (StringUtils.hasText(search)) {
            modalityPage = modalityRepository.findByModalityNameContainingIgnoreCase(search.trim(), pageable);
        } else {
            modalityPage = modalityRepository.findAll(pageable);
        }
        return modalityPage.map(modalityMapper::toResponse);
    }

    @Override
    public ModalityResponse createModality(ModalityRequest modalityRequest) {

        if (modalityRepository.existsByModalityName(modalityRequest.getName())) {
            throw new DuplicateResourceException("Modality", "name", modalityRequest.getName());
        }

        ModalityEntity modalityEntity = modalityMapper.toEntity(modalityRequest);
        return modalityMapper.toResponse(modalityRepository.save(modalityEntity));
    }
    
    @Override
    public ModalityResponse updateModality(Long modalityId, ModalityRequest modalityRequest) {
        
        ModalityEntity modalityEntity = modalityRepository.findById(modalityId)
                .orElseThrow(() -> new ResourceNotFoundException("Modality", "id", modalityId));
        
        if (!modalityEntity.getModalityName().equals(modalityRequest.getName()) && modalityRepository.existsByModalityName(modalityRequest.getName())) {
            throw new DuplicateResourceException("Modality", "name", modalityRequest.getName());
        }

        modalityEntity.setModalityName(modalityRequest.getName());
        return modalityMapper.toResponse(modalityRepository.save(modalityEntity));
    }
    
    @Override
    public void deleteModality(Long modalityId) {
        try {
            ModalityEntity modalityEntity = modalityRepository.findById(modalityId)
                    .orElseThrow(() -> new ResourceNotFoundException("Modality", "id", modalityId));
            modalityRepository.delete(modalityEntity);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("group_configurations_modality_id_fkey")) {
                throw new ResourceDependencyException("Modality", modalityId, "assigned group configurations", "group_configurations");
            } else {
                throw new ResourceDependencyException("Modality", modalityId, "associated records", "unknown");
            }
        }
    }
    
}
