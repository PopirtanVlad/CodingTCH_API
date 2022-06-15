package com.example.licenta.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.Duration;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "test_results")
public class TestResult {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name="TEST_STATUS")
    private boolean isCorrect;

    @Column(name="TIME_ELAPSED")
    private Duration timeElapsed;

    @Column(name="MEMORY_USED")
    private int memoryUsed;

    @Column(name="ERROR_MESSAGE")
    private String errorMessage;

    @ManyToOne
    private Submission submission;

}
