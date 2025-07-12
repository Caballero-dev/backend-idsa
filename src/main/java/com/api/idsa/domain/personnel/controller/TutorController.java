package com.api.idsa.domain.personnel.controller;

import com.api.idsa.common.response.ApiResponse;
import com.api.idsa.common.response.PageInfo;
import com.api.idsa.domain.personnel.dto.request.TutorRequest;
import com.api.idsa.domain.personnel.dto.response.TutorResponse;
import com.api.idsa.domain.personnel.service.ITutorService;

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
@RequestMapping("/api/admin/tutors")
public class TutorController {

    @Autowired
    ITutorService tutorService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<TutorResponse>>> getAllTutor(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "100") int size,
        @RequestParam(required = false) String search
    ) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<TutorResponse> tutorPage = tutorService.getAllTutor(pageable, search);
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse<List<TutorResponse>>(
                HttpStatus.OK,
                "Tutors retrieved successfully",
                tutorPage.getContent(),
                PageInfo.fromPage(tutorPage)
            )
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TutorResponse>> createTutor(@Valid @RequestBody TutorRequest tutorRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            new ApiResponse<TutorResponse>(
                HttpStatus.CREATED,
                "Tutor created successfully",
                tutorService.createTutor(tutorRequest)
            )
        );
    }

    @PutMapping("/{tutorId}")
    public ResponseEntity<ApiResponse<TutorResponse>> updateTutor(@PathVariable UUID tutorId, @Valid @RequestBody TutorRequest tutorRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse<TutorResponse>(
                HttpStatus.OK,
                "Tutor updated successfully",
                tutorService.updateTutor(tutorId, tutorRequest)
            )
        );
    }

    @DeleteMapping("/{tutorId}")
    public void deleteTutor(@PathVariable UUID tutorId) {
        tutorService.deleteTutor(tutorId);
    }

}
