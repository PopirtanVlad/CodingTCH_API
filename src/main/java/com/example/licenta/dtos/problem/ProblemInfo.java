package com.example.licenta.dtos.problem;

import com.example.licenta.utils.ProblemDifficulty;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class ProblemInfo {
    UUID id;
    String title;
    String statement;
    ProblemDifficulty difficulty;
}
