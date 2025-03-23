package com.api.idsa.controller;

import com.api.idsa.dto.request.TutorRequest;
import com.api.idsa.dto.response.TutorResponse;
import com.api.idsa.exception.DuplicateResourceException;
import com.api.idsa.exception.ResourceNotFoundException;
import com.api.idsa.service.ITutorService;
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
