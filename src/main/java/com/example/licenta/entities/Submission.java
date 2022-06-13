package com.example.licenta.entities;

import com.example.licenta.utils.ProgrammingLanguage;
import com.example.licenta.utils.SubmissionStatus;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@Table(name = "submissions")
@AllArgsConstructor
public class Submission {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Problem problem;

    @Column(name = "PROGRAMMING_LANGUAGE")
    private ProgrammingLanguage programmingLanguage;

    @Column(name = "UPLOAD_TIME")
    private LocalDateTime uploadTime;

    @Column(name = "SUBMISSION_STATUS")
    private SubmissionStatus submissionStatus;

    @OneToMany(mappedBy = "submission")
    private Set<TestResult> testResults;

}
