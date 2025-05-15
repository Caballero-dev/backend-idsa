package com.api.idsa.domain.academic.controller;

import com.api.idsa.common.response.ApiResponse;
import com.api.idsa.common.response.PageInfo;
import com.api.idsa.domain.academic.dto.request.ModalityRequest;
import com.api.idsa.domain.academic.dto.response.ModalityResponse;
import com.api.idsa.domain.academic.service.IModalityService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/modalities")
public class ModalityController {

    @Autowired
    IModalityService modalityService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ModalityResponse>>> getAllModality(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "100") int size
    ) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<ModalityResponse> modalityPage = modalityService.getAllModality(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse<List<ModalityResponse>>(
                HttpStatus.OK,
                "Modalities retrieved successfully",
                modalityPage.getContent(),
                PageInfo.fromPage(modalityPage)
            )
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ModalityResponse>> createModality(@Valid @RequestBody ModalityRequest modalityRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            new ApiResponse<ModalityResponse>(
                HttpStatus.CREATED,
                "Modality created successfully",
                modalityService.createModality(modalityRequest)
            )
        );
    }

    @PutMapping("/{modalityId}")
    public ResponseEntity<ApiResponse<ModalityResponse>> updateModality(@PathVariable Long modalityId, @Valid @RequestBody ModalityRequest modalityRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse<ModalityResponse>(
                HttpStatus.OK,
                "Modality updated successfully",
                modalityService.updateModality(modalityId, modalityRequest)
            )
        );
    }

    @DeleteMapping("/{modalityId}")
    public void deleteModality(@PathVariable Long modalityId) {
        modalityService.deleteModality(modalityId);
    }

}
