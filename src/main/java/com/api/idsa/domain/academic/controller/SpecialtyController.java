package com.api.idsa.domain.academic.controller;

import com.api.idsa.common.response.ApiResponse;
import com.api.idsa.common.response.PageInfo;
import com.api.idsa.domain.academic.dto.request.SpecialtyRequest;
import com.api.idsa.domain.academic.dto.response.SpecialtyResponse;
import com.api.idsa.domain.academic.service.ISpecialtyService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specialities")
public class SpecialtyController {

    @Autowired
    ISpecialtyService specialtyService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<SpecialtyResponse>>> getAllSpecialty(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "100") int size
    ) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<SpecialtyResponse> specialtyPage = specialtyService.getAllSpecialty(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse<List<SpecialtyResponse>>(
                HttpStatus.OK,
                "Specialties retrieved successfully",
                specialtyPage.getContent(),
                PageInfo.fromPage(specialtyPage)
            )
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SpecialtyResponse>> createSpecialty(@Valid @RequestBody SpecialtyRequest specialtyRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            new ApiResponse<SpecialtyResponse>(
                HttpStatus.CREATED,
                "Specialty created successfully",
                specialtyService.createSpecialty(specialtyRequest)
            )
        );
    }

    @PutMapping("/{specialtyId}")
    public ResponseEntity<ApiResponse<SpecialtyResponse>> updateSpecialty(@PathVariable Long specialtyId, @Valid @RequestBody SpecialtyRequest specialtyRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse<SpecialtyResponse>(
                HttpStatus.OK,
                "Specialty updated successfully",
                specialtyService.updateSpecialty(specialtyId, specialtyRequest)
            )
        );
    }

    @DeleteMapping("/{specialtyId}")
    public void deleteSpeciality(@PathVariable Long specialtyId) {
        specialtyService.deleteSpecialty(specialtyId);
    }

}
