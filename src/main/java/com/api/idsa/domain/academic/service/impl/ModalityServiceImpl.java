package com.api.idsa.domain.academic.service.impl;

import com.api.idsa.common.exception.DuplicateResourceException;
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

@Service
public class ModalityServiceImpl implements IModalityService {

    @Autowired
    IModalityRepository modalityRepository;

    @Autowired
    IModalityMapper modalityMapper;

    @Override
    public Page<ModalityResponse> getAllModality(Pageable pageable) {
        Page<ModalityEntity> modalityPage = modalityRepository.findAll(pageable);
        return modalityPage.map(modalityMapper::toResponse);
    }

    @Override
    public ModalityResponse createModality(ModalityRequest modalityRequest) {

        if (modalityRepository.existsByModalityName(modalityRequest.getName())) {
            throw new DuplicateResourceException("create", "Modality", modalityRequest.getName());
        }

        ModalityEntity modalityEntity = modalityMapper.toEntity(modalityRequest);
        return modalityMapper.toResponse(modalityRepository.save(modalityEntity));
    }
    
    @Override
    public ModalityResponse updateModality(Long modalityId, ModalityRequest modalityRequest) {
        
        ModalityEntity modalityEntity = modalityRepository.findById(modalityId)
                .orElseThrow(() -> new ResourceNotFoundException("update", "Modality", modalityId));
        
        if (!modalityEntity.getModalityName().equals(modalityRequest.getName()) && modalityRepository.existsByModalityName(modalityRequest.getName())) {
            throw new DuplicateResourceException("update", "Modality", modalityRequest.getName());
        }

        modalityEntity.setModalityName(modalityRequest.getName());
        return modalityMapper.toResponse(modalityRepository.save(modalityEntity));
    }
    
    @Override
    public void deleteModality(Long modalityId) {

        ModalityEntity modalityEntity = modalityRepository.findById(modalityId)
                .orElseThrow(() -> new ResourceNotFoundException("delete", "Modality", modalityId));
        
        modalityRepository.delete(modalityEntity);
    }
    
}
