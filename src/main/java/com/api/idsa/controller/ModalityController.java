package com.api.idsa.controller;

import com.api.idsa.dto.request.ModalityRequest;
import com.api.idsa.dto.response.ModalityResponse;
import com.api.idsa.exception.DuplicateResourceException;
import com.api.idsa.exception.ResourceNotFoundException;
import com.api.idsa.service.IModalityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modality")
public class ModalityController {

    @Autowired
    IModalityService modalityService;

    @GetMapping
    public ResponseEntity<List<ModalityResponse>> getAllModalities() {
        return ResponseEntity.ok(modalityService.findAll());
    }

    @PostMapping
    public ResponseEntity<ModalityResponse> createModality(@Valid @RequestBody ModalityRequest modalityRequest) throws DuplicateResourceException {
        return ResponseEntity.ok(modalityService.createModality(modalityRequest));
    }

    @PutMapping("/{modalityId}")
    public ResponseEntity<ModalityResponse> updateModality(@PathVariable Long modalityId, @Valid @RequestBody ModalityRequest modalityRequest) throws ResourceNotFoundException, DuplicateResourceException {
        return ResponseEntity.ok(modalityService.updateModality(modalityId, modalityRequest));
    }

    @DeleteMapping("/{modalityId}")
    public void deleteModality(@PathVariable Long modalityId) throws ResourceNotFoundException {
        modalityService.deleteModality(modalityId);
    }

}
