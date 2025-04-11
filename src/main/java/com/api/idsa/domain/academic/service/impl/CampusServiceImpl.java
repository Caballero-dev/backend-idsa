package com.api.idsa.domain.academic.service.impl;

import com.api.idsa.common.exception.DuplicateResourceException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.academic.dto.request.CampusRequest;
import com.api.idsa.domain.academic.dto.response.CampusResponse;
import com.api.idsa.domain.academic.mapper.ICampusMapper;
import com.api.idsa.domain.academic.model.CampusEntity;
import com.api.idsa.domain.academic.repository.ICampusRepository;
import com.api.idsa.domain.academic.service.ICampusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampusServiceImpl implements ICampusService {

    @Autowired
    ICampusRepository campusRepository;

    @Autowired
    ICampusMapper campusMapper;

    @Override
    public List<CampusResponse> getAllCampus() {
        return campusMapper.toResponseList(campusRepository.findAll());
    }

    @Override
    public CampusResponse createCampus(CampusRequest campusRequest) {

        if (campusRepository.existsByCampusName(campusRequest.getName())) {
            throw new DuplicateResourceException("create", "Campus", campusRequest.getName());
        }

        CampusEntity campusEntity = campusMapper.toEntity(campusRequest);
        return campusMapper.toResponse(campusRepository.save(campusEntity));
    }

    @Override
    public CampusResponse updateCampus(Long campusId, CampusRequest campusRequest) {
        
        CampusEntity campusEntity = campusRepository.findById(campusId)
                .orElseThrow(() -> new ResourceNotFoundException("update", "Campus", campusId));

        if (!campusEntity.getCampusName().equals(campusRequest.getName()) && campusRepository.existsByCampusName(campusRequest.getName())) {
            throw new DuplicateResourceException("update", "Campus", campusRequest.getName());
        }

        campusEntity.setCampusName(campusRequest.getName());
        return campusMapper.toResponse(campusRepository.save(campusEntity));
    }

    @Override
    public void deleteCampus(Long campusId) {

        CampusEntity campusEntity = campusRepository.findById(campusId)
                .orElseThrow(() -> new ResourceNotFoundException("delete", "Campus", campusId));
        
        campusRepository.delete(campusEntity);
    }

}
