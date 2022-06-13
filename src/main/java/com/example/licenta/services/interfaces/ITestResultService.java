package com.example.licenta.services.interfaces;

import com.example.licenta.entities.TestResult;

import java.util.List;
import java.util.UUID;

public interface ITestResultService {

    List<TestResult> getTestResultsBySubmission(UUID submissionId);

}
