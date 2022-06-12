package com.example.licenta.services.interfaces;

import com.example.licenta.dtos.submissions.SubmissionRequest;
import com.example.licenta.entities.Submission;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

public interface ISubmissionService {

    List<SubmissionRequest> getAllSubmissions();

    Submission addNewSubmission(SubmissionRequest submissionRequest, Authentication authentication);

    SubmissionRequest getSubmissionById(UUID id);
}
