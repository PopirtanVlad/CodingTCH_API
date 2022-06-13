package com.example.licenta.builders;

import com.example.licenta.dtos.TestResultInfo;
import com.example.licenta.dtos.submissions.SubmissionDetails;
import com.example.licenta.dtos.submissions.SubmissionPreview;
import com.example.licenta.dtos.submissions.SubmissionRequest;
import com.example.licenta.entities.Problem;
import com.example.licenta.entities.Submission;
import com.example.licenta.entities.User;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class SubmissionBuilder {

    public static SubmissionPreview generateSubmissionPreview(Submission submission){
        return SubmissionPreview.builder()
                .id(submission.getId())
                .submissionStatus(submission.getSubmissionStatus())
                .uploadTime(submission.getUploadTime().toLocalTime().truncatedTo(ChronoUnit.SECONDS))
                .uploadDate(submission.getUploadTime().toLocalDate())
                .problemTitle(submission.getProblem().getTitle())
                .build();
    }

    public static SubmissionDetails generateSubmissionDetails(Submission submission, Problem problem, String solution, List<TestResultInfo> testResultInfos){
        return SubmissionDetails.builder()
                .solution(solution)
                .submissionPreview(generateSubmissionPreview(submission))
                .problem(ProblemBuilder.generateProblemInfo(problem))
                .programmingLanguage(submission.getProgrammingLanguage())
                .testResults(testResultInfos)
                .build();
    }

    public static SubmissionRequest generateSubmissionRequest(Submission submission){
        return SubmissionRequest.builder()
                .id(submission.getId())
                .problemID(submission.getId())
                .programmingLanguage(submission.getProgrammingLanguage())
                .userID(submission.getUser().getId())
                .uploadTime(submission.getUploadTime())
                .build();
    }


    public static Submission generateSubmission(SubmissionRequest submissionRequest, User user, Problem problem){
        return Submission.builder()
                .problem(problem)
                .programmingLanguage(submissionRequest.getProgrammingLanguage())
                .user(user)
                .uploadTime(submissionRequest.getUploadTime())
                .build();
    }

}
