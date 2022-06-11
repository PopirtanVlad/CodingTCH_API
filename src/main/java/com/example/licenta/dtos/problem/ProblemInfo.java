package com.example.licenta.dtos.problem;

import com.example.licenta.utils.ProblemDifficulty;
import lombok.Builder;
import lombok.Value;

import java.time.Duration;
import java.util.UUID;

@Value
@Builder
public class ProblemInfo {
    UUID id;
    String title;
    String statement;
    ProblemDifficulty difficulty;
    String exampleInput;
    String exampleOutput;
    int memoryLimit;
    Long timeLimit;
}
