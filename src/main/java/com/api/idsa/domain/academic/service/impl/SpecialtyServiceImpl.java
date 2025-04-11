package com.api.idsa.domain.academic.service.impl;

import com.api.idsa.common.exception.DuplicateResourceException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.academic.dto.request.SpecialtyRequest;
import com.api.idsa.domain.academic.dto.response.SpecialtyResponse;
import com.api.idsa.domain.academic.mapper.ISpecialtyMapper;
import com.api.idsa.domain.academic.model.SpecialtyEntity;
import com.api.idsa.domain.academic.repository.ISpecialtyRepository;
import com.api.idsa.domain.academic.service.ISpecialtyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialtyServiceImpl implements ISpecialtyService {

    @Autowired
    ISpecialtyRepository specialtyRepository;

    @Autowired
    ISpecialtyMapper specialtyMapper;

    @Override
    public List<SpecialtyResponse> getAllSpecialty() {
        return specialtyMapper.toResponseList(specialtyRepository.findAll());
    }

    @Override
    public SpecialtyResponse createSpecialty(SpecialtyRequest specialtyRequest) {

        if (specialtyRepository.existsBySpecialtyName(specialtyRequest.getName())) {
            throw new DuplicateResourceException("create", "Speciality", "name", specialtyRequest.getName());
        }
        if (specialtyRepository.existsByShortName(specialtyRequest.getShortName())) {
            throw new DuplicateResourceException("create", "Speciality", "shortName", specialtyRequest.getShortName());
        }

        SpecialtyEntity specialtyEntity = specialtyMapper.toEntity(specialtyRequest);

        return specialtyMapper.toResponse(specialtyRepository.save(specialtyEntity));
    }

    @Override
    public SpecialtyResponse updateSpecialty(Long specialtyId, SpecialtyRequest specialtyRequest) {
        
        SpecialtyEntity specialtyEntity = specialtyRepository.findById(specialtyId)
                .orElseThrow(() -> new ResourceNotFoundException("update", "Specialty", specialtyId));

        if (!specialtyEntity.getSpecialtyName().equals(specialtyRequest.getName()) && specialtyRepository.existsBySpecialtyName(specialtyRequest.getName())) {
            throw new DuplicateResourceException("update", "Specialty", "name", specialtyRequest.getName());
        } 
        if (!specialtyEntity.getShortName().equals(specialtyRequest.getShortName()) && specialtyRepository.existsByShortName(specialtyRequest.getShortName())) {
            throw new DuplicateResourceException("update", "Specialty", "shortName", specialtyRequest.getShortName());
        }

        specialtyEntity.setSpecialtyName(specialtyRequest.getName());
        specialtyEntity.setShortName(specialtyRequest.getShortName());

        return specialtyMapper.toResponse(specialtyRepository.save(specialtyEntity));
    }

    @Override
    public void deleteSpecialty(Long specialtyId) {

        SpecialtyEntity specialtyEntity = specialtyRepository.findById(specialtyId)
                .orElseThrow(() -> new ResourceNotFoundException("delete", "Speciality", specialtyId));

        specialtyRepository.delete(specialtyEntity);
    }

}
