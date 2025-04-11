package com.api.idsa.domain.personnel.controller;

import com.api.idsa.domain.personnel.dto.request.TutorRequest;
import com.api.idsa.domain.personnel.dto.response.TutorResponse;
import com.api.idsa.domain.personnel.service.ITutorService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tutors")
public class TutorController {

    @Autowired
    ITutorService tutorService;

    @GetMapping
    public ResponseEntity<List<TutorResponse>> getAllTutor() {
        return ResponseEntity.ok(tutorService.getAllTutor());
    }

    @PostMapping
    public ResponseEntity<TutorResponse> createTutor(@Valid @RequestBody TutorRequest tutorRequest) {
        return ResponseEntity.ok(tutorService.createTutor(tutorRequest));
    }

    @PutMapping("/{tutorId}")
    public ResponseEntity<TutorResponse> updateTutor(@PathVariable Long tutorId, @Valid @RequestBody TutorRequest tutorRequest) {
        return ResponseEntity.ok(tutorService.updateTutor(tutorId, tutorRequest));
    }

    @DeleteMapping("/{tutorId}")
    public void deleteTutor(@PathVariable Long tutorId) {
        tutorService.deleteTutor(tutorId);
    }

}
