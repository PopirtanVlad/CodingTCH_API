package com.example.licenta.entities;

import com.example.licenta.utils.ProgrammingLanguage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.Duration;
import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "submissions")
@AllArgsConstructor
public class Solution {

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

//    @Column(name = "UPLOAD_TIME")
//    private Duration uploadTime;



}
