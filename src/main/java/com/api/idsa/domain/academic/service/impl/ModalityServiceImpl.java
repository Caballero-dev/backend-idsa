package com.api.idsa.domain.academic.service.impl;

import com.api.idsa.common.exception.DuplicateResourceException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.academic.dto.request.ModalityRequest;
import com.api.idsa.domain.academic.dto.response.ModalityResponse;
import com.api.idsa.domain.academic.mapper.IModalityMapper;
import com.api.idsa.domain.academic.model.ModalityEntity;
import com.api.idsa.domain.academic.repository.IModalityRepositoty;
import com.api.idsa.domain.academic.service.IModalityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModalityServiceImpl implements IModalityService {

    @Autowired
    IModalityRepositoty modalityRepositoty;

     @Autowired
    IModalityMapper modalityMapper;


    @Override
    public List<ModalityResponse> findAll() {
        return modalityMapper.toResponseList(modalityRepositoty.findAll());
    }

    @Override
    public ModalityResponse createModality(ModalityRequest modalityRequest) throws DuplicateResourceException {
        if (modalityRepositoty.existsByModalityName(modalityRequest.getName())) {
            throw new DuplicateResourceException("create", "Campus", modalityRequest.getName());
        }

        ModalityEntity modalityEntity = modalityMapper.toEntity(modalityRequest);
        return modalityMapper.toResponse(modalityRepositoty.save(modalityEntity));
    }

    @Override
    public ModalityResponse updateModality(Long modalityId, ModalityRequest modalityRequest) throws ResourceNotFoundException, DuplicateResourceException {
        ModalityEntity modalityEntity = modalityRepositoty.findById(modalityId)
                .orElseThrow(() -> new ResourceNotFoundException("update", "Campus", modalityId));

        if (!modalityEntity.getModalityName().equals(modalityRequest.getName()) && modalityRepositoty.existsByModalityName(modalityRequest.getName())) {
            throw new DuplicateResourceException("update", "Campus", modalityRequest.getName());
        }

        modalityEntity.setModalityName(modalityRequest.getName());
        return modalityMapper.toResponse(modalityRepositoty.save(modalityEntity));
    }

    @Override
    public void deleteModality(Long modalityId) throws ResourceNotFoundException {
        ModalityEntity modalityEntity = modalityRepositoty.findById(modalityId)
                .orElseThrow(() -> new ResourceNotFoundException("delete", "Campus", modalityId));

        modalityRepositoty.delete(modalityEntity);
    }

}
