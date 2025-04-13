package com.api.idsa.domain.academic.controller;

import com.api.idsa.common.response.ApiResponse;
import com.api.idsa.domain.academic.dto.request.CampusRequest;
import com.api.idsa.domain.academic.dto.response.CampusResponse;
import com.api.idsa.domain.academic.service.ICampusService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campuses")
public class CampusController {

    @Autowired
    ICampusService campusService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CampusResponse>>> getAllCampus() {
        return ResponseEntity.ok(
            new ApiResponse<List<CampusResponse>>(
                HttpStatus.OK,
                "Campus retrieved successfully",
                campusService.getAllCampus()
            )
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CampusResponse>> createCampus(@Valid @RequestBody CampusRequest campusRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            new ApiResponse<CampusResponse>(
                HttpStatus.CREATED,
                "Campus created successfully",
                campusService.createCampus(campusRequest)
            )
        );
    }

    @PutMapping("/{campusId}")
    public ResponseEntity<ApiResponse<CampusResponse>> updateCampus(@PathVariable Long campusId, @Valid @RequestBody CampusRequest campusRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse<CampusResponse>(
                HttpStatus.OK,
                "Campus updated successfully",
                campusService.updateCampus(campusId, campusRequest)
            )
        );
    }

    @DeleteMapping("/{campusId}")
    public void deleteCampus(@PathVariable Long campusId) {
        campusService.deleteCampus(campusId);
    }

}
