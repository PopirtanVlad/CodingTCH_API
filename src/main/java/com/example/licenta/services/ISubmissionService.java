package com.example.licenta.services;

import com.example.licenta.dtos.submissions.SubmissionDetails;
import com.example.licenta.entities.Submission;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ISubmissionService {

    List<SubmissionDetails> getAllSubmissions();

    Submission addNewSubmission(SubmissionDetails submissionDetails, Authentication authentication);
}
