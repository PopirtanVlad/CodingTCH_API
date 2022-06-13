package com.example.licenta.controller;

import com.example.licenta.dtos.submissions.SubmissionDetails;
import com.example.licenta.dtos.submissions.SubmissionPreview;
import com.example.licenta.dtos.submissions.SubmissionRequest;
import com.example.licenta.dtos.user.security.ApiResponse;
import com.example.licenta.services.SubmissionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/submissions")
public class SubmissionsController {

    @Autowired
    private SubmissionServiceImpl submissionService;

    @GetMapping("/all")
    public ResponseEntity<List<SubmissionPreview>> getAllSubmissions(Authentication authentication){
        List<SubmissionPreview> submissions = submissionService.getAllSubmissionsByUser(authentication);
        return new ResponseEntity<>(submissions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSubmissionById(@PathVariable UUID id){
        SubmissionDetails submission = submissionService.getSubmissionDetailsById(id);
        return new ResponseEntity<>(submission, HttpStatus.OK);
    }


    @PostMapping("/add")
    public ResponseEntity<?> addSubmission(@Valid @RequestBody SubmissionRequest submissionRequest, Authentication authentication){
        submissionService.addNewSubmission(submissionRequest, authentication);
        return ResponseEntity.ok().body(new ApiResponse(true, "Submission successful"));
    }

}
