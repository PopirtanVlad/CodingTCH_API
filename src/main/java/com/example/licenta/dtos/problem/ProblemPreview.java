package com.example.licenta.dtos.problem;

import com.example.licenta.utils.ProblemDifficulty;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class ProblemPreview {
    String title;
    ProblemDifficulty difficulty;
}
