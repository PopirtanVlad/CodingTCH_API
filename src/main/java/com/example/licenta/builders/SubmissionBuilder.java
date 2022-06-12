package com.example.licenta.builders;

import com.example.licenta.dtos.submissions.SubmissionDetails;
import com.example.licenta.entities.Problem;
import com.example.licenta.entities.Submission;
import com.example.licenta.entities.User;

public class SubmissionBuilder {

    public static SubmissionDetails generateSubmissionDetails(Submission submission){
        return SubmissionDetails.builder()
                .id(submission.getId())
                .problemID(submission.getId())
                .programmingLanguage(submission.getProgrammingLanguage())
                .userID(submission.getUser().getId())
                .build();
    }


    public static Submission generateSubmission(SubmissionDetails submissionDetails, User user, Problem problem){
        return Submission.builder()
                .problem(problem)
                .programmingLanguage(submissionDetails.getProgrammingLanguage())
                .user(user)
                .build();
    }

}
