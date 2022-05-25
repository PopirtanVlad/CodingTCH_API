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
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name="TEST_STATUS")
    private boolean isCorrect;

    @Column(name="TEST_TIME_ELAPSED")
    private Duration timeElapsed;

    @Column(name="TEST_MEMORY_USED")
    private int memoryUsed;

    @Column(name="ERROR_MESSAGE")
    private String errorMessage;

}
