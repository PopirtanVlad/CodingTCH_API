package com.example.licenta.dtos;

import lombok.Builder;
import lombok.Value;

import java.time.Duration;

@Value
@Builder
public class TestResultInfo{
    String errorMessage;
    boolean testStatus;
    int testMemoryUsed;
    long testTimeElapsed;
}
