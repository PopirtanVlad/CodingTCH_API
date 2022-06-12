package com.example.licenta.controller;

import com.example.licenta.dtos.problem.ProblemInfo;
import com.example.licenta.dtos.submissions.SubmissionDetails;
import com.example.licenta.services.SubmissionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/submissions")
public class SubmissionsController {

    @Autowired
    private SubmissionServiceImpl submissionService;

    @GetMapping("/all")
    public ResponseEntity<List<SubmissionDetails>> getAllSubmissions(){
        List<SubmissionDetails> submissions = submissionService.getAllSubmissions();
        return new ResponseEntity<>(submissions, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addSubmission(@Valid @RequestBody SubmissionDetails submissionDetails, Authentication authentication){
        submissionService.addNewSubmission(submissionDetails, authentication);
        return new ResponseEntity<>("Problem saved successfully", HttpStatus.OK);
    }

}
