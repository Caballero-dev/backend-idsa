package com.api.idsa.controller;

import com.api.idsa.dto.request.CampusRequest;
import com.api.idsa.dto.response.CampusResponse;
import com.api.idsa.exception.DuplicateResourceException;
import com.api.idsa.exception.ResourceNotFoundException;
import com.api.idsa.service.ICampusService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campus")
public class CampusController {

    @Autowired
    ICampusService campusService;

    @GetMapping
    public ResponseEntity<List<CampusResponse>> getAllCampus() {
        return ResponseEntity.ok(campusService.findAll());
    }

    @PostMapping
    public ResponseEntity<CampusResponse> createCampus(@Valid @RequestBody CampusRequest campusRequest) throws DuplicateResourceException {
        return ResponseEntity.ok(campusService.createCampus(campusRequest));
    }

    @PutMapping("/{campusId}")
    public ResponseEntity<CampusResponse> updateCampus(@PathVariable Long campusId, @Valid @RequestBody CampusRequest campusRequest) throws ResourceNotFoundException, DuplicateResourceException {
        return ResponseEntity.ok(campusService.updateCampus(campusId, campusRequest));
    }

    @DeleteMapping("/{campusId}")
    public void deleteCampus(@PathVariable Long campusId) throws ResourceNotFoundException {
        campusService.deleteCampus(campusId);
    }

}
