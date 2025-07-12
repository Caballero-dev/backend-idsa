package com.api.idsa.domain.academic.service.impl;

import com.api.idsa.common.exception.DuplicateResourceException;
import com.api.idsa.common.exception.ResourceDependencyException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.academic.dto.request.SpecialtyRequest;
import com.api.idsa.domain.academic.dto.response.SpecialtyResponse;
import com.api.idsa.domain.academic.mapper.ISpecialtyMapper;
import com.api.idsa.domain.academic.model.SpecialtyEntity;
import com.api.idsa.domain.academic.repository.ISpecialtyRepository;
import com.api.idsa.domain.academic.service.ISpecialtyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
public class SpecialtyServiceImpl implements ISpecialtyService {

    @Autowired
    ISpecialtyRepository specialtyRepository;

    @Autowired
    ISpecialtyMapper specialtyMapper;

    @Override
    public Page<SpecialtyResponse> getAllSpecialty(Pageable pageable, String search) {
        Page<SpecialtyEntity> specialtyPage;

        if (StringUtils.hasText(search)) {
            specialtyPage = specialtyRepository.findByShortNameContainingIgnoreCaseOrSpecialtyNameContainingIgnoreCase(
                    search.trim(), search.trim(), pageable);
        } else {
            specialtyPage = specialtyRepository.findAll(pageable);
        }

        return specialtyPage.map(specialtyMapper::toResponse);
    }

    @Override
    public SpecialtyResponse createSpecialty(SpecialtyRequest specialtyRequest) {

        if (specialtyRepository.existsBySpecialtyName(specialtyRequest.getName())) {
            throw new DuplicateResourceException("Specialty", "name", specialtyRequest.getName());
        }
        if (specialtyRepository.existsByShortName(specialtyRequest.getShortName())) {
            throw new DuplicateResourceException("Specialty", "short_name", specialtyRequest.getShortName());
        }

        SpecialtyEntity specialtyEntity = specialtyMapper.toEntity(specialtyRequest);

        return specialtyMapper.toResponse(specialtyRepository.save(specialtyEntity));
    }

    @Override
    public SpecialtyResponse updateSpecialty(UUID specialtyId, SpecialtyRequest specialtyRequest) {

        SpecialtyEntity specialtyEntity = specialtyRepository.findById(specialtyId)
                .orElseThrow(() -> new ResourceNotFoundException("Specialty", "id", specialtyId));

        if (!specialtyEntity.getSpecialtyName().equals(specialtyRequest.getName()) && specialtyRepository.existsBySpecialtyName(specialtyRequest.getName())) {
            throw new DuplicateResourceException("Specialty", "name", specialtyRequest.getName());
        }
        if (!specialtyEntity.getShortName().equals(specialtyRequest.getShortName()) && specialtyRepository.existsByShortName(specialtyRequest.getShortName())) {
            throw new DuplicateResourceException("Specialty", "short_name", specialtyRequest.getShortName());
        }

        specialtyEntity.setSpecialtyName(specialtyRequest.getName());
        specialtyEntity.setShortName(specialtyRequest.getShortName());

        return specialtyMapper.toResponse(specialtyRepository.save(specialtyEntity));
    }

    @Override
    public void deleteSpecialty(UUID specialtyId) {
        try {
            SpecialtyEntity specialtyEntity = specialtyRepository.findById(specialtyId)
                    .orElseThrow(() -> new ResourceNotFoundException("Specialty", "id", specialtyId));

            specialtyRepository.delete(specialtyEntity);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("group_configurations_specialty_id_fkey")) {
                throw new ResourceDependencyException("Specialty", specialtyId, "assigned group configurations", "group_configurations");
            } else {
                throw new ResourceDependencyException("Specialty", specialtyId, "associated records", "unknown");
            }
        }
    }

}
