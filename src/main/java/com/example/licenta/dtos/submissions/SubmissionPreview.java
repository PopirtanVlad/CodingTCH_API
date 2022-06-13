package com.example.licenta.dtos.submissions;

import com.example.licenta.utils.SubmissionStatus;
import lombok.Builder;
import lombok.Value;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Value
@Builder
public class SubmissionPreview {
    UUID id;
    SubmissionStatus submissionStatus;
    String problemTitle;
    LocalDate uploadDate;
    LocalTime uploadTime;
}
