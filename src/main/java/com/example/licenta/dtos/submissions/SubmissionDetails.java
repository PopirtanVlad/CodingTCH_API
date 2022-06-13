package com.example.licenta.dtos.submissions;

import com.example.licenta.dtos.TestResultInfo;
import com.example.licenta.dtos.problem.ProblemInfo;
import com.example.licenta.entities.Problem;
import com.example.licenta.entities.TestResult;
import com.example.licenta.utils.ProgrammingLanguage;
import com.example.licenta.utils.SubmissionStatus;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Value
@Builder
public class SubmissionDetails implements Serializable {
    ProblemInfo problem;
    SubmissionPreview submissionPreview;
    ProgrammingLanguage programmingLanguage;
    String solution;
    List<TestResultInfo> testResults;

}
