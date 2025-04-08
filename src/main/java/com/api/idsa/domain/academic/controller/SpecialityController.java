package com.api.idsa.domain.academic.controller;

import com.api.idsa.common.exception.DuplicateResourceException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.academic.dto.request.SpecialityRequest;
import com.api.idsa.domain.academic.dto.response.SpecialityResponse;
import com.api.idsa.domain.academic.service.ISpecialityService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/speciality")
public class SpecialityController {

    @Autowired
    ISpecialityService specialityService;

    @GetMapping
    public ResponseEntity<List<SpecialityResponse>> getAllSpecialities() {
        return ResponseEntity.ok(specialityService.findAll());
    }

    @PostMapping
    public ResponseEntity<SpecialityResponse> createSpeciality(@Valid @RequestBody SpecialityRequest specialityRequest) throws DuplicateResourceException {
        return ResponseEntity.ok(specialityService.createEspeciality(specialityRequest));
    }

    @PutMapping("/{specialityId}")
    public ResponseEntity<SpecialityResponse> updateSpeciality(@PathVariable Long specialityId, @Valid @RequestBody SpecialityRequest specialityRequest) throws ResourceNotFoundException, DuplicateResourceException {
        return ResponseEntity.ok(specialityService.updateEspeciality(specialityId, specialityRequest));
    }

    @DeleteMapping("/{specialityId}")
    public void deleteSpeciality(@PathVariable Long specialityId) throws ResourceNotFoundException {
        specialityService.deleteEspeciality(specialityId);
    }

}
