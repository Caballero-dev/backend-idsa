package com.api.idsa.domain.academic.controller;

import com.api.idsa.domain.academic.dto.request.ModalityRequest;
import com.api.idsa.domain.academic.dto.response.ModalityResponse;
import com.api.idsa.domain.academic.service.IModalityService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modalities")
public class ModalityController {

    @Autowired
    IModalityService modalityService;

    @GetMapping
    public ResponseEntity<List<ModalityResponse>> getAllModality() {
        return ResponseEntity.ok(modalityService.getAllModality());
    }

    @PostMapping
    public ResponseEntity<ModalityResponse> createModality(@Valid @RequestBody ModalityRequest modalityRequest) {
        return ResponseEntity.ok(modalityService.createModality(modalityRequest));
    }

    @PutMapping("/{modalityId}")
    public ResponseEntity<ModalityResponse> updateModality(@PathVariable Long modalityId, @Valid @RequestBody ModalityRequest modalityRequest) {
        return ResponseEntity.ok(modalityService.updateModality(modalityId, modalityRequest));
    }

    @DeleteMapping("/{modalityId}")
    public void deleteModality(@PathVariable Long modalityId) {
        modalityService.deleteModality(modalityId);
    }

}
