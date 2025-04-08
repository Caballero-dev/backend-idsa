package com.api.idsa.domain.personnel.controller;

import com.api.idsa.common.exception.DuplicateResourceException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.personnel.dto.request.TutorRequest;
import com.api.idsa.domain.personnel.dto.response.TutorResponse;
import com.api.idsa.domain.personnel.service.ITutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tutor")
public class TutorController {

    @Autowired
    ITutorService tutorService;

    @GetMapping
    public ResponseEntity<List<TutorResponse>> getAllTutors() {
        return ResponseEntity.ok(tutorService.findAll());
    }

    @PostMapping
    public ResponseEntity<TutorResponse> createTutor(@Validated(TutorRequest.Create.class) @RequestBody TutorRequest tutorRequest) throws DuplicateResourceException, ResourceNotFoundException {
        return ResponseEntity.ok(tutorService.createTutor(tutorRequest));
    }

    @PutMapping("/{tutorId}")
    public ResponseEntity<TutorResponse> updateTutor(@PathVariable Long tutorId, @Validated(TutorRequest.Update.class) @RequestBody TutorRequest tutorRequest) throws ResourceNotFoundException, DuplicateResourceException {
        return ResponseEntity.ok(tutorService.updateTutor(tutorId, tutorRequest));
    }

    @DeleteMapping("/{tutorId}")
    public void deleteTutor(@PathVariable Long tutorId) throws ResourceNotFoundException {
        tutorService.deleteTutor(tutorId);
    }

}
