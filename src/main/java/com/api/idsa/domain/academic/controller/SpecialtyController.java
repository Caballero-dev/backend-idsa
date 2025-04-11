package com.api.idsa.domain.academic.controller;

import com.api.idsa.domain.academic.dto.request.SpecialtyRequest;
import com.api.idsa.domain.academic.dto.response.SpecialtyResponse;
import com.api.idsa.domain.academic.service.ISpecialtyService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specialities")
public class SpecialtyController {

    @Autowired
    ISpecialtyService specialtyService;

    @GetMapping
    public ResponseEntity<List<SpecialtyResponse>> getAllSpecialty() {
        return ResponseEntity.ok(specialtyService.getAllSpecialty());
    }

    @PostMapping
    public ResponseEntity<SpecialtyResponse> createSpecialty(@Valid @RequestBody SpecialtyRequest specialtyRequest) {
        return ResponseEntity.ok(specialtyService.createSpecialty(specialtyRequest));
    }

    @PutMapping("/{specialtyId}")
    public ResponseEntity<SpecialtyResponse> updateSpecialty(@PathVariable Long specialtyId, @Valid @RequestBody SpecialtyRequest specialtyRequest) {
        return ResponseEntity.ok(specialtyService.updateSpecialty(specialtyId, specialtyRequest));
    }

    @DeleteMapping("/{specialtyId}")
    public void deleteSpeciality(@PathVariable Long specialtyId) {
        specialtyService.deleteSpecialty(specialtyId);
    }

}
