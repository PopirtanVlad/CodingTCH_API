package com.example.licenta.builders;

import com.example.licenta.dtos.TestResultInfo;
import com.example.licenta.entities.TestResult;

public class TestResultBuilder {

    public static TestResultInfo generateTestResultInfo(TestResult testResult){
        return TestResultInfo.builder()
                .testMemoryUsed(testResult.getMemoryUsed())
                .testStatus(testResult.isCorrect())
                .errorMessage(testResult.getErrorMessage())
                .testTimeElapsed(testResult.getTimeElapsed().toMillis())
                .build();
    }

}
