package com.api.idsa.domain.academic.service.impl;

import com.api.idsa.common.exception.DuplicateResourceException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.academic.dto.request.SpecialityRequest;
import com.api.idsa.domain.academic.dto.response.SpecialityResponse;
import com.api.idsa.domain.academic.mapper.ISpecialityMapper;
import com.api.idsa.domain.academic.model.SpecialityEntity;
import com.api.idsa.domain.academic.repository.ISpecialityRespository;
import com.api.idsa.domain.academic.service.ISpecialityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialityServiceImpl implements ISpecialityService {

    @Autowired
    ISpecialityRespository specialityRespository;

    @Autowired
    ISpecialityMapper specialityMapper;

    @Override
    public List<SpecialityResponse> findAll() {
        return specialityMapper.toResponseList(specialityRespository.findAll());
    }

    @Override
    public SpecialityResponse createEspeciality(SpecialityRequest specialityRequest) throws DuplicateResourceException {

        if (specialityRespository.existsBySpecialityName(specialityRequest.getName())) {
            throw new DuplicateResourceException("create", "Speciality", "name", specialityRequest.getName());
        } else if (specialityRespository.existsByShortName(specialityRequest.getShortName())) {
            throw new DuplicateResourceException("create", "Speciality", "shortName", specialityRequest.getShortName());
        }

        SpecialityEntity specialityEntity = specialityMapper.toEntity(specialityRequest);

        return specialityMapper.toResponse(specialityRespository.save(specialityEntity));
    }

    @Override
    public SpecialityResponse updateEspeciality(Long specialityId, SpecialityRequest specialityRequest) throws ResourceNotFoundException, DuplicateResourceException {
        SpecialityEntity specialityEntity = specialityRespository.findById(specialityId)
                .orElseThrow(() -> new ResourceNotFoundException("update", "Speciality", specialityId));

        if (!specialityEntity.getSpecialityName().equals(specialityRequest.getName()) && specialityRespository.existsBySpecialityName(specialityRequest.getName())) {
            throw new DuplicateResourceException("update", "Speciality", "name", specialityRequest.getName());
        } else if (!specialityEntity.getShortName().equals(specialityRequest.getShortName()) && specialityRespository.existsByShortName(specialityRequest.getShortName())) {
            throw new DuplicateResourceException("update", "Speciality", "shortName", specialityRequest.getShortName());
        }

        specialityEntity.setSpecialityName(specialityRequest.getName());
        specialityEntity.setShortName(specialityRequest.getShortName());

        return specialityMapper.toResponse(specialityRespository.save(specialityEntity));
    }

    @Override
    public void deleteEspeciality(Long specialityId) throws ResourceNotFoundException {

        SpecialityEntity specialityEntity = specialityRespository.findById(specialityId)
                .orElseThrow(() -> new ResourceNotFoundException("delete", "Speciality", specialityId));
        specialityRespository.delete(specialityEntity);
    }
}
