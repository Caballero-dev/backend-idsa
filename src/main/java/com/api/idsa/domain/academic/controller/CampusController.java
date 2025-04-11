package com.api.idsa.domain.academic.controller;

import com.api.idsa.domain.academic.dto.request.CampusRequest;
import com.api.idsa.domain.academic.dto.response.CampusResponse;
import com.api.idsa.domain.academic.service.ICampusService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campuses")
public class CampusController {

    @Autowired
    ICampusService campusService;

    @GetMapping
    public ResponseEntity<List<CampusResponse>> getAllCampus() {
        return ResponseEntity.ok(campusService.getAllCampus());
    }

    @PostMapping
    public ResponseEntity<CampusResponse> createCampus(@Valid @RequestBody CampusRequest campusRequest) {
        return ResponseEntity.ok(campusService.createCampus(campusRequest));
    }

    @PutMapping("/{campusId}")
    public ResponseEntity<CampusResponse> updateCampus(@PathVariable Long campusId, @Valid @RequestBody CampusRequest campusRequest) {
        return ResponseEntity.ok(campusService.updateCampus(campusId, campusRequest));
    }

    @DeleteMapping("/{campusId}")
    public void deleteCampus(@PathVariable Long campusId) {
        campusService.deleteCampus(campusId);
    }

}
