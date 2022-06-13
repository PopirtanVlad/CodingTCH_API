package com.example.licenta.dtos.submissions;

import com.example.licenta.utils.ProgrammingLanguage;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder
public class SubmissionRequest {
    UUID id;
    ProgrammingLanguage programmingLanguage;
    UUID problemID;
    Long userID;
    String solutionText;
    LocalDateTime uploadTime;
}
