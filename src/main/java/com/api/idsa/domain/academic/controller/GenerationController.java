package com.api.idsa.domain.academic.controller;

import com.api.idsa.domain.academic.dto.request.GenerationRequest;
import com.api.idsa.domain.academic.dto.response.GenerationResponse;
import com.api.idsa.domain.academic.service.IGenerationService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/generations")
public class GenerationController {

    @Autowired
    IGenerationService generationService;

    @GetMapping
    public ResponseEntity<List<GenerationResponse>> getAllGeneration() {
        return ResponseEntity.ok(generationService.getAllGeneration());
    }

    @PostMapping
    public ResponseEntity<GenerationResponse> createGeneration(@Valid @RequestBody GenerationRequest request) {
        return ResponseEntity.ok(generationService.createGeneration(request));
    }

    @PutMapping("/{generationId}")
    public ResponseEntity<GenerationResponse> updateGeneration(@PathVariable Long generationId, @Valid @RequestBody GenerationRequest generationRequest) {
        return ResponseEntity.ok(generationService.updateGeneration(generationId, generationRequest));
    }

    @DeleteMapping("/{generationId}")
    public void deleteGeneration(@PathVariable Long generationId) {
        generationService.deleteGeneration(generationId);
    }
}
