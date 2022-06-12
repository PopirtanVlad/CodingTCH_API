package com.example.licenta.builders;

import com.example.licenta.dtos.submissions.SubmissionRequest;
import com.example.licenta.entities.Problem;
import com.example.licenta.entities.Submission;
import com.example.licenta.entities.User;

public class SubmissionBuilder {

    public static SubmissionRequest generateSubmissionRequest(Submission submission){
        return SubmissionRequest.builder()
                .id(submission.getId())
                .problemID(submission.getId())
                .programmingLanguage(submission.getProgrammingLanguage())
                .userID(submission.getUser().getId())
                .build();
    }


    public static Submission generateSubmission(SubmissionRequest submissionRequest, User user, Problem problem){
        return Submission.builder()
                .problem(problem)
                .programmingLanguage(submissionRequest.getProgrammingLanguage())
                .user(user)
                .build();
    }

}
