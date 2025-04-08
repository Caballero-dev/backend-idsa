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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeServiceImpl implements IGradeService {

    @Autowired
    IGradeRepository gradeRepository;

    @Autowired
    IGradeMapper gradeMapper;

    @Override
    public List<GradeResponse> findAll() {
        return gradeMapper.toResponseList(gradeRepository.findAll());
    }

    @Override
    public GradeResponse create(GradeRequest gradeRequest) throws DuplicateResourceException {

        if (gradeRepository.existsByGradeName(gradeRequest.getName())) {
            throw new DuplicateResourceException("create", "Grade", gradeRequest.getName());
        }

        GradeEntity gradeEntity = gradeMapper.toEntity(gradeRequest);
        return gradeMapper.toResponse(gradeRepository.save(gradeEntity));
    }

    @Override
    public GradeResponse update(Long gradeId, GradeRequest gradeRequest) throws ResourceNotFoundException, DuplicateResourceException {
        GradeEntity gradeEntity = gradeRepository.findById(gradeId).orElseThrow(() -> new ResourceNotFoundException("update", "Grade", gradeId));

        if (!gradeEntity.getGradeName().equals(gradeRequest.getName()) && gradeRepository.existsByGradeName(gradeRequest.getName())) {
            throw new DuplicateResourceException("update", "Grade", gradeRequest.getName());
        }

        gradeEntity.setGradeName(gradeRequest.getName());
        return gradeMapper.toResponse(gradeRepository.save(gradeEntity));
    }

    @Override
    public void delete(Long gradeId) throws ResourceNotFoundException {
        GradeEntity gradeEntity = gradeRepository.findById(gradeId).orElseThrow(() -> new ResourceNotFoundException("delete", "Grade", gradeId));
        gradeRepository.delete(gradeEntity);
    }

}
