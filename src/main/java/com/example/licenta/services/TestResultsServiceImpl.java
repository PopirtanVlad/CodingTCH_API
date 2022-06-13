package com.example.licenta.services;


import com.example.licenta.builders.TestResultBuilder;
import com.example.licenta.dtos.TestResultInfo;
import com.example.licenta.entities.Submission;
import com.example.licenta.entities.TestResult;
import com.example.licenta.exceptions.ResourceNotFoundException;
import com.example.licenta.services.repositories.SubmissionRepository;
import com.example.licenta.services.repositories.TestResultsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TestResultsServiceImpl {


    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private TestResultsRepo testResultsRepo;

    public List<TestResultInfo> getTestResultsBySubmission(UUID submissionID){
        Optional<Submission> submission = submissionRepository.findById(submissionID);

        if (submission.isEmpty()){
            throw new ResourceNotFoundException("Submission", "id", submissionID);
        }

        List<TestResult> testResults = testResultsRepo.findBySubmission(submission.get());

        List<TestResultInfo> testResultsInfos = testResults.stream()
                .map(TestResultBuilder::generateTestResultInfo)
                .collect(Collectors.toList());
        return testResultsInfos;
    }

}
