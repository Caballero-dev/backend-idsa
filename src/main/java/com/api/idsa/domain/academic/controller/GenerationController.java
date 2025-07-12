package com.api.idsa.domain.academic.controller;

import com.api.idsa.common.response.ApiResponse;
import com.api.idsa.common.response.PageInfo;
import com.api.idsa.domain.academic.dto.request.GenerationRequest;
import com.api.idsa.domain.academic.dto.response.GenerationResponse;
import com.api.idsa.domain.academic.service.IGenerationService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/generations")
public class GenerationController {

    @Autowired
    IGenerationService generationService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<GenerationResponse>>> getAllGeneration(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "100") int size,
        @RequestParam(required = false) String search
    ) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<GenerationResponse> generationPage = generationService.getAllGeneration(pageable, search);
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse<List<GenerationResponse>>(
                HttpStatus.OK,
                "Generations retrieved successfully",
                generationPage.getContent(),
                PageInfo.fromPage(generationPage)
            )
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<GenerationResponse>> createGeneration(@Valid @RequestBody GenerationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            new ApiResponse<GenerationResponse>(
                HttpStatus.CREATED,
                "Generation created successfully",
                generationService.createGeneration(request)
            )
        );
    }

    @PutMapping("/{generationId}")
    public ResponseEntity<ApiResponse<GenerationResponse>> updateGeneration(@PathVariable UUID generationId, @Valid @RequestBody GenerationRequest generationRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse<GenerationResponse>(
                HttpStatus.OK,
                "Generation updated successfully",
                generationService.updateGeneration(generationId, generationRequest)
            )
        );
    }

    @DeleteMapping("/{generationId}")
    public void deleteGeneration(@PathVariable UUID generationId) {
        generationService.deleteGeneration(generationId);
    }
}
