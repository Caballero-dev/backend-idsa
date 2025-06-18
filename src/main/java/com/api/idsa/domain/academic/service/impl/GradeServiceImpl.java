package com.api.idsa.domain.academic.service.impl;

import com.api.idsa.common.exception.DuplicateResourceException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.academic.dto.request.GradeRequest;
import com.api.idsa.domain.academic.dto.response.GradeResponse;
import com.api.idsa.domain.academic.mapper.IGradeMapper;
import com.api.idsa.domain.academic.model.GradeEntity;
import com.api.idsa.domain.academic.repository.IGradeRepository;
import com.api.idsa.domain.academic.service.IGradeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;

@Service
public class GradeServiceImpl implements IGradeService {

    @Autowired
    IGradeRepository gradeRepository;

    @Autowired
    IGradeMapper gradeMapper;

    @Override
    public Page<GradeResponse> getAllGrade(Pageable pageable) {
        Page<GradeEntity> gradePage = gradeRepository.findAll(pageable);
        return gradePage.map(gradeMapper::toResponse);
    }

    @Override
    public GradeResponse createGrade(GradeRequest gradeRequest) {

        if (gradeRepository.existsByGradeName(gradeRequest.getName())) {
            throw new DuplicateResourceException("Grade", "name", gradeRequest.getName());
        }

        GradeEntity gradeEntity = gradeMapper.toEntity(gradeRequest);
        return gradeMapper.toResponse(gradeRepository.save(gradeEntity));
    }

    @Override
    public GradeResponse updateGrade(Long gradeId, GradeRequest gradeRequest) {

        GradeEntity gradeEntity = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new ResourceNotFoundException("Grade", "id", gradeId));

        if (!gradeEntity.getGradeName().equals(gradeRequest.getName()) && gradeRepository.existsByGradeName(gradeRequest.getName())) {
            throw new DuplicateResourceException("Grade", "name", gradeRequest.getName());
        }

        gradeEntity.setGradeName(gradeRequest.getName());
        return gradeMapper.toResponse(gradeRepository.save(gradeEntity));
    }

    @Override
    public void deleteGrade(Long gradeId) {
        try {
            GradeEntity gradeEntity = gradeRepository.findById(gradeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Grade", "id", gradeId));
            gradeRepository.delete(gradeEntity);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("group_configurations_grade_id_fkey")) {
                throw new com.api.idsa.common.exception.ResourceDependencyException("Grade", gradeId, "assigned groups", "group_configurations");
            } else {
                throw new com.api.idsa.common.exception.ResourceDependencyException("Grade", gradeId, "associated records", "unknown");
            }
        }
    }

}
