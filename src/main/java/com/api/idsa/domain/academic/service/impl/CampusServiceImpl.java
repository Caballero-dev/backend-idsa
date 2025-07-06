package com.api.idsa.domain.academic.service.impl;

import com.api.idsa.common.exception.DuplicateResourceException;
import com.api.idsa.common.exception.ResourceDependencyException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.academic.dto.request.CampusRequest;
import com.api.idsa.domain.academic.dto.response.CampusResponse;
import com.api.idsa.domain.academic.mapper.ICampusMapper;
import com.api.idsa.domain.academic.model.CampusEntity;
import com.api.idsa.domain.academic.repository.ICampusRepository;
import com.api.idsa.domain.academic.service.ICampusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CampusServiceImpl implements ICampusService {

    @Autowired
    ICampusRepository campusRepository;

    @Autowired
    ICampusMapper campusMapper;

    @Override
    public Page<CampusResponse> getAllCampus(Pageable pageable, String search) {
        Page<CampusEntity> campusPage;
        if (StringUtils.hasText(search)) {
            campusPage = campusRepository.findByCampusNameContainingIgnoreCase(search.trim(), pageable);
        } else {
            campusPage = campusRepository.findAll(pageable);
        }
        return campusPage.map(campusMapper::toResponse);
    }

    @Override
    public CampusResponse createCampus(CampusRequest campusRequest) {

        if (campusRepository.existsByCampusName(campusRequest.getName())) {
            throw new DuplicateResourceException("Campus", "name", campusRequest.getName());
        }

        CampusEntity campusEntity = campusMapper.toEntity(campusRequest);
        return campusMapper.toResponse(campusRepository.save(campusEntity));
    }

    @Override
    public CampusResponse updateCampus(Long campusId, CampusRequest campusRequest) {

        CampusEntity campusEntity = campusRepository.findById(campusId)
                .orElseThrow(() -> new ResourceNotFoundException("Campus", "id", campusId));

        if (!campusEntity.getCampusName().equals(campusRequest.getName()) && campusRepository.existsByCampusName(campusRequest.getName())) {
            throw new DuplicateResourceException("Campus", "name", campusRequest.getName());
        }

        campusEntity.setCampusName(campusRequest.getName());
        return campusMapper.toResponse(campusRepository.save(campusEntity));
    }

    @Override
    public void deleteCampus(Long campusId) {
        try {
            CampusEntity campusEntity = campusRepository.findById(campusId)
                    .orElseThrow(() -> new ResourceNotFoundException("Campus", "id", campusId));

            campusRepository.delete(campusEntity);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("group_configurations_campus_id_fkey")) {
                throw new ResourceDependencyException("Campus", campusId, "groups assigned", "group_configurations");
            } else {
                throw new ResourceDependencyException("Campus", campusId, "associated records", "unknown");
            }
        }
    }

}
